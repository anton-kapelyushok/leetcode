package houserobberiii

import org.junit.jupiter.api.Test

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution {

    // root = entrance
    // two directly linked houses
    // maximum amount of money
    fun rob(root: TreeNode?): Int {

        val dp = mutableMapOf<Pair<TreeNode, Boolean>, Int>()
        fun f(node: TreeNode?, visitedParent: Boolean): Int {
            if (node == null) {
                return 0
            }

            if (node to visitedParent in dp) {
                return dp[node to visitedParent]!!
            }

            var result = 0
            if (!visitedParent) {
                // can take this one
                val l = f(node.left, true)
                val r = f(node.right, true)
                result = maxOf(result, node.`val` + l + r)
            }
            val l = f(node.left, false)
            val r = f(node.right, false)
            result = maxOf(result, l + r)

            dp[node to visitedParent] = result
            return result
        }

        return f(root, false)
    }
}


class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}


class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.solve())
    }
}
