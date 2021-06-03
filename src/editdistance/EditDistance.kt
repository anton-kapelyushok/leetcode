package editdistance

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minDistance(_word1: String, _word2: String): Int {
        val word1 = "*$_word1*"
        val word2 = "*$_word2*"

        val dp = Array(word1.length) { IntArray(word2.length) }
        for (i in word1.indices) {
            for (j in word2.indices) {
                val top = if (i - 1 in word1.indices) dp[i - 1][j] + 1 else -1
                val left = if (j - 1 in word2.indices) dp[i][j - 1] + 1 else -1
                val leftTop = if (i - 1 in word1.indices && j - 1 in word2.indices)
                    dp[i - 1][j - 1] + (if (word1[i] == word2[j]) 0 else 1) else -1

                val res = listOf(top, left, leftTop).filter { it != -1 }.min() ?: 0

                dp[i][j] = res
            }
        }

        return dp[word1.length - 1][word2.length - 1]
    }
}


class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(0, s.minDistance(
                "zoologicoarchaeologist",
                "zoogeologist"

        ))
    }

}
