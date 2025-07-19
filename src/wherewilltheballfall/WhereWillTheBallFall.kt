package problems

object WhereWillTheBallFall {

    class Solution {
        fun findBall(grid: Array<IntArray>): IntArray {
            val w = grid[0].size
            fun p(row: Int, col: Int): Int? {
                if (col !in grid[0].indices) return null
                return grid[row][col]
            }

            val result = IntArray(grid[0].size)
            outer@ for (startingPos in 0 until w) {
                var pos = startingPos
                for (row in grid.indices) {
                    val nextPos = pos + p(row, pos)!!
                    if (p(row, nextPos) != p(row, pos)) continue@outer
                    pos = nextPos
                }
                result[startingPos] = 1
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
        }
    }
}