package problems

object BestTimeToBuyAndSellStockWithCooldown {
    class Solution {
        fun maxProfit(prices: IntArray): Int {
            class I {
                val dps = Array(prices.size) { null as Int? }
                val dpb = Array(prices.size) { null as Int? }

                fun s(i: Int): Int {
                    if (i >= prices.size) return 0
                    if (dps[i] != null) return dps[i]!!
                    val sellToday = prices[i] + b(i + 2)
                    val holdToday = s(i + 1)
                    val res = maxOf(sellToday, holdToday)
                    dps[i] = res
                    return res
                }

                fun b(i: Int): Int {
                    if (i >= prices.size) return 0
                    if (dpb[i] != null) return dpb[i]!!
                    val buyToday = -prices[i] + s(i + 1)
                    val skipToday = b(i + 1)
                    val res = maxOf(buyToday, skipToday)
                    dpb[i] = res
                    return res
                }
            }

            val i = I()

            return i.b(0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(maxProfit(intArrayOf(1, 2, 3, 0, 2)))
        }
    }
}