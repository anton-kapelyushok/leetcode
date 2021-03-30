package minimumlimitofballsinabag;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Approach 1:
 * Ideas:
 * on each step we need to divide only the max bucket
 * there is bucket / 2 to do it
 * complexity will be O(maxOperations * n * max(bucket)) - will not pass time constrains
 *
 * Approach 2:
 * Ideas:
 * bucket divided into k buckets will have max value  = (v + k - 1) / k
 *
 * minimumSize(nums, mOperations) = min(k = (0..maxOperations), max(v(nums[0], k), minimumSize(nums[1:], mOperations - k))
 * use dp[maxOperations + 1][nums size]
 *
 * k is still to big?
 *
 * Approach 3:
 * Try to guess the correct answer, it will be in (0..max(nums)) range
 *
 * Complexity:
 * O(n) to find max number
 * [+]
 * log(max(nums)) times for each guess
 * [*]
 * O(n) to check with v = (n + k - 1) / k formula
 *
 */

class Solution {
    fun minimumSize(nums: IntArray, maxOperations: Int): Int {
        val max = nums.max()!!
        var l = 1
        var r = max
        while (l < r) {
            val m = (l + r) / 2
            if (checkGuess(nums, maxOperations, m)) {
                r = m
            } else {
                l = m + 1
            }
        }
        return l
    }

    fun checkGuess(nums: IntArray, maxOperations: Int, guess: Int): Boolean {
        var timesLeft = maxOperations
        for (i in nums.indices) {
            val k = (nums[i] + guess - 1) / guess - 1
            timesLeft -= k
        }
        return timesLeft >= 0
    }
}

class DpSolution {
    fun minimumSize(nums: IntArray, maxOperations: Int): Int {
        val dp = Array(maxOperations + 1) { IntArray(nums.size) }

        val res = minimumSize(nums, 0, maxOperations, dp)
        return res
    }

    fun minimumSize(nums: IntArray, start: Int, maxOperations: Int, dp: Array<IntArray>): Int {
        if (start >= nums.size) return 0
        if (dp[maxOperations][start] != 0) return dp[maxOperations][start]
        var min = Int.MAX_VALUE

        for (k in 0..maxOperations) {
            val v = (nums[start] + k) / (k + 1)
            val tail = minimumSize(nums, start + 1, maxOperations - k, dp)
            min = Math.min(Math.max(tail, v), min)
        }
        dp[maxOperations][start] = min

        return min
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.minimumSize(intArrayOf(9), 2))
    }

    @Test
    fun test2() {
        assertEquals(2, s.minimumSize(intArrayOf(2, 4, 8, 2), 4))
    }

    @Test
    fun test3() {
        assertEquals(129, s.minimumSize(intArrayOf(431, 922, 158, 60, 192, 14, 788, 146, 788, 775, 772, 792, 68, 143, 376, 375, 877, 516, 595, 82, 56, 704, 160, 403, 713, 504, 67, 332, 26), 80))
    }

    @Test
    fun test4() {
        assertEquals(true, s.checkGuess(intArrayOf(10, 20, 30), 1, 20))
        assertEquals(false, s.checkGuess(intArrayOf(10, 20, 30), 1, 19))
        assertEquals(true, s.checkGuess(intArrayOf(10, 20, 30), 2, 19))
        assertEquals(true, s.checkGuess(intArrayOf(10, 20, 30), 2, 15))
        assertEquals(false, s.checkGuess(intArrayOf(10, 20, 30), 2, 14))
    }
}
