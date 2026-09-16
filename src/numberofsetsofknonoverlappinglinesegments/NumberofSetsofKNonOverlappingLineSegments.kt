package numberofsetsofknonoverlappinglinesegments

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    val mod = 1_000_000_000 + 7

    // k,n ~ 2000
    fun numberOfSets(n: Int, k: Int): Int {
        val dp = Array(2) {Array(n + 1) { LongArray(k + 1) { -1L } } }


        // k is the number of not closed segemnts
        fun f(isOpen: Boolean, i: Int, k: Int): Long {
            if (k == 0) {
                return 1L
            }

            if (i == n) {
                return 0L
            }

            val o = if (isOpen) 1 else 0

            if (dp[o][i][k] != -1L)  {
                return dp[o][i][k]
            }

            var result = 0L
            if (isOpen) {
                // if the segment is open we can:

                // 1) close the current segment with current element
                result += f(false, i, k - 1)

                // 2) expand the current segment
                result += f(true, i + 1, k)
            } else {
                // if the segment is closed we can:
                // 1) open it
                result += f(true, i + 1, k)

                // 2) or skip to the next element
                result += f(false, i + 1, k)
            }

            result %= mod

            dp[o][i][k] = result

            return result
        }


        val result = f(false, 0, k).toInt()

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(5, s.numberOfSets(4, 2))
    }

    @Test
    fun test2() {
        assertEquals(3, s.numberOfSets(3, 1))
    }

    @Test
    fun test3() {
        assertEquals(796297179, s.numberOfSets(30, 7))
    }

}
