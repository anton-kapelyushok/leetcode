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

class DpSolution1 {
    fun wiggleMaxLength(nums: IntArray): Int {
        val upDp = Array(nums.size) { 0 }
        val downDp = Array(nums.size) { 0 }

        upDp[0] = 1
        downDp[0] = 1

        for (i in nums.indices) {
            for (j in 0 until i) {
                if (nums[i] > nums[j]) {
                    upDp[i] = downDp[j] + 1
                } else if (nums[i] < nums[j]) {
                    downDp[i] = upDp[j] + 1
                }
            }
        }

        return (upDp + downDp).max()
    }
}

class DpSolution2 {
    fun wiggleMaxLength(nums: IntArray): Int {
        val upDp = Array(nums.size) { 0 }
        val downDp = Array(nums.size) { 0 }

        upDp[0] = 1
        downDp[0] = 1

        for (i in (1 until nums.size)) {
            upDp[i] = upDp[i - 1]
            downDp[i] = downDp[i - 1]
            if (nums[i] > nums[i - 1]) {
                upDp[i] = downDp[i - 1] + 1
            } else if (nums[i] < nums[i - 1]) {
                downDp[i] = upDp[i - 1] + 1
            }
        }

        return (upDp + downDp).max()
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
    fun test2_reversed() {
        assertEquals(7, s.wiggleMaxLength(intArrayOf(1, 17, 5, 10, 13, 15, 10, 5, 16, 8).reversedArray()))
    }

    @Test
    fun test3() {
        assertEquals(2, s.wiggleMaxLength(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)))
    }
}
