package rotateimage

import org.junit.jupiter.api.Test

class Solution {
    fun rotate(matrix: Array<IntArray>): Unit {
        if (matrix.size % 2 == 1) {
            val m = matrix.size
            val c = m / 2
            for (r in 0..c) {
                val left = c - r
                val top = c - r
                val bottom = c + r
                val right = c + r
                for (i in 0 until r * 2) {
                    val tmp = matrix[left + i][top]
                    matrix[left + i][top] = matrix[right][top + i]
                    matrix[right][top + i] = matrix[right - i][bottom]
                    matrix[right - i][bottom] = matrix[left][bottom - i]
                    matrix[left][bottom - i] = tmp
                }
            }
        } else {
            val m = matrix.size
            val c = m / 2 - 1
            for (r in 0..c) {
                val left = c - r
                val top = c - r
                val bottom = c + r + 1
                val right = c + r + 1

                for (i in 0..r * 2) {
                    val tmp = matrix[left + i][top]
                    matrix[left + i][top] = matrix[right][top + i]
                    matrix[right][top + i] = matrix[right - i][bottom]
                    matrix[right - i][bottom] = matrix[left][bottom - i]
                    matrix[left][bottom - i] = tmp
                }
            }
        }
    }
}

