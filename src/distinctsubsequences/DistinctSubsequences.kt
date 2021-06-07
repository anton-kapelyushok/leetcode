package distinctsubsequences

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun numDistinct(s: String, t: String): Int {
        val dp = Array(s.length + 1) { IntArray(t.length + 1) { -1 } }

        fun f(i: Int, j: Int): Int {
            if (dp[i][j] != -1) return dp[i][j]
            if (i >= s.length) {
                val res = 0
                dp[i][j] = res
                return res
            }
            if (j == t.length - 1) {
                var res = 0
                if (s[i] == t[j]) {
                    res += 1
                }

                res += f(i + 1, j)
                dp[i][j] = res
                return res
            }
            if (s[i] != t[j]) {
                var res = f(i + 1, j)
                dp[i][j] = res
                return res
            } else {
                val take = f(i + 1, j + 1)
                val skip = f(i + 1, j)
                val res = take + skip
                dp[i][j] = res
                return res
            }
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

    @Test
    fun test2() {
        assertEquals(5, s.numDistinct("babgbag", "bag"))
    }

}
