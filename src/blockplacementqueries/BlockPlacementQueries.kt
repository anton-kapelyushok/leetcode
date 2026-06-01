package blockplacementqueries

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.measureTime

class Solution {
    val mx = 50001

    sealed class Query {
        data class PlaceObstacle(val x: Int) : Query()

        data class CheckCanPlaceBlock(val x: Int, val size: Int) : Query()
    }

    // max gap length stored on [l, r]
    class Node(
        val l: Int,
        val r: Int,
        var maxGap: Int = 0,
        var left: Node? = null,
        var right: Node? = null,
    )

    fun getResults(queries: Array<IntArray>): List<Boolean> {
        val tQueries = queries.map {
            if (it[0] == 1) Query.PlaceObstacle(it[1])
            else Query.CheckCanPlaceBlock(it[1], it[2])
        }

        fun build(l: Int, r: Int): Node {
            if (l == r) return Node(l, r, 0)
            val m = (l + r) / 2
            val ln = build(l, m)
            val rn = build(m + 1, r)
            val n = Node(l, r, 0, ln, rn)
            return n
        }

        // set gap at [i]
        fun update(n: Node, i: Int, newGap: Int) {
            if (n.l == n.r) {
                check(i == n.l)
                n.maxGap = newGap
                return
            }
            val left = n.left!!
            val right = n.right!!
            when (i) {
                in left.l..left.r -> update(left, i, newGap)
                in right.l..right.r -> update(right, i, newGap)
                else -> error("???")
            }
            n.maxGap = maxOf(left.maxGap, right.maxGap)
        }

        // get maxGap[l,r]
        fun query(n: Node, l: Int, r: Int): Int {
            // no overlap
            if (n.l > r) return 0
            if (n.r < l) return 0

            // full overlap
            if (n.l == l && n.r == r) return n.maxGap

            // leaf
            if (n.l == n.r) {
                return n.maxGap
            }

            return maxOf(
                query(n.left!!, l, r),
                query(n.right!!, l, r)
            )
        }

        val root = build(0, mx)

        val obstacles = TreeSet<Int>()
        obstacles += 0
        obstacles += mx
        update(root, mx, mx)

        fun putObstacle(x: Int) {
            val left = obstacles.floor(x)
            val right = obstacles.ceiling(x)

            obstacles += x

            update(root, x, x - left)
            update(root, right, right - x)
        }


        fun canInsert(x: Int, size: Int): Boolean {
            val maxClosedGap = query(root, 0, x)
            if (maxClosedGap >= size) return true

            val left = obstacles.floor(x)
            val openGap = x - left
            if (openGap >= size) return true

            return false
        }

        val result = mutableListOf<Boolean>()



        for (q in tQueries) {
            when (q) {
                is Query.PlaceObstacle -> {
                    putObstacle(q.x)
                }

                is Query.CheckCanPlaceBlock -> {
                    result += canInsert(q.x, q.size)
                }
            }
        }

        return result
    }
}

class Solution1 {
    sealed class Query {
        data class PlaceObstacle(val x: Int) : Query()

        data class CheckCanPlaceBlock(val x: Int, val size: Int) : Query()
    }

    data class Interval(val start: Int, val end: Int) {
        fun size(): Int {
            return end - start
        }

        fun split(m: Int): Pair<Interval, Interval> {
            return Interval(start, m) to Interval(m, end)
        }
    }

