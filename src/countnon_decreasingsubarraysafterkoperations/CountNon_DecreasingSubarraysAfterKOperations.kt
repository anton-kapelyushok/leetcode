package countnon_decreasingsubarraysafterkoperations

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // take subarray of nums
    // increment any element in the subarray up to k times
    // return # of subarrays that can be made non decreasing after at most k operations
    fun countNonDecreasingSubarrays(nums: IntArray, k: Int): Long {

        val activeRs = ArrayDeque<Int>() // INVARIANT: increasing monostack

        var result = 0L
        var requiredK = 0L
        var r = nums.size - 1
        for (l in nums.indices.reversed()) {
            // EXPAND:
            // we take nums[l] as the first element.
            // nums[l] requires every element after it to be at least nums[l]
            // For 4 2 1 3 4
            // current monostack is [2 3 4]
            // that means that the current "filled" array is
            // 4 2 2 3 4
            // we need to fill [2,3) with 4s
            // [3,4) with 4s
            // [4,end)
            while (activeRs.isNotEmpty() && nums[activeRs.first()] <= nums[l]) {
                val activeR = activeRs.removeFirst()
                val rightBorderExclusive = activeRs.firstOrNull() ?: (r + 1)
                val width = rightBorderExclusive - activeR
                val heightToFill = nums[l] - nums[activeR]
                requiredK += 1L * width * heightToFill
            }
            activeRs.addFirst(l)

            // SHRINK
            while (requiredK > k) {
                // effectively our array looks like
                // monostack:          2   3   4
                //   indices:          l         r
                // effective values:   2 2 3 3 4 ? ? ?
                //     values:         2 1 3 1 4 2
                // we want to move r to the left
                // hence we either advance the monostack - for free
                // or replenish spent k
                if (r == activeRs.last()) {
                    activeRs.removeLast()
                } else {
                    requiredK -= nums[activeRs.last()] - nums[r]
                }
                r--
            }

            // RECORD
            result += r - l + 1
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.countNonDecreasingSubarrays(intArrayOf(4, 3, 2, 1, 2, 4, 8, 16, 15, 12, 13), 0))
    }

    @Test
    fun test2() {
        assertEquals(17, s.countNonDecreasingSubarrays(intArrayOf(6, 3, 1, 2, 4, 4), 7))
    }

    @Test
    fun test3() {
        assertEquals(12, s.countNonDecreasingSubarrays(intArrayOf(6, 3, 1, 3, 6), 4))
    }

    @Test
    fun test4() {
        assertEquals(4, s.countNonDecreasingSubarrays(intArrayOf(17, 17, 6), 7))
    }

    @Test
    fun test5() {
        assertEquals(4, s.countNonDecreasingSubarrays(intArrayOf(15, 17, 2), 6))
    }

    @Test
    fun test6() {
        assertEquals(6, s.countNonDecreasingSubarrays(intArrayOf(10, 16, 11, 16), 3))
    }

    @Test
    fun test7() {
        assertEquals(9, s.countNonDecreasingSubarrays(intArrayOf(25, 12, 17, 1), 40))
    }

    @Test
    fun test8() {
        assertEquals(12, s.countNonDecreasingSubarrays(intArrayOf(20, 19, 20, 15, 8), 14))
    }

    @Test
    fun test9() {
        assertEquals(8, s.countNonDecreasingSubarrays(intArrayOf(25, 2, 1, 24, 15), 4))
    }

    @Test
    fun test10() {
        assertEquals(12, s.countNonDecreasingSubarrays(intArrayOf(1000000000, 1, 1, 1, 1), 1000000000))
    }
}
