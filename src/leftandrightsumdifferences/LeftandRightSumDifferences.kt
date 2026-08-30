package leftandrightsumdifferences

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun leftRightDifference(nums: IntArray): IntArray {
        val lsum = LongArray(nums.size)
        val rsum = LongArray(nums.size)

        for (i in 1 until lsum.size) {
            lsum[i] = lsum[i - 1] + nums[i - 1]
        }

        for (i in rsum.size - 2 downTo 0) {
            rsum[i] = rsum[i + 1] + nums[i + 1]
        }

        return nums.indices.map { kotlin.math.abs(lsum[it] - rsum[it]).toInt() }.toIntArray()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        s.leftRightDifference(intArrayOf(10,4,8,3))
    }

}
