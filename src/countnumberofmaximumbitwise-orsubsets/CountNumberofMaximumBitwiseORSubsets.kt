package countnumberofmaximumbitwise

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun countMaxOrSubsets(nums: IntArray): Int {
        var maxSum = -1
        var count = 0
        fun maxOrSum(i: Int, acc: Int) {
            if (i == nums.size) return
            val newSum = nums[i] or acc
            if (newSum > maxSum) {
                count = 1
                maxSum = newSum
            } else if (newSum == maxSum) {
                count += 1
            }
            maxOrSum(i + 1, newSum)
            maxOrSum(i + 1, acc)
        }
        maxOrSum(0, 0)
        return count
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.countMaxOrSubsets(intArrayOf(3, 1)))
    }

    @Test
    fun test2() {
//        assertEquals(2, s.countMaxOrSubsets(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)))
    }

    @Test
    fun test3() {
        assertEquals(6, s.countMaxOrSubsets(intArrayOf(3,2,1,5)))
    }

}
