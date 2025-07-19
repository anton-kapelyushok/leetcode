package problems

object RestoreTheArray {
    class Solution {
        val mod = 1_000_000_000 + 7

        fun numberOfArrays(s: String, k: Int): Int {

            val dp = Array(s.length) { IntArray(Math.log10(k.toDouble()).toInt() + 2) { -1 } }

            fun f(i: Int, acc: Long): Int {
                if (i == s.length) return 1

                val logAcc = if (acc == 0L) 0 else (Math.log10(acc.toDouble()).toInt() + 1)
                if (dp[i][logAcc] != -1) {
                    return dp[i][logAcc]
                }
                val c = s[i].toInt() - '0'.toInt()
//                if (c == 0 && acc == 0) return 0
                var res = 0L
                // attach this one
                if (acc * 10 + c <= k) {
                    res += f(i + 1, acc * 10 + c)
                }
                res %= mod
                // start over
                if (acc != 0L && c != 0 && c <= k) {
                    res += f(i + 1, c.toLong())
                }
                res %= mod
                dp[i][logAcc] = res.toInt()
                return res.toInt()
            }

            return f(0, 0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(numberOfArrays("1000", 1000))
            println(numberOfArrays("1000", 10))
            println(numberOfArrays("1317", 2000))
            println(
                numberOfArrays(
                    "407780786171321121429620765476840275495357129574174233567552131453038760763182952432348422252546559691171161181440370120987634895458140447952079749439961325982629462531738374032416182281868731817661954890417245087359968833257550123324827240537957646002494771036413572415",
                    823924906
                )
            )
        }
    }
}