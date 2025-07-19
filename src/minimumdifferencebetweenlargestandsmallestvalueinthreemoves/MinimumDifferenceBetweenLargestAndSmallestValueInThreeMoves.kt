package problems

object MinimumDifferenceBetweenLargestAndSmallestValueInThreeMoves {
    class Solution {
        fun minDifference(nums: IntArray): Int {
            if (nums.size <= 3) return 0
            nums.sort()

            fun f(n: Int, l: Int, r: Int): Int {
                if (n == 0) {
                    return nums[r] - nums[l]
                }
                return minOf(
                    f(n - 1, l + 1, r),
                    f(n - 1, l, r - 1)
                )
            }

            return f(3, 0, nums.size - 1)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(minDifference(intArrayOf(5, 3, 2, 4))) // 0
            println(minDifference(intArrayOf(1, 5, 0, 10, 14))) // 1
            println(minDifference(intArrayOf(3, 100, 20))) // 0
        }
    }
}