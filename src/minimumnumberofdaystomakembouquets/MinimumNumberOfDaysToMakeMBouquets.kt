package minimumnumberofdaystomakembouquets

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class Solution {
    fun minDays(roses: IntArray, n: Int, k: Int): Int {
        var r = roses.max()
        var l = 0

        while (l < r) {
            val m = (l + r) / 2
            if (countRoses(roses, k , m) < n) {
                l = m + 1
            } else {
                r = m
            }
        }

        return if (countRoses(roses, k, l) < n) -1 else l
    }

    fun countRoses(roses: IntArray, k: Int, d: Int): Int {
        var count = 0
        var current = 0

        for (i in roses.indices) {
            if (roses[i] <= d) {
                current++
                if (current == k) {
                    count++
                    current = 0
                }
            } else {
                current = 0
            }
        }

        return count
    }
}

class DpSolution {
    fun minDays(roses: IntArray, n: Int, k: Int): Int {
        val windowedRoses = roses.toList()
                .windowed(k, 1)
                .map { it.max() }
                .toIntArray()

        val dp = Array(n + 1) { IntArray(windowedRoses.size) }
        val res = (windowedRoses.indices).minOfOrNull { calc(it, windowedRoses, k, n, dp) }!!

        return if (res == Int.MAX_VALUE) -1 else res
    }

    fun calc(s: Int, r: IntArray, k: Int, n: Int, dp: Array<IntArray>): Int {
        if (dp[n][s] != 0) return dp[n][s]

        if (n == 1) {
            var min = Int.MAX_VALUE
            for (i in s until r.size) if (min > r[i]) min = r[i]
            dp[n][s] = min
            return dp[n][s]
        } else {
            var tmp = Int.MAX_VALUE
            for (i in s until r.size - k) {
                val c = Math.max(r[i], calc(i + k, r, k, n - 1, dp))
                tmp = Math.min(c, tmp)
            }
            dp[n][s] = Math.max(tmp, s)
        }

        return dp[n][s]
    }
}


class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.minDays(intArrayOf(1, 2, 4, 9, 3, 4, 1), 2, 2))
    }

    @Test
    fun test2() {
        assertEquals(3, s.minDays(intArrayOf(1, 10, 3, 10, 2), 3, 1))
    }

    @Test
    fun test3() {
        assertEquals(-1, s.minDays(intArrayOf(1, 10, 3, 10, 2), 3, 2))
    }
}
