package removesubfoldersfromthefilesystem

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    class Trie(
        val children: MutableMap<String, Trie> = mutableMapOf(),
        var hasValue: Boolean = false
    )

    fun removeSubfolders(folder: Array<String>): List<String> {
        val root = Trie()
        val folders = folder
        folders.forEach { folder ->
            var cur = root
            folder.split("/").drop(1).forEach { component ->
                cur.children[component] = cur.children[component] ?: Trie()
                cur = cur.children[component]!!
            }
            cur.hasValue = true
        }

        val result = mutableListOf<String>()
        fun traverse(t: Trie, acc: String) {
            if (t.hasValue) {
                result += acc
                return
            }
            t.children.forEach { c -> traverse(c.value, acc + "/" + c.key) }
        }

        traverse(root, "")
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf("/a", "/c/d", "/c/f"), s.removeSubfolders(arrayOf("/a", "/a/b", "/c/d", "/c/d/e", "/c/f")))
    }

}
