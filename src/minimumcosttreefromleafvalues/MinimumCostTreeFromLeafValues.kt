package minimumcosttreefromleafvalues

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun mctFromLeafValues(arr: IntArray): Int {

        // construct the tree on range [l..r] where r is root
        class Tree(
            val sumOfNonLeaves: Int,
            val maxLeaf: Int,
        )

        val dp = Array(arr.size) { Array(arr.size) { null as Tree? } }

        fun constructTree(l: Int, r: Int): Tree {
            if (l == r) { // is leaf
                return Tree(
                    sumOfNonLeaves = 0,
                    maxLeaf = arr[l],
                )
            }
            if (dp[l][r] != null) return dp[l][r]!!

            var minTree: Tree? = null
            for (m in l until r) {
                val left = constructTree(l, m)
                val right = constructTree(m + 1, r)

                val v = left.maxLeaf * right.maxLeaf
                val tree = Tree(
                    sumOfNonLeaves = v + left.sumOfNonLeaves + right.sumOfNonLeaves, // this one we want to minimise actually
                    maxLeaf = maxOf(left.maxLeaf, right.maxLeaf), // the same for every tree
                )
                if (minTree == null || minTree.sumOfNonLeaves > tree.sumOfNonLeaves) {
                    minTree = tree
                }
            }

            dp[l][r] = minTree
            return minTree!!
        }

        return constructTree(0, arr.size - 1).sumOfNonLeaves
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(32, s.mctFromLeafValues(intArrayOf(6, 2, 4)))
    }

    @Test
    fun test2() {
        assertEquals(1456, s.mctFromLeafValues(intArrayOf(7, 5, 12, 2, 2, 3, 13, 8, 4, 9, 12, 9, 3, 10, 4, 13, 7, 5, 15)))
    }
}
