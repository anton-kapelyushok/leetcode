package problems

object NumberOfWaysToFormATargetStringGivenADictionary {
    class Solution {
        fun numWays(words: Array<String>, target: String): Int {
            val mod = 1_000_000_000 + 7
            val ws = words[0].length
            val dp = Array(target.length) { IntArray(ws) { -1 } }
            val counts = words[0].indices.associateWith { idx ->
                words.map { it[idx] }.groupBy { it }.mapValues { (_, v) -> v.size }
            }

            fun f(i: Int, j: Int): Int {
                if (i == target.length) return 1
                if (j == ws) return 0
                if (dp[i][j] != -1) return dp[i][j]
                val c = target[i]
                val count = counts[j]!![c] ?: 0
                var res = 0L
                res += f(i, j + 1)
                if (count != 0) res += count.toLong() * f(i + 1, j + 1)
                res %= mod
                dp[i][j] = res.toInt()
                return dp[i][j]
            }


            return f(0, 0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println()
        }
    }
}