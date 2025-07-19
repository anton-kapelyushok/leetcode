package problems

object NumberOfGoodPaths {
    class Solution {

        class Dsu(val n: Int) {
            val parents = IntArray(n) { it }

            fun union(l: Int, r: Int) {
                val lParent = parent(l)
                val rParent = parent(r)
                parents[lParent] = rParent
            }

            fun parent(i: Int): Int {
                if (parents[i] == i) return i
                parents[i] = parent(parents[i])
                return parents[i]
            }
        }

        fun numberOfGoodPaths(vals: IntArray, edges: Array<IntArray>): Int {
            val verticesByV = vals.indices.groupBy { vals[it] }

            val edgesByMaxV = edges.groupBy { e ->
                val (l, r) = e
                maxOf(vals[l], vals[r])
            }

            val dsu = Dsu(vals.size)

            var res = vals.size
            for (v in edgesByMaxV.keys.sorted()) {
                val vEdges = edgesByMaxV[v]!!
                for ((l, r) in vEdges) {
                    dsu.union(l, r)
                }
                val countsByChunk = verticesByV[v]!!
                    .groupBy { dsu.parent(it) }
                    .mapValues { (_, vs) -> vs.size }

                for (c in countsByChunk.values) {
                    res += c * (c - 1) / 2
                }
            }

            return res
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                numberOfGoodPaths(
                    intArrayOf(1, 3, 2, 1, 3),
                    arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(1, 2),
                        intArrayOf(2, 3),
                        intArrayOf(2, 4),
                    )
                )
            )

            println(
                numberOfGoodPaths(
                    intArrayOf(1, 1, 2, 2, 3, 2),
                    arrayOf(
                        intArrayOf(0, 1),
                        intArrayOf(1, 2),
                        intArrayOf(2, 3),
                        intArrayOf(2, 4),
                        )
                )
            )
        }
    }
}