package findpeakelement

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findPeakElement(nums: IntArray): Int {
        if (nums.size == 1) return 0
        if (nums[0] > nums[1]) return 0
        if (nums[nums.size - 1] > nums[nums.size - 2]) return nums.size - 1

        var l = 0
        var r = nums.size - 1
        while (true) {
            val m = (r + l) / 2
            if (nums[m - 1] < nums[m] && nums[m] > nums[m + 1]) return m

            if (nums[m - 1] < nums[m + 1]) l = m
            else r = m
        }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(5, s.findPeakElement(intArrayOf(1, 2, 1, 3, 5, 6, 4)))
    }
}
