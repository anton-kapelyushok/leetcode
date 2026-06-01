package findminimuminrotatedsortedarray

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findMin(nums: IntArray): Int {
        val m = nums.size

        var l = 1
        var r = m - 1

        val first = nums[0]
        if (first < nums.last()) return first

        while (l < r) {
            val m = (l + r) / 2

            if (nums[m] > first) {
                l = m + 1
            } else {
                r = m
            }
        }

        return nums[l]
    }
}
