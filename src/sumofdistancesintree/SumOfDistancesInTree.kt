package problems

import java.util.*

object SumOfDistancesInTree {
    class Solution {
        class Node(val n: Int, val children: MutableSet<Node> = mutableSetOf()) {
            override fun toString(): String {
                return "Node[n = $n, #children=${children.size}]"
            }
        }

        fun sumOfDistancesInTree(n: Int, edges: Array<IntArray>): IntArray {
            val tree = buildTree(edges)

            val counts = mutableMapOf<Node, Int>()
            fun calculateCounts(node: Node?): Int {
                if (node == null) return 0
                val sum = node.children.sumOf { calculateCounts(it) } + 1
                counts[node] = sum
                return sum
            }

            val countRoot = calculateCounts(tree)

            fun calculateDistanceDownwards(n: Node?): Int {
                if (n == null) return 0
                return n.children.sumOf { counts[it]!! + calculateDistanceDownwards(it) }
            }

            val uRoot = calculateDistanceDownwards(tree)

            val result = IntArray(n)

            fun f(n: Node, p: Int) {
                val countN = counts[n]!!
                val res = countRoot - countN + p - countN

                n.children.forEach { f(it, res) }

                result[n.n] = res
            }

            f(tree, uRoot + countRoot)

            return result
        }

        fun buildTree(edges: Array<IntArray>): Node {
            val edgeMap = mutableMapOf<Int, MutableSet<Int>>()

            for ((l, r) in edges) {
                edgeMap[l] = edgeMap[l] ?: mutableSetOf()
                edgeMap[r] = edgeMap[r] ?: mutableSetOf()

                edgeMap[l]!! += r
                edgeMap[r]!! += l
            }

            val nodes = mutableMapOf<Int, Node>()
            fun node(v: Int): Node = nodes.computeIfAbsent(v) { Node(v) }

            data class N(val v: Int, val p: Int)

            val q = LinkedList<N>()
            q.offer(N(0, -1))

            val visited = mutableSetOf<Int>()

            while (q.isNotEmpty()) {
                val (v, p) = q.poll()
                if (v in visited) continue
                visited += v
                val node = node(v)

                for (n in edgeMap[v] ?: mutableSetOf()) {
                    if (n == p) continue
                    node.children += node(n)
                    q.offer(N(n, v))
                }
            }

            return node(0)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                sumOfDistancesInTree(
                    n = 6,
                    edges = arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(0, 2),
                        intArrayOf(2, 3),
                        intArrayOf(2, 4),
                        intArrayOf(2, 5)
                    )
                ).toList()
            )
        }
    }
}