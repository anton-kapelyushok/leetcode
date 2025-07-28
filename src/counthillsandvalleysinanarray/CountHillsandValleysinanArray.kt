package counthillsandvalleysinanarray

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun countHillValley(nums: IntArray): Int {
        var prev = nums[0]
        var i = 1
        while (nums[i] == prev) {
            i++
            if (i !in nums.indices) return 0
        }
        var goingUp = nums[i] > prev
        prev = nums[i]
        var hillsAndValleysCount = 0
        i++
        while (i in nums.indices) {
            if (goingUp && nums[i] < prev) {
                goingUp = false
                hillsAndValleysCount++
            } else if (!goingUp && nums[i] > prev) {
                goingUp = true
                hillsAndValleysCount++
            }

            prev = nums[i]
            i++
        }
        return hillsAndValleysCount
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.countHillValley(intArrayOf(2, 4, 1, 1, 6, 5)))
    }

    @Test
    fun test2() {
        assertEquals(0, s.countHillValley(intArrayOf(6, 6, 5, 5, 4, 1)))
    }

    @Test
    fun test3() {
        assertEquals(0, s.countHillValley(intArrayOf(2, 2, 2)))
    }

    @Test
    fun test4() {
        assertEquals(1, s.countHillValley(intArrayOf(1, 2, 2, 3, 2)))
    }

}
