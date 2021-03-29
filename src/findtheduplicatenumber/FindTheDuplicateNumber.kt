package findtheduplicatenumber

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findDuplicate(nums: IntArray): Int {
        var tort = nums[0]
        var hare = nums[nums[0]]

        while (tort != hare) {
            tort = nums[tort]
            hare = nums[nums[hare]]
        }

        hare = 0
        while (tort != hare) {
            tort = nums[tort]
            hare = nums[hare]
        }

        return tort
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.findDuplicate(intArrayOf(1, 3, 4, 2, 2)))
    }


    @Test
    fun test2() {
        assertEquals(3, s.findDuplicate(intArrayOf(3, 1, 3, 4, 2)))
    }

    @Test
    fun test3() {
        assertEquals(1, s.findDuplicate(intArrayOf(1, 1)))
    }

    @Test
    fun test4() {
        assertEquals(1, s.findDuplicate(intArrayOf(1, 1, 2)))
    }

    @Test
    fun test5() {
        assertEquals(2, s.findDuplicate(intArrayOf(2, 2, 2, 2, 2)))
    }

    @Test
    fun test6() {
        assertEquals(4, s.findDuplicate(intArrayOf(1, 4, 4, 2, 4)))
    }

    @Test
    fun test7() {
        assertEquals(3, s.findDuplicate(intArrayOf(3, 1, 3, 4, 2)))
    }

    @Test
    fun test8() {
        assertEquals(4, s.findDuplicate(intArrayOf(1, 4, 2, 4, 3)))
    }
}
