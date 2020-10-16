// https://leetcode.com/problems/two-sum/

package twosum

import org.junit.jupiter.api.Test

class Solution {
    fun twoSum(nums: IntArray, targetValue: Int): IntArray {
        val valueToIndices = mutableMapOf<Int, MutableList<Int>>()
        for (i in nums.indices) {
            val present = valueToIndices[nums[i]] ?: mutableListOf()
            present.add(i)
            valueToIndices[nums[i]] = present
        }

        for (leftIndex in nums.indices) {
            val leftValue = nums[leftIndex]
            val rightValue = targetValue - leftValue

            val rightIndices = valueToIndices[rightValue] ?: emptyList<Int>()

            if (rightIndices.isEmpty()) {
                continue
            }

            if (rightIndices[0] != leftIndex) {
                return intArrayOf(leftIndex, rightIndices[0])
            }

            if (rightIndices.size != 1) {
                return intArrayOf(leftIndex, rightIndices[1])
            }
        }

        return intArrayOf()
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test() {
        assert(s.twoSum(intArrayOf(1, 2, 3), 4).contentEquals(intArrayOf(0, 2)))
    }
}