    fun getResults(queries: Array<IntArray>): List<Boolean> {
        val tQueries = queries.map {
            if (it[0] == 1) Query.PlaceObstacle(it[1])
            else Query.CheckCanPlaceBlock(it[1], it[2])
        }


        class Node(
            val lx: Int, val rx: Int,
            var max: Int,
            var height: Int,

            var lNode: Node? = null,
            var rNode: Node? = null,
        ) {
            fun isLeaf(): Boolean {
                return lNode == null && rNode == null
            }

            override fun toString(): String {
                if (isLeaf()) {
                    return "[$lx,$rx]"
                } else {
                    return "[$lx,$rx [$lNode][$rNode]]"
                }
            }
        }

        fun place(n: Node, x: Int) {
            if (n.isLeaf()) {
                n.lNode = Node(n.lx, x, x - n.lx, 1)
                n.rNode = Node(x, n.rx, n.rx - x, 1)
                n.max = maxOf(x - n.lx, n.rx - x)
            } else {
                check(n.lNode != null)
                check(n.rNode != null)
                check(n.lNode!!.rx == n.rNode!!.lx)
                check(n.lNode!!.rx != x)

                if (x < n.lNode!!.rx) {
                    place(n.lNode!!, x)
                } else {
                    place(n.rNode!!, x)
                }

                n.max = maxOf(n.lNode!!.max, n.rNode!!.max)
                n.height = maxOf(n.lNode!!.height, n.rNode!!.height) + 1
            }
        }

        fun check(n: Node, end: Int, size: Int): Boolean {
            if (n.max < size) return false
            if (end <= n.lx) return false

            if (n.isLeaf()) {
                val nSize = minOf(end, n.rx) - n.lx
                return nSize >= size
            } else {
                return check(n.lNode!!, end, size) || check(n.rNode!!, end, size)
            }
        }

        var root = Node(0, Int.MAX_VALUE, Int.MAX_VALUE, 1)

        fun rebuild(blocks: List<Int>): Node {
            val root = Node(0, Int.MAX_VALUE, Int.MAX_VALUE, 1)

            fun f(l: Int, r: Int) {
                if (l > r) return

                val m = (l + r) / 2
                place(root, blocks[m])

                f(l, m - 1)
                f(m + 1, r)
            }

            f(0, blocks.lastIndex)
            return root
        }

        val blocks = mutableListOf<Int>()

        val result = mutableListOf<Boolean>()
        var i = 0
//        println("start building ${System.currentTimeMillis()}")
        var start = System.currentTimeMillis()
        for (q in tQueries) {
            when (q) {
                is Query.PlaceObstacle -> {
                    blocks += q.x
                    place(root, q.x)
                    if (blocks.size > 1) {
                        val limit = kotlin.math.ceil(kotlin.math.log2(blocks.size.toDouble() + 1) * 64).toInt()

                        if (root.height > limit) {
                            blocks.sort()
//                            println("rebuild ${root.height} ${blocks.size} limit=$limit")
                            root = rebuild(blocks)
//                            println("after ${root.height}")
                        }
                    }
                }

                is Query.CheckCanPlaceBlock -> {
//                    if (i++ == 0) println("start checking  ${System.currentTimeMillis() - start}")
                    result += check(root, q.x, q.size)
                }
            }
        }

//        println("complete ${System.currentTimeMillis() - start}")

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        with(s) {
            val r = s.getResults(
                arrayOf(
                    intArrayOf(1, 2),
                    intArrayOf(2, 3, 3),
                    intArrayOf(2, 3, 1),
                    intArrayOf(2, 2, 2),
                )
            )

            println(r.toList().toString())
        }
    }

    @Test
    fun test2() {
        with(s) {
            val r = s.getResults(
                arrayOf(
                    intArrayOf(1, 7),
                    intArrayOf(2, 7, 6),
                    intArrayOf(1, 2),
                    intArrayOf(2, 7, 5),
                    intArrayOf(2, 7, 6),
                )
            )

            println(r.toList().toString())
        }
    }

    @Test
    fun test3() {
        with(s) {
            val r = s.getResults(
                arrayOf(
                    intArrayOf(1, 1),
                    intArrayOf(1, 11),
                    intArrayOf(1, 4),
                    intArrayOf(1, 8),
                    intArrayOf(2, 13, 7),
                )
            )

            println(r.toList().toString())
        }
    }

    @Test
    fun test4() {
        val text = Path("/Users/Anton.Kapeliushok/Projects/leetcode/src/blockplacementqueries/testcase_743.txt").readText()
        val data: Array<IntArray> =
            text.removePrefix("[[")
                .removeSuffix("]]")
                .split("],[")
                .map { part ->
                    part.split(",")
                        .map(String::toInt)
                        .toIntArray()
                }
                .toTypedArray()
        with(s) {
            val r = s.getResults(
                data
            )


//            println(r.toList().toString())
        }


        println(measureTime { Solution().getResults(data) })
        println(measureTime { Solution1().getResults(data) })
    }

}
