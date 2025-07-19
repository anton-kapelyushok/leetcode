package problems

object MaximumNumberOfDartsInsideOfACircularDartboard {
    class Solution {

        data class Point(val x: Double, val y: Double)

        fun d(p1: Point, p2: Point): Double {
            return Math.sqrt(Math.pow(p2.x - p1.x, 2.0) + Math.pow(p2.y - p1.y, 2.0))
        }

        fun numPoints(darts: Array<IntArray>, r: Int): Int {
            if (darts.size < 2) return darts.size
            val points = darts.map { (x, y) -> Point(x.toDouble(), y.toDouble()) }

            var res = 1

            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val p1 = points[i]
                    val p2 = points[j]

                    val m = Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2)

                    val d = d(p1, p2)
                    if (d > r * 2) continue

                    val h = Math.sqrt(Math.pow(r.toDouble(), 2.0) - Math.pow(d / 2, 2.0))

                    val c1 = Point(
                        m.x + h * (p1.y - p2.y) / d,
                        m.y + h * (p2.x - p1.x) / d
                    )

                    val c2 = Point(
                        m.x - h * (p1.y - p2.y) / d,
                        m.y - h * (p2.x - p1.x) / d
                    )

                    var res1 = 0
                    var res2 = 0

                    for (p in points) {
                        if (d(p, c1) <= r + 0.000001) res1++
                        if (d(p, c2) <= r + 0.000001) res2++
                    }

                    res = maxOf(res, res1, res2)
                }
            }

            return res
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                numPoints(
                    arrayOf(
                        intArrayOf(-2, 0),
                        intArrayOf(2, 0),
                        intArrayOf(0, 2),
                        intArrayOf(0, -2),
                    ),
                    2
                )
            )
            println(
                numPoints(
                    arrayOf(
                        intArrayOf(4, 5),
                        intArrayOf(-4, 1),
                        intArrayOf(-3, 2),
                        intArrayOf(-4, 0),
                        intArrayOf(0, 2),
                    ),
                    5
                )
            )

            println(
                numPoints(
                    arrayOf(
                        intArrayOf(-2, 0),
                        intArrayOf(2, 0)
                    ),
                    1
                )
            )
        }
    }
}