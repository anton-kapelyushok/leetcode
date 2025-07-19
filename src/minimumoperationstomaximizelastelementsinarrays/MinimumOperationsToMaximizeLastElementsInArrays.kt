package problems

import kotlin.math.min

object MinimumOperationsToMaximizeLastElementsInArrays {

    class Solution {
        fun minOperations(nums1: IntArray, nums2: IntArray): Int {
            fun f(m1: Int, m2: Int): Int? {
                var c = 0
                for (i in 0 until nums1.size - 1) {
                    val n1 = nums1[i]
                    val n2 = nums2[i]
                    if (n1 <= m1 && n2 <= m2) continue
                    if (n1 <= m2 && n2 <= m1) {
                        c++
                        continue
                    }
                    return null
                }
                return c
            }

            val m1 = nums1.last()
            val m2 = nums2.last()
            val r1 = (f(m1, m2) ?: return -1)
            val r2 = (f(m2, m1) ?: return -1) + 1

            return minOf(r1, r2)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(minOperations(intArrayOf(1, 2, 7), intArrayOf(4, 5, 3)))
            println(minOperations(intArrayOf(2, 3, 4, 5, 9), intArrayOf(8, 8, 4, 4, 4)))
            println(minOperations(intArrayOf(1, 5, 4), intArrayOf(2, 5, 3)))
        }
    }
}