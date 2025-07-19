package problems

import java.util.*

object NumberOfVisiblePeopleInAQueue {
    class Solution {
        fun canSeePersonsCount(heights: IntArray): IntArray {
            val stack = LinkedList<Int>()

            val result = IntArray(heights.size)

            for (i in 0 until heights.size) {
                // we are higher than previous guy, increment it, and pop it as it won't see anything else now
                while (stack.isNotEmpty() && heights[stack.peek()] < heights[i]) {
                    result[stack.pop()]++
                }

                // previous higher than us and sees us
                if (stack.isNotEmpty()) {
                    result[stack.peek()]++
                }

                stack.push(i)
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(canSeePersonsCount(intArrayOf(10, 6, 8, 5, 11, 9)).toList())
            println(canSeePersonsCount(intArrayOf(4, 3, 2, 1)).toList())
        }
    }
}