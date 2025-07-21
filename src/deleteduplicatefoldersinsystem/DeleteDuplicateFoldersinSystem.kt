package deleteduplicatefoldersinsystem

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun deleteDuplicateFolder(paths: List<List<String>>): List<List<String>> {
        class Folder(
            val children: MutableMap<String, Folder> = mutableMapOf(),
            var contentHash: Int = 0,
        )

        val root = Folder()

        paths.forEach { parts ->
            var cur = root
            parts.forEach { part ->
                cur.children[part] = cur.children[part] ?: Folder()
                cur = cur.children[part]!!
            }
        }


        val compressedPaths = mutableMapOf<String, Int>()
        var lastCompressedPathId = 0


        val hashes = mutableMapOf<String, Int>()
        var lastHash = 0

        fun calculateHash(cur: Folder) {
            if (cur.children.isEmpty()) {
                cur.contentHash = 0
                return
            }
            cur.children.forEach { calculateHash(it.value) }

            val contentId = cur.children.toList()
                .map { (path, folder) ->
                    compressedPaths[path] = compressedPaths[path] ?: ++lastCompressedPathId
                    val compressedPathId = compressedPaths[path]!!
                    compressedPathId to folder.contentHash
                }
                .sortedBy { it.first }
                .joinToString(" ") { (pathId, contentHash) -> "$pathId.$contentHash" }

            hashes[contentId] = hashes[contentId] ?: ++lastHash
            cur.contentHash = hashes[contentId]!!
        }
        calculateHash(root)

        val counts = mutableMapOf<Int, Int>()
        fun countHashes(cur: Folder) {
            counts[cur.contentHash] = (counts[cur.contentHash] ?: 0) + 1
            cur.children.forEach { (_, folder) -> countHashes(folder) }
        }
        countHashes(root)

        val result = mutableListOf<List<String>>()
        fun collectResult(cur: Folder, acc: MutableList<String>) {
            val count = counts[cur.contentHash]!!
            if (cur.contentHash != 0 && count > 1) {
                return
            }

            if (cur != root) {
                result += acc.toList()
            }

            cur.children.forEach { (path, folder) ->
                acc += path
                collectResult(folder, acc)
                acc.removeLast()
            }
        }
        collectResult(root, mutableListOf())

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            listOf(
                listOf("x")
            ), s.deleteDuplicateFolder(
                listOf(
                    listOf("a"),
                    listOf("a", "b"),
                    listOf("a", "c"),
                    listOf("a", "c", "d"),
                    listOf("x", "a"),
                    listOf("x", "a", "b"),
                    listOf("x", "a", "c"),
                    listOf("x", "a", "c", "d"),
                )
            )
        )
    }

    @Test
    fun test2() {
        assertEquals(
            listOf(
                listOf("a")
            ),
            s.deleteDuplicateFolder(
                listOf(
                    listOf("a"),
                    listOf("a", "b"),
                    listOf("a", "b", "c"),
                    listOf("x"),
                    listOf("x", "c")
                )
            )
        )
    }

    @Test
    fun test3() {
        assertEquals(
            listOf(
                listOf("a"),
                listOf("a", "b"),
                listOf("c"),
                listOf("c", "b"),
            ),
            s.deleteDuplicateFolder(
                listOf(
                    listOf("a"),
                    listOf("c"),
                    listOf("a", "b"),
                    listOf("c", "b"),
                    listOf("a", "b", "x"),
                    listOf("a", "b", "x", "y"),
                    listOf("w"),
                    listOf("w", "y"),
                )
            )
        )
    }

    @Test
    fun test4() {
        assertEquals(
            listOf(
                listOf("a"),
                listOf("a", "z"),
                listOf("b"),
                listOf("b", "z"),
                listOf("b", "w"),
            ),
            s.deleteDuplicateFolder(
                listOf(
                    listOf("a"),
                    listOf("a", "x"),
                    listOf("a", "x", "y"),
                    listOf("a", "z"),

                    listOf("b"),
                    listOf("b", "x"),
                    listOf("b", "x", "y"),
                    listOf("b", "z"),
                    listOf("b", "w")
                )
            )
        )
    }

    @Test
    fun test5() {
        assertEquals(
            listOf(
                listOf("c"),
                listOf("c", "a")
            ),
            s.deleteDuplicateFolder(
                listOf(
                    listOf("c"),
                    listOf("b"),
                    listOf("p"),
                    listOf("c", "a", "b", "a"),
                    listOf("c", "a", "b"),
                    listOf("c", "a"),
                    listOf("b", "a"),
                    listOf("b", "a", "a"),
                    listOf("p", "a"),
                    listOf("p", "a", "a")
                )
            )
        )
    }

}
