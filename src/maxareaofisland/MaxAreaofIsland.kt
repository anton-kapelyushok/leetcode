package maxareaofisland

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {

        var lastN = 1

        val sizes = mutableMapOf<Int, Int>()
        val q = LinkedList<QEntry>()

        for (x in grid.indices) {
            for (y in grid[0].indices) {
                q.offer(QEntry(x, y, 0))
            }
        }

        while (q.isNotEmpty()) {
            val (x, y, n) = q.pop()

            if (grid[x][y] != 1) continue

            val _n = if (n == 0) ++lastN else n
            sizes[_n] = (sizes[_n] ?: 0) + 1
            grid[x][y] = _n

            if (x - 1 in grid.indices) q.push(QEntry(x - 1, y, _n))
            if (x + 1 in grid.indices) q.push(QEntry(x + 1, y, _n))
            if (y - 1 in grid[0].indices) q.push(QEntry(x, y - 1, _n))
            if (y + 1 in grid[0].indices) q.push(QEntry(x, y + 1, _n))
        }

        return sizes.values.max() ?: 0
    }

    data class QEntry(
            val x: Int,
            val y: Int,
            val n: Int
    )
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.maxAreaOfIsland(arrayOf(intArrayOf(1, 1, 0, 0, 0), intArrayOf(1, 1, 0, 0, 0), intArrayOf(0, 0, 0, 1, 1), intArrayOf(0, 0, 0, 1, 1))))
    }

}
