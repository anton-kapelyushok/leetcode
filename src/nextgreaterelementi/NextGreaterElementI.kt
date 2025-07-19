package problems

import java.util.*

object NextGreaterElementI {
    class Solution {
        fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {

            val nums3 = IntArray(nums2.size) { -1 }
            // monostack, decreasing, left to right
            val stack = LinkedList<Int>()
            for (i in nums2.indices) {
                while (stack.isNotEmpty() && (nums2[i] > nums2[stack.peek()])) {
                    nums3[stack.pop()] = i
                }
                stack.push(i)
            }

            val rev = nums2.indices.associate { idx -> nums2[idx] to idx }
            return nums1.map {
                val idx = nums3[rev[it]!!]
                if (idx == -1) -1
                else nums2[idx]
            }.toIntArray()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(nextGreaterElement(intArrayOf(4, 1, 2), intArrayOf(1, 3, 4, 2)).toList())
            println(nextGreaterElement(intArrayOf(2, 4), intArrayOf(1, 2, 3, 4)).toList())
            println(nextGreaterElement(intArrayOf(1, 3, 4, 2, 5), intArrayOf(1, 3, 4, 2, 5)).toList())
            println(nextGreaterElement(intArrayOf(1, 3, 5, 2, 4), intArrayOf(5, 4, 3, 2, 1)).toList())
        }
    }

}