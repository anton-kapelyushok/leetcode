package wordbreak2

import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    fun wordBreak(s: String, wordDict: List<String>): List<String> {
        val dict = wordDict.toSet()

        val q = LinkedList<Pair<Int, String>>()

        q.offer(0 to "")

        val result = mutableListOf<String>()

        while (!q.isEmpty()) {
            val (v, path) = q.pollFirst()

            if (v == s.length) {
                result.add(path)
            }

            for (i in v until s.length) {
                val word = s.substring(v..i)
                if (word in dict) {
                    q.offer((i + 1) to "$path $word")
                }
            }
        }

        return result.map { it.substring(1) }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        println(s.wordBreak("catsanddog", listOf("cats", "dog", "sand", "and", "cat")))
    }
}
