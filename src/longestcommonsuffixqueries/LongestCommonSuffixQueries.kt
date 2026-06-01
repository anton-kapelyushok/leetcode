package longestcommonsuffixqueries

import org.junit.jupiter.api.Test

class Solution {
    class Trie(
        var wordIdx: Int = -1,
        val next: MutableMap<Char, Trie> = mutableMapOf()
    )

    fun stringIndices(wordsContainer: Array<String>, wordsQuery: Array<String>): IntArray {

        val sortedWords = wordsContainer
            .withIndex()
            .sortedWith(compareBy({ (i, v) -> v.length }, { (i) -> i }))
            .also { println(it) }

        val root = Trie(wordIdx = sortedWords[0].index)
        fun addWord(idx: Int, w: String) {
            var n = root
            w.reversed().forEach { c ->
                n.next[c] = n.next[c] ?: Trie(wordIdx = idx)
                n = n.next[c]!!
            }
        }


        sortedWords.forEach { (i, v) -> addWord(i, v) }


        fun query(q: String): Int {
            var n = root
            for (c in q.reversed()) {
                if (n.next[c] != null) n = n.next[c]!!
                else return n.wordIdx
            }
            return n.wordIdx
        }

        return wordsQuery.map { query(it) }.toIntArray()

    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        val r = s.stringIndices(
            arrayOf("abcd", "bcd", "xbcd"),
            arrayOf("cd","bcd","xyz")
        )

        println(r.toList())
    }

    @Test
    fun test2() {
        val r = s.stringIndices(
            arrayOf("abcdefgh","poiuygh","ghghgh"),
            arrayOf("gh","acbfgh","acbfegh")
        )

        println(r.toList())
    }

}
