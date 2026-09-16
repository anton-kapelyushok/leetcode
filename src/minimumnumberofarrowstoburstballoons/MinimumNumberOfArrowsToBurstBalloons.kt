package minimumnumberofarrowstoburstballoons

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun findMinArrowShots(points: Array<IntArray>): Int {
        if (points.isEmpty()) return 0

        val pq = PriorityQueue<IntArray>(compareBy { it[1] })
        points.forEach { pq += it }

        var lastArrow = pq.peek()[1]
        var arrowsShot = 1

        while (pq.isNotEmpty()) {
            val b = pq.poll()
            if (lastArrow in b[0]..b[1]) {
                continue
            }
            lastArrow = b[1]
            arrowsShot++
        }

        return arrowsShot
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(0, s.findMinArrowShots(arrayOf()))
    }

}
