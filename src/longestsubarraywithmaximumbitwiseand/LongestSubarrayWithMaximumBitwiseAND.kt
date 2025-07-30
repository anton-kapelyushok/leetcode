package longestsubarraywithmaximumbitwiseand

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun longestSubarray(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        val max = nums.max()
        var curConsequent = 0
        var maxConsequent = 0
        for (n in nums) {
            if (n == max) curConsequent++
            else curConsequent = 0
            maxConsequent = maxOf(maxConsequent, curConsequent)
        }
        return maxConsequent
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.longestSubarray(intArrayOf(1, 2, 3, 3, 2, 2)))
    }

}
