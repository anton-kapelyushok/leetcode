package houserobber

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun rob(nums: IntArray): Int {
        var dp1 = 0
        var dp2 = 0

        for (i in nums.size - 1 downTo 0) {
            val skip = dp1
            val rob = nums[i] + dp2

            val result = maxOf(skip, rob)
            dp2 = dp1
            dp1 = result
        }

        return dp1
    }
}


class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.rob(intArrayOf(1, 2, 3, 1)))
    }

}
