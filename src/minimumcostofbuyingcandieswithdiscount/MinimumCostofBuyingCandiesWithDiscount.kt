package minimumcostofbuyingcandieswithdiscount

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minimumCost(cost: IntArray): Int {
        cost.sortDescending()
        var b = 0
        var r = 0
        for (c in cost) {
            if (b == 2) {
                b = 0
            } else {
                r += c
                b += 1
            }
        }
        return r
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(5, s.minimumCost(intArrayOf(1, 2, 3)))
    }

}
