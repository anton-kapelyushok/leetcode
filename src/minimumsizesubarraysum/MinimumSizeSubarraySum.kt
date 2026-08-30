package minimumsizesubarraysum

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var left = 0

        var minLength = Int.MAX_VALUE
        var currentSum = 0

        for (right in nums.indices) {
            // EXPAND - add right to the window
            currentSum += nums[right]

            // SHRINK - remove [left] until window becomes valid
            while (currentSum - nums[left] >= target /* window is invalid */) {
                // remove [left] from the window
                currentSum -= nums[left]
                left++
            }

            // RECORD - record the valid window
            if (currentSum >= target) {
                minLength = minOf(minLength, right - left + 1)
            }
        }

        return minLength.takeIf { it != Int.MAX_VALUE } ?: 0
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.minSubArrayLen(7, intArrayOf(2, 3, 1, 2, 4, 3)))
    }

}
