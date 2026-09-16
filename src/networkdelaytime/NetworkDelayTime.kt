package networkdelaytime

import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    fun networkDelayTime(times: Array<IntArray>, n: Int, k: Int): Int {
        val byFrom = times.groupBy { (from) -> from }

        data class N(val n: Int, val time: Int)

        val pq = PriorityQueue<N>(compareBy { it.time })
        pq.offer(N(k, 0))

        val visited = mutableMapOf<Int, Int>()

        while (pq.isNotEmpty()) {
            val (n, time) = pq.poll()
            if (n in visited && visited[n]!! <= time) continue
            visited[n] = time

            val edges = byFrom[n] ?: emptyList()
            edges.forEach { (from, to, w) ->
                require(from == n)
                pq.offer(N(to, w + time))
            }
        }
        if (visited.size != n) return -1

        return visited.values.max()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.solve())
    }

}
