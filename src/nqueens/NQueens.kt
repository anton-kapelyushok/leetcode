package nqueens

class Solution {
    fun solveNQueens(n: Int): List<List<String>> {
        val field = Array(n) { IntArray(n) } // 0 - free, > 1 blocked, -1 - Queen

        val result = mutableListOf<List<String>>()

        fun bt(x: Int) {
            if (x == n) {
                result.add(toResultString(field))
                return
            }

            for (y in 0 until n) {
                if (field[x][y] != 0) {
                    continue
                }
                placeQueen(field, x, y)

                bt(x + 1)

                displaceQueen(field, x, y)
            }
        }

        bt(0)

        return result
    }

    fun placeQueen(field: Array<IntArray>, x: Int, y: Int) {
        field[x][y] = -1
        val n = field.size
        for (i in 0 until n) {
            // hor
            if (field[i][y] != -1) field[i][y]++

            // ver
            if (field[x][i] != -1) field[x][i]++

            // b-r diag
            run {
                val xx = 0 + i
                val yy = y + x - i

                if (xx in 0 until n && yy in 0 until n) {
                    if (field[xx][yy] != -1) field[xx][yy]++
                }
            }
            // t-r diag
            run {
                val xx = 0 + i
                val yy = y - x + i

                if (xx in 0 until n && yy in 0 until n) {
                    if (field[xx][yy] != -1) field[xx][yy]++
                }
            }
        }
    }

    fun displaceQueen(field: Array<IntArray>, x: Int, y: Int) {
        val n = field.size
        for (i in 0 until n) {
            // hor
            if (field[i][y] != -1) field[i][y]--

            // ver
            if (field[x][i] != -1) field[x][i]--

            // b-r diag
            run {
                val xx = 0 + i
                val yy = y + x - i

                if (xx in 0 until n && yy in 0 until n) {
                    if (field[xx][yy] != -1) field[xx][yy]--
                }
            }
            // t-r diag
            run {
                val xx = 0 + i
                val yy = y - x + i

                if (xx in 0 until n && yy in 0 until n) {
                    if (field[xx][yy] != -1) field[xx][yy]--
                }
            }
        }

        field[x][y] = 0
    }


    fun toResultString(field: Array<IntArray>): List<String> {
        val len = field.size
        val res = mutableListOf<String>()
        for (i in 0 until len) {
            val r = StringBuilder()

            for (j in 0 until len) {
                if (field[i][j] == -1) r.append('Q') else r.append('.')
            }

            res.add(r.toString())
        }

        return res
    }
}
