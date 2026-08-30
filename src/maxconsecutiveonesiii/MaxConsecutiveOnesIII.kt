package maxconsecutiveonesiii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun longestOnes(nums: IntArray, k: Int): Int {
        var flipped = 0

        var left = 0

        var maxWindow = 0
        for (right in nums.indices) {
            // 1) EXPAND STATE - add [right] to the state
            if (nums[right] == 0) flipped++

            // 2) SHRINK - remove [left] until window becomes valid
            while (flipped > k) {
                if (nums[left] == 0) flipped--
                left++
            }

            // 3) RECORD the valid window
            maxWindow = maxOf(maxWindow, right - left + 1)
        }

        return maxWindow
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }

}
