package firstmissingpositive

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun firstMissingPositive(nums: IntArray): Int {
        val min = nums.asSequence().filter { it > 0 }.min() ?: return 1
        if (min != 1) return 1

        for (i in nums.indices) {
            while (nums[i] - 1 in nums.indices && nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                val tmp = nums[nums[i] - 1]
                nums[nums[i] - 1] = nums[i]
                nums[i] = tmp
            }
        }

        for (i in nums.indices) {
            if (nums[i] != i + 1) return i + 1
        }

        return nums.size + 1
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.firstMissingPositive(intArrayOf(1, 2, 0)))
    }

    @Test
    fun test2() {
        assertEquals(2, s.firstMissingPositive(intArrayOf(3, 4, -1, 1)))
    }

    @Test
    fun test3() {
        assertEquals(1, s.firstMissingPositive(intArrayOf(7, 8, 9, 11, 12)))
    }

    @Test
    fun test4() {
        assertEquals(3, s.firstMissingPositive(intArrayOf(1, 2, 4)))
    }

    @Test
    fun test5() {
        assertEquals(2, s.firstMissingPositive(intArrayOf(1, 1)))
    }

    @Test
    fun test6() {
        assertEquals(2, s.firstMissingPositive(intArrayOf(7, 3, 5, 8, 1)))
    }

    @Test
    fun test7() {
        assertEquals(2, s.firstMissingPositive(intArrayOf(1)))
    }

    @Test
    fun test8() {
        assertEquals(3, s.firstMissingPositive(intArrayOf(1, 2)))
    }
}

