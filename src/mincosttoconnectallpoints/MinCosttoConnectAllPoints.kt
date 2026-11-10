package mincosttoconnectallpoints

import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    class Dsu(val n: Int) {
        val parents = IntArray(n) { it }

        fun union(l: Int, r: Int) {
            parents[parentOf(l)] = parentOf(r)
        }

        fun parentOf(e: Int): Int {
            if (parents[e] == e) {
                return e
            }
            parents[e] = parentOf(parents[e])
            return parents[e]
        }
    }

    fun minCostConnectPoints(points: Array<IntArray>): Int {
        fun d(p1: IntArray, p2: IntArray): Int {
            return kotlin.math.abs(p1[0] - p2[0]) + kotlin.math.abs(p1[1] - p2[1])
        }

        var edgesUsed = 0
        val edges = PriorityQueue<Pair<Int, Int>>(compareBy { (l, r) -> d(points[l], points[r]) })
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                edges += i to j
            }
        }

        val dsu = Dsu(points.size)

        var result = 0
        while (edgesUsed != points.size - 1) {
            val (l, r) = edges.poll()
            if (dsu.parentOf(l) == dsu.parentOf(r)) continue
            dsu.union(l, r)
            result += d(points[l], points[r])
            edgesUsed += 1
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.solve())
    }
}
