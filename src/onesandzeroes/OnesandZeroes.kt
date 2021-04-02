package onesandzeroes;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class Solution {
    fun findMaxForm(strs: Array<String>, m: Int, n: Int): Int {
        val pairs = strs.map { P(it.count { c -> c == '0' }, it.count { c -> c == '1' }) }.toTypedArray()
        val dp = Array(m + 1) { Array(n + 1) { 0 } }

        for (p in pairs) {
            for (mm in m downTo 0) {
                for (nn in n downTo 0) {
                    if (p.zeros <= mm && p.ones <= nn) {
                        dp[mm][nn] = Math.max(dp[mm][nn], dp[mm - p.zeros][nn - p.ones] + 1)
                    } else {
                        dp[mm][nn] = dp[mm][nn]
                    }
                }
            }
        }

        return dp[m][n]
    }

    class P(val zeros: Int, val ones: Int)
}


class N3SolutionBottomUp {
    fun findMaxForm(strs: Array<String>, m: Int, n: Int): Int {
        val pairs = strs.map { P(it.count { c -> c == '0' }, it.count { c -> c == '1' }) }.toTypedArray()

        val dp = Array(pairs.size + 1) { Array(m + 1) { Array(n + 1) { 0 } } }

        for (i in 1..pairs.size) {
            val p = pairs[i - 1]
            for (mm in 0..m) {
                for (nn in 0..n) {
                    if (p.zeros <= mm && p.ones <= nn) {
                        dp[i][mm][nn] = Math.max(dp[i - 1][mm][nn], dp[i - 1][mm - p.zeros][nn - p.ones] + 1)
                    } else {
                        dp[i][mm][nn] = dp[i - 1][mm][nn]
                    }
                }
            }
        }

        return dp[strs.size][m][n]
    }

    class P(val zeros: Int, val ones: Int)
}

class N3Solution {
    fun findMaxForm(strs: Array<String>, m: Int, n: Int): Int {
        val pairs = strs.map { P(it.count { c -> c == '0' }, it.count { c -> c == '1' }) }

        val dp = Array(pairs.size + 1) { Array(m + 1) { Array(n + 1) { null as Int? } } }
        val res = findMax(0, pairs, m, n, dp)
        return res
    }

    fun findMax(start: Int, pairs: List<P>, m: Int, n: Int, dp: Array<Array<Array<Int?>>>): Int {
        if (dp[start][m][n] != null) return dp[start][m][n]!!
        if (start == pairs.size) {
            dp[start][m][n] = 0
            return dp[start][m][n]!!
        }
        if (m == 0 && n == 0) {
            dp[start][m][n] = 0
            return dp[start][m][n]!!
        }

        val p = pairs[start]
        val zeros = p.zeros
        val ones = p.ones

        val r1 = if (zeros > m || ones > n) 0 else 1 + findMax(start + 1, pairs, m - zeros, n - ones, dp)
        val r2 = findMax(start + 1, pairs, m, n, dp)

        dp[start][m][n] = Math.max(r1, r2)
        return dp[start][m][n]!!
    }

    class P(val zeros: Int, val ones: Int)
}

class SolutionTest {

    private val s = N3SolutionBottomUp()

    @Test
    fun test1() {
        assertEquals(4, s.findMaxForm(arrayOf("10", "0001", "1", "0"), 5, 3))
    }

    @Test
    fun test2() {
        assertEquals(5, s.findMaxForm(arrayOf("10", "0001", "111001", "1", "0"), 50, 50))
    }

    @Test
    fun test3() {
        assertEquals(29, s.findMaxForm(arrayOf("11", "11", "0", "0", "10", "1", "1", "0", "11", "1", "0", "111", "11111000", "0", "11", "000", "1", "1", "0", "00", "1", "101", "001", "000", "0", "00", "0011", "0", "10000"), 90, 66))
    }

    @Test
    fun test4() {
        assertEquals(3, s.findMaxForm(arrayOf("11111", "100", "1101", "1101", "11000"), 5, 7))
    }

    @Test
    fun test5() {
        assertEquals(60, s.findMaxForm(arrayOf("1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0"), 30, 30))
    }

}
