package distinctsubsequences

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun numDistinct(s: String, t: String): Int {

        val dp = Array(s.length) { IntArray(t.length) { -1 } }

        fun f(i: Int, j: Int): Int {
            if (j == t.length) return 1
            if (i == s.length) return 0

            if (dp[i][j] != -1) return dp[i][j]

            var result = 0
            if (s[i] == t[j]) {
                result += f(i + 1, j + 1)
            }
            result += f(i + 1, j)

            dp[i][j] = result

            return result
        }

        return f(0, 0)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.numDistinct("rabbbit", "rabbit"))
    }

}
