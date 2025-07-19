package problems

object NumberOfZeroFilledSubarrays {
    // 1 + 2 + .. + n = n (n + 1) / 2
    class Solution {
        fun zeroFilledSubarray(nums: IntArray): Long {
            var result = 0L
            var acc = 0
            for (n in nums) {
                if (n == 0) {
                    acc += 1
                } else {
                    result += (1L * acc * (acc + 1)) / 2
                    acc = 0
                }
            }
            result += (1L * acc * (acc + 1)) / 2
            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(zeroFilledSubarray(intArrayOf(1, 3, 0, 0, 2, 0, 0, 4)))// 6
            println(zeroFilledSubarray(intArrayOf(0, 0, 0, 2, 0, 0)))// 9
            println(zeroFilledSubarray(intArrayOf(2, 10, 2019)))// 0
        }
    }
}