package problems

import java.util.*

object MaximumWidthRamp {
    object V1 {
        class Solution {
            fun maxWidthRamp(nums: IntArray): Int {
                val map = TreeMap<Int, Int>()
                var max = 0
                for (i in nums.indices) {
                    // update max
                    val n = nums[i]
                    val floor = map.floorEntry(n)
                    if (floor != null) {
                        val (k, v) = floor
                        max = maxOf(max, i - v)
                    } else {
                        map[n] = i
                    }
                }
                return max
            }
        }
    }

    object V2 {
        class Solution {
            fun maxWidthRamp(nums: IntArray): Int {
                val stack = LinkedList<Int>()

                for (i in nums.indices) {
                    val n = nums[i]
                    if (stack.isEmpty() || n < nums[stack.peek()]) {
                        stack.push(i)
                    }
                }

                var max = 0
                for (j in nums.size - 1 downTo 1) {
                    while (stack.isNotEmpty() && stack.peek() >= j) {
                        stack.pop() // remove indices that are not participating anymore
                    }

                    while (stack.isNotEmpty() && nums[stack.peek()] <= nums[j]) {
                        // remove matched index, because j is rightmost it could be matched with
                        max = maxOf(max, j - stack.pop())
                    }
                }

                return max
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(V1.Solution()) {
            println(maxWidthRamp(intArrayOf(6, 0, 8, 2, 1, 5)))
            println(maxWidthRamp(intArrayOf(9, 8, 1, 0, 1, 9, 4, 0, 4, 1)))
        }
        with(V2.Solution()) {
//            println(maxWidthRamp(intArrayOf(6, 0, 8, 2, 1, 5)))
            println(maxWidthRamp(intArrayOf(9, 8, 1, 0, 1, 9, 4, 0, 4, 1)))
        }
    }
}