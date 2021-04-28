package uniquepaths2

class Solution {
    fun uniquePathsWithObstacles(grid: Array<IntArray>): Int {
        val m = grid.size
        val n = grid[0].size

        for (x in m - 1 downTo 0) {
            for (y in n - 1 downTo 0) {
                when {
                    grid[x][y] == 1 -> grid[x][y] = 0
                    x == m - 1 && y == n - 1 -> grid[x][y] = 1
                    else -> {
                        val right = if (y + 1 < n) grid[x][y + 1] else 0
                        val bottom = if (x + 1 < m) grid[x + 1][y] else 0
                        grid[x][y] = right + bottom
                    }
                }
            }
        }

        return grid[0][0]
    }
}
