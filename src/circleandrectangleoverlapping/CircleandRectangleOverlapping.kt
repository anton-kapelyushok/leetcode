package circleandrectangleoverlapping

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun checkOverlap(radius: Int, xCenter: Int, yCenter: Int, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        // normalize
        val x1 = x1 - xCenter
        val y1 = y1 - yCenter
        val x2 = x2 - xCenter
        val y2 = y2 - yCenter

        data class Point(val x: Int, val y: Int)
        data class Edge(val p1: Point, val p2: Point)

        val vs = listOf(Point(x1, y1), Point(x2, y1), Point(x2, y2), Point(x1, y2))
        val edges = listOf(Edge(vs[0], vs[1]), Edge(vs[1], vs[2]), Edge(vs[2], vs[3]), Edge(vs[3], vs[0]))

        fun Edge.closestPoint(): Point {
            if (p1.x == p2.x) {
                // p1 0 p2 -> 0
                // 0 p1 p2 -> p1
                // p1 p2 0 -> p2
                val y = listOf(p1.y, p2.y, 0).sorted()[1]
                return Point(p1.x, y)
            } else {
                require(p1.y == p2.y)
                val x = listOf(p1.x, p2.x, 0).sorted()[1]
                return Point(x, p1.y)
            }
        }

        fun Point.isInsideCircle(): Boolean {
            return x * x + y * y <= radius * radius
        }

        fun circleIsInsideRectangle(): Boolean {
            val (x1, x2) = listOf(x1, x2).sorted()
            if (x1 >= -radius || x2 <= radius) return false
            val (y1, y2) = listOf(y1, y2).sorted()
            if (y1 >= -radius || y2 <= radius) return false
            return true
        }

        return circleIsInsideRectangle() || edges.map { it.closestPoint() }.any { it.isInsideCircle() }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.checkOverlap(4, 102, 50, 0, 0, 100, 100))
    }

    @Test
    fun test2() {
        assertEquals(true, s.checkOverlap(2, 102, 50, 0, 0, 100, 100))
    }
}
