package isgraphbipartite

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        val n = graph.size


        val notColored = mutableSetOf<Int>()
        (0 until n).forEach { notColored += it }

        val colors = IntArray(n)

        data class N(val i: Int, val color: Int)

        while (notColored.isNotEmpty()) {
            val q = LinkedList<N>()
            q.offer(N(notColored.first(), 1))
            while (q.isNotEmpty()) {
                val (i, color) = q.pop()
                if (colors[i] != 0) {
                    if (colors[i] != color) {
                        // visited with a different color, cannot color the graph
                        return false
                    } else {
                        // visited and colored the same color, skip
                        continue
                    }
                }

                colors[i] = color
                notColored -= i

                for (next in graph[i]) {
                    q.offer(N(next, -color))
                }
            }
        }
        return true
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }
}
