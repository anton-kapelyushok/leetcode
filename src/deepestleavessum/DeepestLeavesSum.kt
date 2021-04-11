package deepestleavessum

import utils.TreeNode
import java.util.*

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
    fun deepestLeavesSum(root: TreeNode?): Int {
        if (root == null) return 0
        var max = 0
        var maxDepth = 0


        val q = LinkedList<N>()
        q.offer(N(root, 1))

        while (q.isNotEmpty()) {
            val (node, depth) = q.poll()

            if (depth == maxDepth) max += node.`val`
            else if (depth > maxDepth) {
                max = node.`val`
                maxDepth = depth
            }

            if (node.left != null) q.offer(N(node.left!!, depth + 1))
            if (node.right != null) q.offer(N(node.right!!, depth + 1))
        }

        return max
    }

    data class N(val node: TreeNode, val depth: Int)
}
