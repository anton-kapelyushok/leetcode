package problems

import kotlin.math.absoluteValue

object MaxPointsOnALine {
    class Solution {

        data class Point(val x: Int, val y: Int) {
            operator fun minus(other: Point): Point {
                return Point(this.x - other.x, this.y - other.y)
            }
        }

        // ax + by + c = 0
        data class Line(val a: Double, val b: Double, val c: Double)

        fun maxPoints(points: Array<IntArray>): Int {
            val typedPoints = points.map { (x, y) -> Point(x, y) }

            // line can always be drawn between two points
            if (typedPoints.size <= 2) return typedPoints.size
            // special case - all points are the same
            if (typedPoints.toSet().size == 1) return typedPoints.size

            var result = 2
            for (i in typedPoints.indices) {
                for (j in i + 1 until typedPoints.size) {
                    val p1 = typedPoints[i]
                    val p2 = typedPoints[j]
                    val (a, b, c) = line(p1, p2) ?: continue
                    val res = typedPoints.count { p ->
                        (a * p.x + b * p.y + c).absoluteValue <= 0.0001
                    }
                    result = maxOf(res, result)
                }
            }

            return result
        }

        fun line(p1: Point, p2: Point): Line? {
            return when {
                p1 == p2 -> null
                // vertical line: -x = C
                p1.x == p2.x -> Line(1.0, 0.0, -p1.x.toDouble())
                // horizontal line: -y = C
                p1.y == p2.y -> Line(0.0, 1.0, -p1.y.toDouble())

                else -> {
                    val b = -(p1.x - p2.x).toDouble() / (p1.y - p2.y)
                    val c = -(b * p1.y + p1.x)
                    Line(1.toDouble(), b, c)
                }
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                maxPoints(
                    arrayOf(
                        intArrayOf(1, 1),
                        intArrayOf(2, 2),
                        intArrayOf(3, 3),
                        intArrayOf(4, 3)
                    )
                )
            )
        }
    }
}