package containerwithmostwater_2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maxArea(height: IntArray): Int {
        var l = 0
        var r = height.lastIndex

        var maxArea = 0

        while (l <= r) {
            val area = (r - l) * minOf(height[l], height[r])
            maxArea = maxOf(area, maxArea)
            if (height[l] < height[r]) l++
            else r--
        }

        return maxArea
    }
}


class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(49, s.maxArea(intArrayOf(1,8,6,2,5,4,8,3,7)))
    }
}
