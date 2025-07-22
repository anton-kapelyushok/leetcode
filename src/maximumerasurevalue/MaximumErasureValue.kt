package maximumerasurevalue

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maximumUniqueSubarray(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        var l = 0
        var r = 0
        var sum = nums[0]
        var nonUniqueCount = 0
        var counts = mutableMapOf<Int, Int>()
        counts[nums[0]] = 1
        var maxSum = sum


        fun moveL() {
            val v = nums[l]
            sum -= v
            counts[v] = counts[v]!! - 1
            if (counts[v] == 1) nonUniqueCount--
            l++
        }

        fun moveR() {
            r++
            val v = nums[r]
            sum += v
            counts[v] = (counts[v] ?: 0) + 1
            if (counts[v]!! > 1) nonUniqueCount++
        }

        loop@ while (true) {
            when {
                l == nums.size - 1 -> break@loop
                r == nums.size - 1 -> moveL()
                nonUniqueCount > 0 -> moveL()
                else -> moveR()
            }
            if (nonUniqueCount == 0 && sum > maxSum) {
                maxSum = sum
//                println("sum: $sum: ${(l..r).map { nums[it] }}")
            }
        }

        return maxSum
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(8, s.maximumUniqueSubarray(intArrayOf(5, 2, 1, 2, 5, 2, 1, 2, 5)))
    }

    @Test
    fun test2() {
        assertEquals(17, s.maximumUniqueSubarray(intArrayOf(4, 2, 4, 5, 6)))
    }
}
