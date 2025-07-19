package problems

import java.util.*

object _132Pattern {
    class Solution {
        fun find132pattern(nums: IntArray): Boolean {
            val byMin = TreeMap<Int, Int>()
            for (n in nums) {
                // check in range
                val floor = byMin.floorEntry(n)
                if (floor != null) {
                    val (k, v) = floor
                    if (k < n && n < v) return true
                }

                val storedMinKey = byMin.firstEntry()?.key
                if (storedMinKey != null && storedMinKey < n) {
                    var minKey = storedMinKey
                    while (minKey != null && minKey < n) {
                        byMin.remove(minKey)
                        minKey = byMin.firstEntry()?.key
                    }
                    byMin[storedMinKey] = n
                } else if (n !in byMin) {
                    byMin[n] = Int.MIN_VALUE
                }

            }

            return false
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
//            println(find132pattern(intArrayOf(1, 2, 3, 4)))
//            println(find132pattern(intArrayOf(3, 1, 4, 2)))
//            println(find132pattern(intArrayOf(-1, 3, 2, 0)))
            println(find132pattern(intArrayOf(-2, 1, 2, -2, 1, 2)))
//            println(find132pattern(intArrayOf(40, 50, 25, 35, 15, 35, 20)))
        }
    }
}