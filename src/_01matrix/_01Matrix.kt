package problems

object _01Matrix {
    class Solution {
        fun updateMatrix(mat: Array<IntArray>): Array<IntArray> {
            val result = Array(mat.size) { IntArray(mat[0].size) { -1 } }

            fun f(x: Int, y: Int): Int {
                if (x !in mat.indices || y !in mat[0].indices) return 10_000
                if (result[x][y] >= 0) return result[x][y]
                if (mat[x][y] == 0) {
                    result[x][y] = 0
                    return 0
                }
                result[x][y] = minOf(
                    minOf(f(x + 1, y), f(x - 1, y)),
                    minOf(f(x, y + 1), f(x, y - 1))
                ) + 1
                return result[x][y]
            }

            for (x in mat.indices) {
                for (y in mat[0].indices) {
                    f(x, y)
                }
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {}
    }
}