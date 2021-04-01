package largestuniquenumber;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun largestUniqueNumber(nums: IntArray): Int {
        val metArray = IntArray(1001)
        for (n in nums) {
            metArray[n]++
        }

        var largest = -1
        for (n in nums) {
            if (metArray[n] == 1 && n > largest) largest = n
        }

        return largest
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(8, s.largestUniqueNumber(intArrayOf(5, 7, 3, 9, 4, 9, 8, 3, 1)))
    }

}
