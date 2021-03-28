package containerwithmostwater

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun maxArea(heights: IntArray): Int {
        var left = 0
        var right = heights.size - 1

        var maxSize = 0

        while (left < right) {
            val effectiveHeight = min(heights[left], heights[right])
            val size = effectiveHeight * (right - left)
            if (maxSize < size) maxSize = size
            when {
                heights[left] < heights[right] -> left++
                else -> right--
            }
        }
        return maxSize
    }

    private fun min(left: Int, right: Int) = if (left < right) left else right
}


class SolutionTest {
    val s = Solution()

    @Test
    fun test1() {
        assertEquals(1, s.maxArea(intArrayOf(1, 1)))
    }

    @Test
    fun test2() {
        assertEquals(4, s.maxArea(intArrayOf(1, 2, 4, 3)))
    }

    @Test
    fun test3() {
        assertEquals(16, s.maxArea(intArrayOf(4, 3, 2, 1, 4)))
    }

    @Test
    fun test4() {
        assertEquals(2, s.maxArea(intArrayOf(1, 2, 1)))
    }

    @Test
    fun test5() {
        assertEquals(49, s.maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7)))
    }
}
