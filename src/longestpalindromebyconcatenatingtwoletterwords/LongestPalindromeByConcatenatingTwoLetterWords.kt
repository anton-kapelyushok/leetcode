package problems

object LongestPalindromeByConcatenatingTwoLetterWords {
    class Solution {
        fun longestPalindrome(words: Array<String>): Int {
            var pairs = 0
            var hasSpareDouble = false

            val m = words.groupingBy { it }.eachCount()
            for ((w, wCount) in m) {
                when {
                    w[0] < w[1] -> {
                        val r = w.reversed()
                        val rCount = m[r] ?: 0
                        pairs += minOf(rCount, wCount)
                    }

                    w[0] == w[1] -> {
                        if (wCount > 0) pairs += wCount / 2
                        if (wCount % 2 == 1) hasSpareDouble = true
                    }
                }
            }

            return pairs * 4 + (if (hasSpareDouble) 2 else 0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(longestPalindrome(arrayOf("lc", "cl", "gg")))
            println(longestPalindrome(arrayOf("ab", "ty", "yt", "lc", "cl", "ab")))
            println(longestPalindrome(arrayOf("cc", "ll", "xx")))
            println(
                longestPalindrome(
                    arrayOf(
                        "aa",
                        "aa",
                        "aa",
                        "bb",
                        "bb",
                        "bb",
                        "cc",
                        "cc",
                        "cc",
                        "dd",
                        "dd",
                        "dd",
                        "dd",
                        "dd",
                    )
                )
            )
            println(
                longestPalindrome(
                    arrayOf(
                        "aa",
                        "aa",
                        "bb",
                        "bb",
                        "cc",
                        "cc",
                        "dd",
                        "dd",
                        "dd",
                        "dd",
                    )
                )
            )
        }
    }
}