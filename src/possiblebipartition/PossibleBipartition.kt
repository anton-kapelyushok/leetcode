package problems

import java.util.*

object PossibleBipartition {
    class Solution {

        class Dsu(val n: Int) {
            val parents = IntArray(n + 1) { it }

            fun parentOf(i: Int): Int {
                if (parents[i] == i) return i
                parents[i] = parentOf(parents[i])
                return parents[i]
            }

            fun merge(l: Int, r: Int) {
                val lParent = parentOf(l)
                val rParent = parentOf(r)
                if (lParent == rParent) return
                parents[lParent] = parents[rParent]
            }
        }

        fun possibleBipartition(n: Int, dislikes: Array<IntArray>): Boolean {
            val dsu = Dsu(n)
            for ((l, r) in dislikes) {
                dsu.merge(l, r)
            }

            val groups = (1..n).groupBy { dsu.parentOf(it) }

            val edges = mutableMapOf<Int, MutableSet<Int>>()
            dislikes.forEach { (l, r) ->
                edges[l] = edges[l] ?: mutableSetOf()
                edges[r] = edges[r] ?: mutableSetOf()

                edges[l]!! += r
                edges[r]!! += l
            }

            val GREEN = -1
            val RED = 1

            fun canPaint(group: List<Int>): Boolean {
                data class N(val n: Int, val color: Int)

                val q = LinkedList<N>()
                val visited = mutableMapOf<Int, Int>()

                q.offer(N(group[0], RED))

                while (q.isNotEmpty()) {
                    val (node, colorToPaint) = q.poll()
                    if (node in visited) {
                        if (visited[node] == colorToPaint) continue
                        else return false
                    }
                    visited[node] = colorToPaint

                    for (next in edges[node] ?: mutableSetOf()) {
                        val nextColor = -colorToPaint
                        q.offer(N(next, nextColor))
                    }
                }
                return true
            }

            return groups.all { canPaint(it.value) }
        }
    }
}