package sortthematrixdiagonally;

import java.util.*

class Solution {
    fun diagonalSort(mat: Array<IntArray>): Array<IntArray> {
        val m = mat.size
        val n = mat[0].size

        val arrayToSort = IntArray(Math.max(m, n))

        // iterate over diagonals, starting with lower left corner
        for (startY in 0 until m) {
            val startX = 0
            sortDiagonal(mat, startX, startY, arrayToSort)
        }

        for (startX in 1 until n) {
            val startY = 0
            sortDiagonal(mat, startX, startY, arrayToSort)
        }

        return mat
    }

    fun sortDiagonal(mat: Array<IntArray>, startX: Int, startY: Int, arrayToSort: IntArray) {
        val m = mat.size
        val n = mat[0].size

        var x = startX
        var y = startY
        var i = 0

        while (y < m && x < n) {
            arrayToSort[i++] = mat[y++][x++]
        }

        Arrays.sort(arrayToSort, 0, i)

        x = startX
        y = startY
        i = 0

        while (y < m && x < n) {
            mat[y++][x++] = arrayToSort[i++]
        }
    }
}
