package trappingrainwater

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun trap(heights: IntArray): Int {
        if (heights.isEmpty()) return 0
        var l = 0
        var r = heights.size - 1
        var hl = heights[l]
        var hr = heights[r]
        var w = 0

        while (l < r) {
            if (heights[l] < heights[r]) {
                if (heights[l] > hl) {
                    hl = heights[l]
                } else {
                    w +=  hl - heights[l]
                }
                l++
            } else {
                if (heights[r] > hr) {
                    hr = heights[r]
                } else {
                    w += hr - heights[r]
                }
                r--
            }
        }

        return w
    }
}

class DpSolution {
    fun trap(heights: IntArray): Int {
        if (heights.isEmpty()) return 0

        val rights = Array(heights.size) { 0 }
        val lefts = Array(heights.size) { 0 }
        rights[rights.size - 1] = heights[heights.size - 1]
        lefts[0] = heights[0]
        for (i in heights.size - 2 downTo 0) {
            rights[i] = Math.max(rights[i + 1], heights[i])
        }

        for (i in 1 until heights.size) {
            lefts[i] = Math.max(lefts[i - 1], heights[i])
        }

        var r = 0
        for (i in heights.indices) {
            val eh = Math.min(lefts[i], rights[i])
            if (heights[i] < eh) r += eh - heights[i]
        }
        return r
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test0() {
        assertEquals(0, s.trap(intArrayOf(1, 2, 3)))
    }

    @Test
    fun test1() {
        assertEquals(6, s.trap(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)))
    }

    @Test
    fun test2() {
        assertEquals(9, s.trap(intArrayOf(4, 2, 0, 3, 2, 5)))
    }

    @Test
    fun test3() {
        assertEquals(83, s.trap(intArrayOf(6, 4, 2, 0, 3, 2, 0, 3, 1, 4, 5, 3, 2, 7, 5, 3, 0, 1, 2, 1, 3, 4, 6, 8, 1, 3)))
    }

    @Test
    fun test4() {
        assertEquals(0, s.trap(intArrayOf()))
    }

    @Test
    fun test5() {
        assertEquals(0, s.trap(intArrayOf(1)))
    }

}
