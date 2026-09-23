package minimumoperationstoreducextozero

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minOperations(nums: IntArray, x: Int): Int {
        var minOps = Int.MAX_VALUE

        val target = nums.sum() - x
        var sum = 0

        var l = 0
        for (r in nums.indices) {
            // EXPAND
            sum += nums[r]

            // SHRINK
            while (sum > target && l <= r) {
                sum -= nums[l]
                l++
            }

            // RECORD
            if (sum == target) {
                val ops = nums.size - (r - l + 1)
                minOps = minOf(minOps, ops)
            }
        }

        val result = minOps.takeIf { it != Int.MAX_VALUE } ?: -1
        return result
    }

    fun minOperations1(nums: IntArray, x: Int): Int {
        var minOps = Int.MAX_VALUE

        var lSum = 0
        var rSum = nums.sum()
        var r = 0

        // left prefix  is [0..l]
        // right prefix is [r..nums.size]
        for (l in -1 until nums.size) {
            // EXPAND LEFT WINDOW
            if (l != -1) {
                lSum += nums[l]
            }

            // LSUM WILL ONLY GET BIGGER, RETURN
            if (lSum > x) {
                break
            }

            // SHRINK RIGHT WINDOW
            while (r <= l || lSum + rSum > x) {
                rSum -= nums[r]
                r++
            }

            // RECORD
            if (lSum + rSum == x) {
                val ops = l + 1 + (nums.size - r)
                minOps = minOf(minOps, ops)
            }
        }

        val result = minOps.takeIf { it != Int.MAX_VALUE } ?: -1
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.minOperations(intArrayOf(1, 1, 4, 2, 3), 5))
    }
}
