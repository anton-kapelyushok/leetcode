package stringcompressionii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun getLengthOfOptimalCompression(s: String, k: Int): Int {
        val dp = Array(s.length + 1) { Array(s.length + 1) { Array(28) { IntArray(s.length + 1) { -1 } } } }
        fun f(i: Int, k: Int, leftC: Char, leftCount: Int): Int {
            if (i >= s.length) return 0
            if (dp[i][k][leftC - 'a'][leftCount] != -1) {
                return dp[i][k][leftC - 'a'][leftCount]
            }

            val c = s[i]

            var min = Int.MAX_VALUE
            if (c == leftC) {
                // try to join current
                val newLeftCount = leftCount + 1
                var res = f(i + 1, k, leftC, newLeftCount)
                if (newLeftCount == 2 || newLeftCount == 10 || newLeftCount == 100) {
                    // transitioned from c -> c2 or c9 -> c10 or c99 -> c100
                    res += 1
                }
                min = minOf(min, res)
            }

            if (k > 0) {
                // try skip
                val skip = f(i + 1, k - 1, leftC, leftCount)
                min = minOf(min, skip)
            }

            if (c != leftC) {
                // start new
                val startNew = 1 + f(i + 1, k, c, 1)
                min = minOf(min, startNew)
            }

            dp[i][k][leftC - 'a'][leftCount] = min
            return min
        }

        val result = f(0, k, 'z' + 1, 0)
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(0, s.getLengthOfOptimalCompression("a", 1))
    }

    @Test
    fun test2() {
        assertEquals(4, s.getLengthOfOptimalCompression("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0))
    }

}
