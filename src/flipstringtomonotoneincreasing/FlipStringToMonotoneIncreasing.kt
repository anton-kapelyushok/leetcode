package problems

object FlipStringToMonotoneIncreasing {
    class Solution {
        fun minFlipsMonoIncr(s: String): Int {
            var onesInLeftPart = 0
            var zerosInRightPart = s.count { it == '0' }

            var res = s.length

            for (i in 0..s.length) {
                res = minOf(res, onesInLeftPart + zerosInRightPart)
                if (i in s.indices) {
                    if (s[i] == '1') onesInLeftPart += 1
                    else zerosInRightPart -= 1
                }
            }

            return res
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                minFlipsMonoIncr("00110")
            )
            println(
                minFlipsMonoIncr("010110")
            )
            println(
                minFlipsMonoIncr("00011000")
            )
        }
    }
}