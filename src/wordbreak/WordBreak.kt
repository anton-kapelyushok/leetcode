package wordbreak

import org.junit.jupiter.api.Test
import java.util.*

class BfsSolution {

    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val dict = wordDict.toSet()
        val q = LinkedList<Int>()
        val visited = mutableSetOf<Int>()

        q.offer(0)

        while (!q.isEmpty()) {
            val v = q.pollFirst()
            if (v in visited) continue
            visited += v

            if (v == s.length) return true

            for (i in v until s.length) {
                val word = s.substring(v..i)
                if (word in dict) {
                    q.offer(i + 1)
                }
            }
        }

        return false
    }
}

class DpBottomUpSolution {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val dict = wordDict.toSet()
        val dp = Array(s.length + 1) { false }

        // end of recursion
        dp[0] = true

        i@ for (i in 1..s.length) {
            for (j in 0 until i) {
                if (dp[j] && s.substring(j until i) in dict) {
                    dp[i] = true
                    continue@i
                }
            }
        }

        return dp[s.length]
    }

}

class DpTopDownSolution {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val dict = wordDict.toSet()

        val dp = Array<Boolean?>(s.length + 1) { null }

        return wordBreak(s, dict, dp)
    }

    fun wordBreak(s: String, dict: Set<String>, dp: Array<Boolean?>): Boolean {
        if (dp[s.length] != null) return dp[s.length]!!
        if (s.isEmpty()) {
            dp[0] = true
            return dp[0]!!
        }

        for (i in 0..s.length) {
            val prefix = s.substring(0 until i)
            if (prefix !in dict) {
                continue
            }

            val postfix = s.substring(i until s.length)
            if (wordBreak(postfix, dict, dp)) {
                dp[s.length] = true
                return dp[s.length]!!
            }
        }

        dp[s.length] = false
        return dp[s.length]!!
    }
}

class SolutionTest {
    private val s = BfsSolution()

    @Test
    fun test1() {
        s.wordBreak("catsandog", listOf("cats", "dog", "sand", "and", "cat"))
    }
}
