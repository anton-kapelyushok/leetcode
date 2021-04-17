package numberofsubmatricesthatsumtotarget

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun numSubmatrixSumTarget(matrix: Array<IntArray>, target: Int): Int {
        val m = matrix.size
        val n = matrix[0].size

        fun m(i: Int, j: Int): Int {
            if (i >= m) return 0
            if (j >= n) return 0
            return matrix[i][j]
        }

        for (i in 0..Math.max(m, n) * 2) {
            for (j in 0..i) {
                val x = m - j
                val y = n - i + j

                if (x !in matrix.indices) continue
                if (y !in matrix[0].indices) continue

                matrix[x][y] = m(x, y) + m(x + 1, y) + m(x, y + 1) - m(x + 1, y + 1)
            }
        }

        var result = 0
        for (i in 0 until m) {
            for (j in 0 until n) {
                for (xl in 1..m - i) {
                    for (yl in 1..n - j) {
                        val r = m(i, j) - m(i + xl, j) - m(i, j + yl) + m(i + xl, j + yl)
                        if (r == target) result++
                    }
                }
            }
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        // [[0,1,0],[1,1,1],[0,1,0]]
        assertEquals(4, s.numSubmatrixSumTarget(arrayOf(
                intArrayOf(0, 1, 0),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 1, 0)), 0))
    }

}
