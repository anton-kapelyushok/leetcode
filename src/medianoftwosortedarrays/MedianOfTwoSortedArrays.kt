package medianoftwosortedarrays

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val allNums = (nums1 + nums2).sorted()
        return if (allNums.size % 2 == 0) {
            (allNums[allNums.size / 2 - 1] + allNums[allNums.size / 2]).toDouble() / 2
        } else {
            (allNums[allNums.size / 2]).toDouble()
        }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2.0, s.findMedianSortedArrays(intArrayOf(1, 3), intArrayOf(2)))
    }

    @Test
    fun test2() {
        assertEquals(2.5, s.findMedianSortedArrays(intArrayOf(1, 2), intArrayOf(3, 4)))
    }
}
