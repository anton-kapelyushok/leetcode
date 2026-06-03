package maxdotproductoftwosubsequences

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maxDotProduct(nums1: IntArray, nums2: IntArray): Int {
        if (
            (nums1.all { it > 0 } && nums2.all { it < 0 })
            || (nums1.all { it < 0 } && nums2.all { it > 0 })
        ) {
            return -nums1.minOf { kotlin.math.abs(it) } * nums2.minOf { kotlin.math.abs(it) }
        }

        val dp = Array(nums1.size + 1) { Array(nums2.size + 1) { null as Int? } }

        fun f(i: Int, j: Int): Int {
            if (i == nums1.size || j == nums2.size) return 0

            if (dp[i][j] != null) return dp[i][j]!!

            val take = f(i + 1, j + 1) + nums1[i] * nums2[j]
            val skipI = f(i + 1, j)
            val skipJ = f(i, j + 1)

            val result = maxOf(take, skipI, skipJ)

            dp[i][j] = result
            return result
        }

        return f(0, 0)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(18, s.maxDotProduct(intArrayOf(2, 1, -2, 5), intArrayOf(3, 0, -6)))
    }

}
