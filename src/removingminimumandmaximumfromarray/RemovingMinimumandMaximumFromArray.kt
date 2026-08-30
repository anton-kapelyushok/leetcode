package removingminimumandmaximumfromarray

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minimumDeletions(nums: IntArray): Int {
        val minIdx = nums.indices.minBy { nums[it] }
        val maxIdx = nums.indices.maxBy { nums[it] }
        val leftIdx = minOf(minIdx, maxIdx)
        val rightIdx = maxOf(minIdx, maxIdx)

        val deleteFromLeft = rightIdx + 1 // 0 1 2 3 - to delete 1 from left = 2 = idx + 1
        val deleteFromRight = nums.size - leftIdx // 0 1 2 3, to delete 1 from right = 3 = size - idx
        val deleteFromBoth = leftIdx + 1 + nums.size - rightIdx

        return minOf(deleteFromLeft, deleteFromRight, deleteFromBoth)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(5, s.minimumDeletions(intArrayOf(2, 10, 7, 5, 4, 1, 8, 6)))
    }

}
