package jumpgame6

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun maxResult(nums: IntArray, k: Int): Int {
        val dp = IntArray(nums.size)
        val pq = PriorityQueue<Int> { l, r -> dp[r] - dp[l] }

        dp[0] = nums[0]

        pq.offer(0)

        for (i in 1 until nums.size) {
            while (pq.peek() < i - k) {
                pq.poll()
            }

            dp[i] = nums[i] + dp[pq.peek()]
            pq.offer(i)
        }

        return dp[nums.size - 1]
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(7, s.maxResult(intArrayOf(1, -1, -2, 4, -7, 3), 2))
    }

    @Test
    fun test2() {
        assertEquals(17, s.maxResult(intArrayOf(10, -5, -2, 4, 0, 3), 3))
    }

}
