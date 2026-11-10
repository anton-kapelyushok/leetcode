package networkdelaytime2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    // times = [source, target, time]
    // from k to every node
    fun networkDelayTime(times: Array<IntArray>, nodesCount: Int, k: Int): Int {
        val edges = times.groupBy { it[0] }
        val minTimes = mutableMapOf<Int, Int>()

        data class N(val n: Int, val t: Int)

        val pq = PriorityQueue<N>(compareBy { it.t })
        pq.add(N(k, t = 0))

        while (pq.isNotEmpty()) {
            val (n, t) = pq.poll()
            if (n in minTimes && minTimes[n]!! <= t) {
                continue
            }
            minTimes[n] = t
            if (minTimes.size == nodesCount) {
                return t
            }

            (edges[n] ?: listOf()).forEach { (source, target, time) ->
                pq.offer(N(target, t = time + t))
            }
        }

        return -1
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            2, s.networkDelayTime(
                arrayOf(
                    intArrayOf(2, 1, 1),
                    intArrayOf(2, 3, 1),
                    intArrayOf(3, 4, 1),
                ),
                nodesCount = 4,
                k = 2,
            )
        )
    }
}
