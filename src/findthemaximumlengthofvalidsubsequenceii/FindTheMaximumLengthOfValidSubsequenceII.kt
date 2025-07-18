package findthemaximumlengthofvalidsubsequenceii

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun maximumLength(nums: IntArray, k: Int): Int {
        // dp[i][j] - size of subsequence starting in i with diff j
        for (i in nums.indices) nums[i] %= k
        val search = nums.indices.groupBy { nums[it] }.mapValues { TreeSet(it.value) }

        val dp = Array(nums.size) { IntArray(k) { -1 } }
        fun f(lIdx: Int, diff: Int): Int {
            println("f($lIdx, $diff)")
            if (dp[lIdx][diff] != -1) return dp[lIdx][diff]
            val l = nums[lIdx]
            println("l: $l")
            // l + r = diff
            // r = diff - l
            val r = (diff + k - l) % k
            println("r: $r")
            val rIdx = search[r]?.higher(lIdx)
            val res = if (rIdx == null) 1 else f(rIdx, diff) + 1
            dp[lIdx][diff] = res
            return res
        }


        var maxLength = 0
        for (i in nums.indices) {
            for (diff in 0 until k) {
                maxLength = maxOf(maxLength, f(i, diff))
            }
        }

        return maxLength
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(5, s.maximumLength(intArrayOf(1, 2, 3, 4, 5), 2))
    }

    @Test
    fun test2() {
        assertEquals(4, s.maximumLength(intArrayOf(1, 4, 2, 3, 1, 4), 3))
    }

    @Test
    fun test3() {
        assertEquals(4, s.maximumLength(intArrayOf(1, 4, 2, 3, 1, 4), 3))
    }

    @Test
    fun test4() {
        assertEquals(2, s.maximumLength(intArrayOf(1, 2), 5))
    }
}
