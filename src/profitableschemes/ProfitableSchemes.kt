package problems

object ProfitableSchemes {
    class Solution {
        fun profitableSchemes(n: Int, minProfit: Int, group: IntArray, profit: IntArray): Int {
            val mod = 1_000_000_000 + 7

            val cache = Array(101) { Array(101) { IntArray(100) { -1 } } }

            fun f(n: Int, p: Int, i: Int): Int {
                if (cache[n][p][i] != -1) return cache[n][p][i]
                // no more days or no more people left
                if (i == profit.size || n == 0) {
                    return if (p >= minProfit) 1
                    else 0
                }

                var res = 0L
                res += f(n, p, i + 1) // don't commit crime today
                res %= mod

                if (n >= group[i]) {
                    res += f(n - group[i], minOf(minProfit, p + profit[i]), i + 1) // commit a crime
                }

                res %= mod
                cache[n][p][i] = res.toInt()
                return res.toInt()
            }
            return f(n, 0, 0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                profitableSchemes(
                    n = 5, minProfit = 3, group = intArrayOf(2, 2), profit = intArrayOf(2, 3)
                )
            )

            println(
                profitableSchemes(n = 10, minProfit = 5, group = intArrayOf(2, 3, 5), profit = intArrayOf(6, 7, 8))
            )

            println(
                profitableSchemes(
                    n = 10,
                    minProfit = 5,
                    group = intArrayOf(
                        66,
                        24,
                        53,
                        49,
                        86,
                        37,
                        4,
                        70,
                        99,
                        68,
                        14,
                        91,
                        70,
                        71,
                        70,
                        98,
                        48,
                        26,
                        13,
                        86,
                        4,
                        82,
                        1,
                        7,
                        51,
                        37,
                        27,
                        87,
                        2,
                        65,
                        93,
                        66,
                        99,
                        28,
                        17,
                        93,
                        83,
                        91,
                        45,
                        3,
                        59,
                        87,
                        92,
                        62,
                        77,
                        21,
                        9,
                        37,
                        11,
                        4,
                        69,
                        46,
                        70,
                        47,
                        28,
                        40,
                        74,
                        47,
                        12,
                        3,
                        85,
                        16,
                        91,
                        100,
                        39,
                        24,
                        52,
                        50,
                        40,
                        23,
                        64,
                        22,
                        2,
                        15,
                        18,
                        62,
                        26,
                        76,
                        3,
                        60,
                        64,
                        34,
                        45,
                        40,
                        49,
                        11,
                        5,
                        8,
                        40,
                        71,
                        12,
                        60,
                        3,
                        51,
                        31,
                        5,
                        42,
                        52,
                        15,
                        36
                    ),
                    profit = intArrayOf(
                        8,
                        4,
                        8,
                        8,
                        9,
                        3,
                        1,
                        6,
                        7,
                        10,
                        1,
                        10,
                        4,
                        9,
                        7,
                        11,
                        5,
                        1,
                        7,
                        4,
                        11,
                        1,
                        5,
                        9,
                        9,
                        5,
                        1,
                        10,
                        0,
                        10,
                        4,
                        1,
                        1,
                        1,
                        6,
                        9,
                        3,
                        6,
                        2,
                        5,
                        4,
                        7,
                        8,
                        5,
                        2,
                        3,
                        0,
                        6,
                        4,
                        5,
                        9,
                        9,
                        10,
                        7,
                        1,
                        8,
                        9,
                        6,
                        0,
                        2,
                        9,
                        2,
                        2,
                        8,
                        6,
                        10,
                        3,
                        4,
                        6,
                        1,
                        10,
                        7,
                        5,
                        4,
                        8,
                        1,
                        8,
                        5,
                        5,
                        4,
                        1,
                        1,
                        10,
                        0,
                        8,
                        0,
                        1,
                        11,
                        5,
                        4,
                        7,
                        9,
                        1,
                        11,
                        1,
                        0,
                        1,
                        6,
                        8,
                        3
                    )
                )
            )
        }
    }
}