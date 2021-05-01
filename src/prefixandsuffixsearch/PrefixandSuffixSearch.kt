package prefixandsuffixsearch

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WordFilter(val words: Array<String>) {

    val trie = Trie(
            words.indices.flatMap { idx ->
                val word = words[idx]
                (0..word.length).map { s ->
                    val w = word.substring(word.length - s until word.length) + '#' + word
                    W(idx, w)
                }
            }
    )

    fun f(prefix: String, suffix: String): Int {
        return trie.find("$suffix#$prefix").lastOrNull() ?: -1
    }

    class Trie(words: List<W>) {
        class Node(val v: Char, val next: MutableMap<Char, Node>, val indices: MutableList<Int>)

        fun node(v: Char): Node = Node(v, mutableMapOf(), mutableListOf())
        val head = node('-')

        init {
            words.forEach { w ->
                var cur = head
                cur.indices += w.index
                w.w.forEach { c ->
                    var next = cur.next[c]
                    if (next == null) {
                        next = node(c)
                        cur.next[c] = next
                    }
                    next.indices += w.index
                    cur = next
                }
            }
        }

        fun find(prefix: String): List<Int> {
            var cur = head

            prefix.forEach { c ->
                cur = cur.next[c] ?: return emptyList()
            }

            return cur.indices
        }
    }

    data class W(val index: Int, val w: String)
}

class SolutionTest {


    @Test
    fun test1() {
        val wf = WordFilter(arrayOf("apple"))
        assertEquals(0, wf.f("a", "e"))
    }


}
