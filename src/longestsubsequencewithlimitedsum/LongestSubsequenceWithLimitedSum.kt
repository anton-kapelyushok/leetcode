package problems

import java.util.*

object LongestSubsequenceWithLimitedSum {
    class Solution {
        fun answerQueries(nums: IntArray, queries: IntArray): IntArray {
            nums.sort()
            val psum = IntArray(nums.size)
            psum[0] = nums[0]
            for (i in 1 until nums.size) {
                psum[i] = psum[i - 1] + nums[i]
            }

            val map = TreeMap<Int, Int>()
            psum.forEachIndexed { idx, it ->
                map[it] = idx
            }

            return queries
                .map { query ->
                    val e = map.floorEntry(query)
                    if (e == null) 0
                    else e.value + 1
                }
                .toIntArray()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(answerQueries(intArrayOf(4, 5, 2, 1), intArrayOf(3, 10, 21)).toList())
        }
    }
}