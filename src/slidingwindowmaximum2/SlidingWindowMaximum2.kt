package slidingwindowmaximum2

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        data class V(val idx: Int, val v: Int)

        val result = IntArray(nums.size - k + 1)

        val maxes = LinkedList<V>()
        var l = 0
        for (r in nums.indices) {
            val v = nums[r]
            // EXPAND
            while (maxes.isNotEmpty() && maxes.peekFirst().v <= v) {
                maxes.pollFirst()
            }
            maxes.offerFirst(V(r, v))

            // SHRINK
            while (r - l + 1 > k) {
                l++
            }
            while (maxes.isNotEmpty() && maxes.peekLast().idx < l) {
                maxes.pollLast()
            }

            require(maxes.isNotEmpty())

            // RECORD
            if (r - l + 1 == k) {
                result[l] = maxes.peekLast().v
            }
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(intArrayOf(), s.maxSlidingWindow(intArrayOf(), 1))
    }
}
