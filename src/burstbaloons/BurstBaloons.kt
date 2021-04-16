package burstbaloons;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class Solution {
    fun maxCoins(nums: IntArray): Int {
        val dp = Array(nums.size + 2) { IntArray(nums.size + 2) }

        for (i in 1..nums.size+1) {
            for (j in -1..nums.size - i) {
                val l = j
                val r = j + i

                val lv = if (l in nums.indices) nums[l] else 1
                val rv = if (r in nums.indices) nums[r] else 1

                var max = 0
                for (m in l + 1..r - 1) {
                    val mv = nums[m] * lv * rv
                    max = Math.max(max, mv + dp[l + 1][m + 1] + dp[m + 1][r + 1])
                }

                dp[l+1][r+1] = max
            }
        }

        return dp[0][nums.size + 1]
    }
}

class TdSolution {
    fun maxCoins(nums: IntArray): Int {
        val dp = Array(nums.size + 2) { IntArray(nums.size + 2) }
        fun f(l: Int, r: Int): Int {
            if (dp[l + 1][r + 1] != 0) return dp[l + 1][r + 1]
            if (r - l == 1) return 0
            val lv = if (l in nums.indices) nums[l] else 1
            val rv = if (r in nums.indices) nums[r] else 1

            var max = 0
            for (i in l + 1..r - 1) {
                val mv = nums[i] * lv * rv
                max = Math.max(max, mv + f(l, i) + f(i, r))
            }

            dp[l + 1][r + 1] = max

            return max
        }


        return f(-1, nums.size)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(167, s.maxCoins(intArrayOf(3, 1, 5, 8)))
    }


    @Test
    fun test2() {
        assertEquals(56, s.maxCoins(intArrayOf(1, 5, 8)))
    }

    @Test
    fun test3() {
        assertEquals(48, s.maxCoins(intArrayOf(5, 8)))
    }

    @Test
    fun test4() {
        assertEquals(8, s.maxCoins(intArrayOf(8)))
    }

}
