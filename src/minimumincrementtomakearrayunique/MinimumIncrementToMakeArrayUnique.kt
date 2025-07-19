package problems

import java.util.*

object MinimumIncrementToMakeArrayUnique {
    class Solution {
        // You are given an integer array nums. In one move, you can pick an index i where 0 <= i < nums.length and increment nums[i] by 1.
        // Return the minimum number of moves to make every value in nums unique.
        // n <= 10^5
        // 1, 1, 2, 2, 3, 7
        fun minIncrementForUnique(nums: IntArray): Int {
            if (nums.isEmpty()) return 0
            nums.sort()
            var moves = 0
            var lastValue = nums[0]
            for (i in 1 until nums.size) {
                if (nums[i] <= lastValue) {
                    moves += lastValue - nums[i] + 1
                    lastValue++
                } else {
                    lastValue = nums[i]
                }
            }

            return moves
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                minIncrementForUnique(
                    intArrayOf(3, 2, 1, 2, 1, 7)
                )
            )
        }
    }
}