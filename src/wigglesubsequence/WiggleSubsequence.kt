package wigglesubsequence

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun wiggleMaxLength(nums: IntArray): Int {
        val up = calculate(nums, "up")
        val down = calculate(nums, "down")

        return if (up > down) up else down
    }

    fun calculate(nums: IntArray, initialDirection: String): Int {
        if (nums.size <= 1) return nums.size

        // case 1: low - high - low sequence
        var direction = initialDirection
        var last = nums[0]
        var count = 1
        for (i in 1 until nums.size) {
            if (direction == "up") {
                if (nums[i] > last) {
                    last = nums[i]
                    count++
                    direction = "down"
                } else {
                    last = nums[i]
                }
            } else {
                if (nums[i] < last) {
                    last = nums[i]
                    count++
                    direction = "up"
                } else {
                    last = nums[i]
                }
            }
        }

        return count
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test0() {
        assertEquals(2, s.wiggleMaxLength(intArrayOf(1, 2)))
    }

    @Test
    fun test1() {
        assertEquals(6, s.wiggleMaxLength(intArrayOf(1, 7, 4, 9, 2, 5)))
    }

    @Test
    fun test2() {
        assertEquals(7, s.wiggleMaxLength(intArrayOf(1, 17, 5, 10, 13, 15, 10, 5, 16, 8)))
    }

    @Test
    fun test3() {
        assertEquals(2, s.wiggleMaxLength(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)))
    }
}
