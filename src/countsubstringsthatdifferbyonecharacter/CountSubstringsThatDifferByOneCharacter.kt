package problems

object CountSubstringsThatDifferByOneCharacter {

    class Solution {
        fun countSubstrings(s: String, t: String): Int {
            val dp = Array(s.length) { Array(t.length) { IntArray(2) { -1 } } }

            fun f(l: Int, r: Int, hasReplaces: Int): Int {
                if (l !in s.indices) return 0
                if (r !in t.indices) return 0
                if (dp[l][r][hasReplaces] >= 0) return dp[l][r][hasReplaces]
                val result = when {
                    s[l] != t[r] && hasReplaces == 0 -> 0
                    s[l] != t[r] && hasReplaces == 1 -> 1 + f(l + 1, r + 1, 0)
                    s[l] == t[r] && hasReplaces == 0 -> 1 + f(l + 1, r + 1, 0)
                    s[l] == t[r] && hasReplaces == 1 -> f(l + 1, r + 1, 1)

                    else -> error("")
                }
                dp[l][r][hasReplaces] = result
                return result
            }

            var result = 0
            for (l in s.indices) {
                for (r in t.indices) {
                    result += f(l, r, 1)
                }
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(countSubstrings("aba", "baba")) // 6
            println(countSubstrings("ab", "bb")) // 3
            println(countSubstrings("abe", "bbc")) // 10
            println(countSubstrings("be", "bc")) // 4
            println(countSubstrings("computer", "computation")) // 90

            println(countSubstrings("ccxcc", "ccycc")) // 25
        }
    }
}