package problems

object MaximumNumberOfVisiblePoints {
    class Solution {
        fun visiblePoints(points: List<List<Int>>, angle: Int, location: List<Int>): Int {
            if (angle == 360) return points.size
            if (points.isEmpty()) return 0

            val (alwaysVisible, sometimesVisible) = points.partition { it[1] == location[1] && it[0] == location[0] }
            if (sometimesVisible.isEmpty()) return alwaysVisible.size

            val angles = sometimesVisible.map { toAngle(location, it) }.sorted()
            fun angle(i: Int): Double = angles[i % angles.size]

            var max = 1
            val fov = Math.toRadians(angle.toDouble())

            var l = 0 // inclusive
            var r = 1 // exclusive

            while (l != angles.size) {
                while (r != l + angles.size && angle(r).inside(angle(l), angle(l) + fov)) {
                    r++
                    max = maxOf(max, r - l)
                }
                l++
            }

            return max + alwaysVisible.size
        }

        fun toAngle(location: List<Int>, point: List<Int>): Double {
            return Math.atan2((location[1] - point[1]).toDouble(), (location[0] - point[0]).toDouble()) + Math.PI
        }

        fun Double.inside(left: Double, right: Double): Boolean {
            var that = this
            if (that < left) {
                that += Math.PI * 2
            }
            return right >= that
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                visiblePoints(
                    listOf(listOf(2, 1), listOf(2, 2), listOf(3, 3)),
                    angle = 90,
                    location = listOf(1, 1)
                )
            ) // 3
            println(
                visiblePoints(
                    listOf(listOf(1, 0), listOf(2, 1)),
                    angle = 13,
                    location = listOf(1, 1)
                )
            )
            println(
                visiblePoints(
                    listOf(listOf(1, 1), listOf(2, 2), listOf(3, 3), listOf(4, 4), listOf(1, 2), listOf(2, 1)),
                    angle = 0,
                    location = listOf(1, 1)
                )
            )
        }
    }
}