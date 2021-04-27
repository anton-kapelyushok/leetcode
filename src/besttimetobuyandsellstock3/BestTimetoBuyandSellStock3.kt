package besttimetobuyandsellstock3

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maxProfit(prices: IntArray): Int {

        val (start, end) = maxProfit1(prices, 0, prices.size - 1)

        val leftCase = run {
            when (start) {
                0 -> prices[end] - prices[start]
                else -> {
                    val (l, r) = maxProfit1(prices, 0, start - 1)
                    return@run prices[end] - prices[start] + prices[r] - prices[l]
                }
            }
        }

        val rightCase = run {
            when (end) {
                prices.size - 1 -> prices[end] - prices[start]
                else -> {
                    val (l, r) = maxProfit1(prices, end + 1, prices.size - 1)
                    return@run prices[end] - prices[start] + prices[r] - prices[l]
                }
            }
        }

        val insideCase = run {
            val mins = IntArray(end - start + 1)

            var currentMin = prices[end]
            for (i in end downTo start) {
                if (prices[i] < currentMin) {
                    currentMin = prices[i]
                }
                mins[i - start] = currentMin
            }


            val maxs = IntArray(end - start + 1)

            var currentMax = prices[start]
            for (i in start..end) {
                if (prices[i] > currentMax) {
                    currentMax = prices[i]
                }
                maxs[i - start] = currentMax
            }

            var maxProfit = 0
            for (i in start until end) {
                val profit = maxs[i - start] - prices[start] + prices[end] - mins[i - start + 1]
                if (profit > maxProfit) maxProfit = profit
            }

            maxProfit
        }

        return intArrayOf(leftCase, rightCase, insideCase).max()!!
    }


    fun maxProfit1(prices: IntArray, start: Int, end: Int): Pair<Int, Int> {
        if (start == end) return start to end

        var minPrice = prices[start]
        var minIndex = start
        var minI = start

        var maxProfit = 0
        var maxIndex = start
        for (i in start..end) {
            if (prices[i] < minPrice) {
                minPrice = prices[i]
                minI = i
            }
            if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice
                minIndex = minI
                maxIndex = i
            }
        }

        return minIndex to maxIndex
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.maxProfit(intArrayOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun test2() {
        assertEquals(0, s.maxProfit(intArrayOf(5, 4, 3, 2, 1)))
    }

    @Test
    fun test3() {
        assertEquals(6, s.maxProfit(intArrayOf(3, 3, 5, 0, 0, 3, 1, 4)))
    }

    @Test
    fun test4() {
        assertEquals(7, s.maxProfit(intArrayOf(3, 3, 5, 0, 0, 4, 1, 4)))
    }

    @Test
    fun test5() {
        assertEquals(7, s.maxProfit(intArrayOf(3, 2, 6, 5, 0, 3)))
    }

    @Test
    fun test6() {
        assertEquals(11, s.maxProfit(intArrayOf(2, 1, 4, 5, 2, 9, 7)))
    }

}
