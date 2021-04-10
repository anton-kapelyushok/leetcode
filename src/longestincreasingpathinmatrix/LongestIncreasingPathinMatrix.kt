package longestincreasingpathinmatrix;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun longestIncreasingPath(matrix: Array<IntArray>): Int {
        val dp = Array(matrix.size) { IntArray(matrix[0].size) }

        fun f(m: Int, n: Int): Int {
            if (dp[m][n] != 0) return dp[m][n]

            val c = matrix[m][n]
            val l = if (m - 1 >= 0 && c < matrix[m - 1][n]) f(m - 1, n) else 0
            val r = if (m + 1 < matrix.size && c < matrix[m + 1][n]) f(m + 1, n) else 0
            val u = if (n - 1 >= 0 && c < matrix[m][n - 1]) f(m, n - 1) else 0
            val d = if (n + 1 < matrix[0].size && c < matrix[m][n + 1]) f(m, n + 1) else 0

            dp[m][n] = arrayOf(l, r, u, d).max()!! + 1
            return dp[m][n]
        }

        var max = 0
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                val v = f(i, j)
                if (v > max) max = v
            }
        }

        return max
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.longestIncreasingPath(arrayOf(
                intArrayOf(9, 9, 4),
                intArrayOf(6, 6, 8),
                intArrayOf(2, 1, 1))))
    }

}
