package problems

import java.util.*

object NextGreaterElementII {
    class Solution {
        fun nextGreaterElements(nums: IntArray): IntArray {

            val nums2 = (nums + nums)

            val nums3 = IntArray(nums2.size) { -1 }
            // monostack, decreasing, left to right
            val stack = LinkedList<Int>()
            for (i in nums2.indices) {
                while (stack.isNotEmpty() && (nums2[i] > nums2[stack.peek()])) {
                    nums3[stack.pop()] = i
                }
                stack.push(i)
            }

            return nums3
                .take(nums.size)
                .map {
                    if (it == -1) it
                    else nums[it % nums.size]
                }.toIntArray()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(nextGreaterElements(intArrayOf(1, 2, 1)).toList())
            println(nextGreaterElements(intArrayOf(1, 2, 3, 4, 3)).toList())
        }
    }

}