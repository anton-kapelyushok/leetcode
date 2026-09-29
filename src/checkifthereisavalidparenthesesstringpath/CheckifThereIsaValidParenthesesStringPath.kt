package checkifthereisavalidparenthesesstringpath

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun hasValidPath(grid: Array<CharArray>): Boolean {

        val dp = Array(grid.size) { Array(grid[0].size) { IntArray(grid.size + grid[0].size) { -1 } } }

        fun f(y: Int, x: Int, open: Int): Boolean {
            if (y !in grid.indices) return false
            if (x !in grid[0].indices) return false
            if (open == 0 && grid[y][x] == ')') return false
            if (dp[y][x][open] != -1) return false

            val newOpen = if (grid[y][x] == '(') open + 1 else open - 1
            if (y == grid.lastIndex && x == grid.last().lastIndex && newOpen == 0) return true
            val result = f(y, x + 1, newOpen) || f(y + 1, x, newOpen)
            if (result) return true
            dp[y][x][open] = 0
            return false
        }

        return f(0, 0, 0)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(false, s.hasValidPath(arrayOf(charArrayOf('(', ')'), charArrayOf('(', ')'))))
    }
}
