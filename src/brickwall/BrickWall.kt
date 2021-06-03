package brickwall

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun leastBricks(wall: List<List<Int>>): Int {
        val holes = mutableMapOf<Int, Int>()
        for (row in wall) {
            var left = 0
            for (brickIndex in 0..row.size - 2) {
                left += row[brickIndex]
                holes[left] = (holes[left] ?: 0) + 1
            }
        }
        return wall.size - (holes.values.max() ?: 0)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.leastBricks(listOf(
                listOf(1, 2, 2, 1),
                listOf(3, 1, 2),
                listOf(1, 3, 2),
                listOf(2, 4),
                listOf(3, 1, 2),
                listOf(1, 3, 1, 1)
        )))
    }

}
