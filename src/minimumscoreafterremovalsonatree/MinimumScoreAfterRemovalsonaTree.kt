package minimumscoreafterremovalsonatree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minimumScore(nums: IntArray, edges: Array<IntArray>): Int {
        val edgeMap = mutableMapOf<Int, MutableSet<Int>>()
        edges.forEach { (l, r) ->
            edgeMap[l] = (edgeMap[l] ?: mutableSetOf()).also { it += r }
            edgeMap[r] = (edgeMap[r] ?: mutableSetOf()).also { it += l }
        }

        return edges.minOf { edge ->
            edgeMap[edge[0]]!! -= edge[1]
            edgeMap[edge[1]]!! -= edge[0]
            val sum = calculateScoreWithoutEdge(nums, edgeMap, edge)

            edgeMap[edge[0]]!! += edge[1]
            edgeMap[edge[1]]!! += edge[0]

            sum
        }
    }

    private fun calculateScoreWithoutEdge(
        nums: IntArray,
        edgeMap: MutableMap<Int, MutableSet<Int>>,
        edge: IntArray,
    ): Int {
        class TreeNode(
            val i: Int,
            val v: Int,
            val sum: Int = 0,
            val children: MutableSet<TreeNode> = mutableSetOf<TreeNode>()
        ) {
            override fun toString(): String {
                return "$v${children.takeIf { it.isNotEmpty() }?.joinToString(",")?.let { "($it)" } ?: ""}"
            }
        }

        fun constructTree(i: Int, used: MutableSet<Int> = mutableSetOf()): TreeNode? {
            if (i in used) return null
            used += i
            val children = edgeMap[i]!!.mapNotNull { constructTree(it, used) }.toMutableSet()
            val sum = children.fold(nums[i]) { acc, next -> acc xor next.sum }
            return TreeNode(i = i, v = nums[i], sum = sum, children = children)
        }

        val lTree = constructTree(edge[0])!!
        val rTree = constructTree(edge[1])!!

        var minDiff = Int.MAX_VALUE

        fun updateDiff(sum1: Int, sum2: Int, sum3: Int) {
            val max = maxOf(sum1, sum2, sum3)
            val min = minOf(sum1, sum2, sum3)
            val diff = max - min
            minDiff = minOf(minDiff, diff)
        }

        fun traverse(otherSum: Int, treeSum: Int, node: TreeNode?) {
            if (node == null) return
            node.children.forEach { child ->
                val sum1 = child.sum
                val sum2 = treeSum xor child.sum
                updateDiff(otherSum, sum1, sum2)
                traverse(otherSum, treeSum, child)
            }
        }

        traverse(rTree.sum, lTree.sum, lTree)
        traverse(lTree.sum, rTree.sum, rTree)

        return minDiff
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            9, s.minimumScore(
                intArrayOf(1, 5, 5, 4, 11), arrayOf(
                    intArrayOf(0, 1),
                    intArrayOf(1, 2),
                    intArrayOf(1, 3),
                    intArrayOf(3, 4),
                )
            )
        )
    }

    @Test
    fun test2() {
        assertEquals(
            0, s.minimumScore(
                intArrayOf(5, 5, 2, 4, 4, 2), arrayOf(
                    intArrayOf(0, 1),
                    intArrayOf(1, 2),
                    intArrayOf(5, 2),
                    intArrayOf(4, 3),
                    intArrayOf(1, 3),
                )
            )
        )
    }

}
