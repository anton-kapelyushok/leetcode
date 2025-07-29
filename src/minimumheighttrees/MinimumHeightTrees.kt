package minimumheighttrees;

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals


class Solution {
    fun findMinHeightTrees(n: Int, edges: Array<IntArray>): List<Int> {
        val adj = mutableMapOf<Int, MutableSet<Int>>()

        if (n < 2) return (0 until n).toList()

        for (edge in edges) {
            adj[edge[0]] = (adj[edge[0]] ?: mutableSetOf()).also { it.add(edge[1]) }
            adj[edge[1]] = (adj[edge[1]] ?: mutableSetOf()).also { it.add(edge[0]) }
        }

        while (adj.size > 2) {
            val leaves = adj.keys.filter { (adj[it] ?: mutableSetOf()).size == 1 }
            leaves.forEach { leaf ->
                adj[leaf]!!.forEach { n ->
                    adj[n]!!.remove(leaf)
                }
                adj.remove(leaf)
            }
        }

        return adj.keys.toList()
    }
}

class DfsSolution {
    fun findMinHeightTrees(n: Int, inputEdges: Array<IntArray>): List<Int> {
        val edges = normalizeEdges(inputEdges)
        val edgesByFrom = edges.groupBy { it.from }

        val result = IntArray(n)

        for (i in 0 until n) {
            val visited = mutableSetOf<Int>()
            val q = LinkedList<Pair<Int, Int>>()
            q.offer(i to 0)
            var max = 0

            while (q.isNotEmpty()) {
                val (e, h) = q.poll()
                if (e in visited) continue
                visited += e

                val nextEdges = (edgesByFrom[e] ?: emptyList())
                if (h + 1 > max) max = h + 1
                nextEdges.forEach { edge -> q.offer(edge.to to h + 1) }
            }

            result[i] = max
        }


        val min = result.min()

        return result.indices.filter { result[it] == min }

    }

    fun normalizeEdges(inputEdges: Array<IntArray>): Set<Edge> {
        return inputEdges.flatMap { edge ->
            listOf(
                    Edge(Math.min(edge[0], edge[1]), Math.max(edge[0], edge[1])),
                    Edge(Math.max(edge[0], edge[1]), Math.min(edge[0], edge[1]))
            )
        }.toSet()
    }

    data class Edge(val from: Int, val to: Int)
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(setOf(1), s.findMinHeightTrees(4, arrayOf(intArrayOf(1, 0), intArrayOf(1, 2), intArrayOf(1, 3))).toSet())
    }

}
