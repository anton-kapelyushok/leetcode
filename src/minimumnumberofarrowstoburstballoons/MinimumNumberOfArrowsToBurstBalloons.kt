package problems

import java.util.*

object MinimumNumberOfArrowsToBurstBalloons {
    class Solution {
        fun findMinArrowShots(points: Array<IntArray>): Int {
            if (points.isEmpty()) return 0

            data class Balloon(val l: Int, val r: Int)

            val typedPoints = points.map { (l, r) -> Balloon(l, r) }

            val pointsQueue = typedPoints.sortedWith(compareBy({ it.l }, { it.r })).let { LinkedList(it) }

            var res = 0
            while (pointsQueue.isNotEmpty()) {
                res += 1
                var (_, r) = pointsQueue.poll()
                while (pointsQueue.isNotEmpty()) {
                    val (newL, newR) = pointsQueue.peek()
                    if (newL <= r) {
                        r = minOf(r, newR)
                        pointsQueue.poll()
                    } else {
                        break
                    }
                }
            }

            return res
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                findMinArrowShots(
                    arrayOf(intArrayOf(10, 16), intArrayOf(2, 8), intArrayOf(1, 6), intArrayOf(7, 12))
                )
            )
        }
    }
}