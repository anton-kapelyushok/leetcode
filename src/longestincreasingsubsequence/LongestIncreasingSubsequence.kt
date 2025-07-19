package problems

object LongestIncreasingSubsequence {
    class Solution {
        fun lengthOfLIS(nums: IntArray): Int {
            // stores last digit of subsequence of length (idx + 1)
            val lengths = mutableListOf<Int>()
            for (n in nums) {
                // find l s.t. lengths[l] >= n
                var l = 0
                var r = lengths.size - 1
                while (l < r) {
                    val m = (l + r) / 2
                    if (lengths[m] >= n) {
                        r = m
                    } else {
                        l = m + 1
                    }
                }
                val notFound = l !in lengths.indices || lengths[l] < n
                if (notFound) {
                    lengths += n
                } else {
                    lengths[l] = n
                }
            }

            return lengths.size
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(lengthOfLIS(intArrayOf(5, 1)))
            println(lengthOfLIS(intArrayOf(10, 9, 2, 5, 3, 7, 101, 18)))
            println(lengthOfLIS(intArrayOf(0, 1, 0, 3, 2, 3)))
            println(lengthOfLIS(intArrayOf(7, 7, 7, 7, 7, 7, 7, 7)))
            println(lengthOfLIS(intArrayOf(1, 2, 5, 6, 6)))
        }
    }
}