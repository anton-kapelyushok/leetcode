package problems

import java.util.LinkedList

object NearestExitFromEntranceInMaze {
    class Solution {
        fun nearestExit(maze: Array<CharArray>, entrance: IntArray): Int {
            val my = maze.size
            val mx = maze[0].size
            val visited = mutableSetOf<C>()
            val e = C(y = entrance[0], x = entrance[1])
            var q = LinkedList<C>()
            q.offer(e)

            var steps = 0
            while (q.isNotEmpty()) {
                val nextQ = LinkedList<C>()
                while (q.isNotEmpty()) {
                    val n = q.poll()
                    if (n.x !in 0 until mx) continue
                    if (n.y !in 0 until my) continue
                    if (maze[n.y][n.x] == '+') continue // wall
                    if (n in visited) continue
                    visited += n
                    if (n != e) {
                        if (n.x == 0) return steps
                        if (n.x == mx - 1) return steps
                        if (n.y == 0) return steps
                        if (n.y == my - 1) return steps
                    }
                    nextQ.offer(C(n.y + 1, n.x))
                    nextQ.offer(C(n.y - 1, n.x))
                    nextQ.offer(C(n.y, n.x + 1))
                    nextQ.offer(C(n.y, n.x - 1))
                }
                steps++
                q = nextQ
            }

            return -1
        }

        data class C(val y: Int, val x: Int)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                nearestExit(
                    arrayOf(
                        "++.+".toCharArray(),
                        "...+".toCharArray(),
                        "+++.".toCharArray()
                    ),
                    intArrayOf(1, 2)
                )
            )
            println(
                nearestExit(
                    arrayOf(
                        "+++".toCharArray(),
                        "...".toCharArray(),
                        "+++".toCharArray()
                    ),
                    intArrayOf(1, 0)
                )
            )
            println(
                nearestExit(
                    arrayOf(
                        ".+".toCharArray(),
                    ),
                    intArrayOf(0, 0)
                )
            )
        }
    }
}