package problems

import java.util.LinkedList

object MinimumTimeToCollectAllApplesInATree {
    class Solution {
        class Node(val id: Int, val hasApple: Boolean, val children: MutableList<Node> = mutableListOf())

        fun minTime(n: Int, edges: Array<IntArray>, hasApple: List<Boolean>): Int {
            val edgeMap = mutableMapOf<Int, MutableSet<Int>>()

            edges.forEach { (l, r) ->
                edgeMap.computeIfAbsent(l) { mutableSetOf() } += r
                edgeMap.computeIfAbsent(r) { mutableSetOf() } += l
            }

            val nodes = mutableMapOf<Int, Node>()
            fun node(id: Int): Node = nodes.computeIfAbsent(id) { Node(id, hasApple[id]) }

            data class S(val parent: Int, val current: Int)

            val q = LinkedList<S>()
            val visited = mutableSetOf<Int>()
            q.offer(S(-1, 0))

            while (q.isNotEmpty()) {
                val top = q.pop()
                if (top.current in visited) continue
                visited += top.current
                val node = node(top.current)
                for (c in edgeMap[top.current]!!) {
                    if (c == top.parent) continue
                    node.children += node(c)
                    q.offer(S(top.current, c))
                }
            }

            fun f(root: Node?): Int? {
                if (root == null) return null
                val childrenResults = root.children.map { f(it) }
                val childrenWithApplesCount = childrenResults.count { it != null }
                if (childrenWithApplesCount == 0 && !root.hasApple) return null
                return childrenWithApplesCount + childrenResults.sumBy { it ?: 0 }
            }

            return (f(node(0)) ?: 0) * 2
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                minTime(
                    7,
                    arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(0, 2),
                        intArrayOf(1, 4),
                        intArrayOf(1, 5),
                        intArrayOf(2, 3),
                        intArrayOf(2, 6),
                    ),
                    listOf(false, false, true, false, true, true, false)
                )
            )
        }
    }
}