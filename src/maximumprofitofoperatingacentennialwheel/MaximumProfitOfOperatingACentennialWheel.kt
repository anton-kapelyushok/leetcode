package maximumprofitofoperatingacentennialwheel;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun minOperationsMaxProfit(customers: IntArray, boardingCost: Int, runningCost: Int): Int {
        var maxProfit = 0
        var maxRotations = -1
        var profit = 0
        var customersWaiting = 0

        var d = 0
        while (customersWaiting > 0 || d in customers.indices) {
            if (d in customers.indices) {
                customersWaiting += customers[d]
            }
            val customersBoarded = Math.min(customersWaiting, 4)
            customersWaiting -= customersBoarded
            profit += customersBoarded * boardingCost
            profit -= runningCost
            if (profit > maxProfit) {
                maxProfit = profit
                maxRotations = d + 1
            }
            d++
        }

        return maxRotations
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.minOperationsMaxProfit(intArrayOf(8, 3), 5, 6))
    }

    @Test
    fun test2() {
        assertEquals(9, s.minOperationsMaxProfit(intArrayOf(10,10,6,4,7), 3, 8))
    }
}
