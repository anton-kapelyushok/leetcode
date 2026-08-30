package shortestpathinagridwithobstacleselimination

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun shortestPath(grid: Array<IntArray>, k: Int): Int {
        val m = grid.size
        val n = grid[0].size

        data class Node(val y: Int, val x: Int, val k: Int)

        val visited = mutableSetOf<Node>()
        var q = LinkedList<Node>()
        q += Node(0, 0, k)

        var step = 0
        while (q.isNotEmpty()) {
            val nextQ = LinkedList<Node>()
            for (node in q) {
                if (node in visited) continue
                visited += node
                val (y, x, k) = node
                if (y == m - 1 && x == n - 1) return step

                val nextNodes = arrayOf(
                    Node(y + 1, x, k),
                    Node(y - 1, x, k),
                    Node(y, x + 1, k),
                    Node(y, x - 1, k),
                )

                for (nextNode in nextNodes) {
                    if (nextNode.y !in grid.indices) continue
                    if (nextNode.x !in grid[0].indices) continue
                    if (grid[nextNode.y][nextNode.x] == 0) {
                        nextQ += nextNode
                    } else if (k > 0) {
                        nextQ += Node(nextNode.y, nextNode.x, k - 1)
                    }
                }
            }
            q = nextQ
            step++
        }
        return -1
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(0, s.shortestPath(arrayOf(), 0))
    }

}
