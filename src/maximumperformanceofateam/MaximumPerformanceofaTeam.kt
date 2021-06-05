package maximumperformanceofateam

import java.util.*

class Solution {
    val m = 1_000_000_000 + 7

    fun maxPerformance(n: Int, speed: IntArray, efficiency: IntArray, k: Int): Int {
        val sorted = (speed.indices).sortedBy { -efficiency[it] }

        fun s(i: Int): Int {
            return speed[sorted[i]]
        }

        fun e(i: Int): Int {
            return efficiency[sorted[i]]
        }

        val sq = PriorityQueue<Int> { l, r -> l - r }

        var sum = 0L
        var max = 0L

        for (i in speed.indices) {
            val e = e(i)
            sum += s(i)
            sq.offer(s(i))
            if (sq.size > k) {
                sum -= sq.poll()
            }

            val te = e * sum
            if (te > max) max = te
        }

        return (max % m).toInt()
    }
}

