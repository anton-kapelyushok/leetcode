package constructbinarytreefrompreorderandinordertraversal

import utils.TreeNode

class Solution {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {

        fun f(pl: Int, pr: Int, il: Int, ir: Int): TreeNode? {
            // println("$pl $pr $il $ir")
            if (pr - pl == 0) return null

            val head = TreeNode(preorder[pl])

            val headInOrder = (il until ir).first { inorder[it] == preorder[pl] }
            head.left = f(pl + 1, pl + 1 + headInOrder - il, il, headInOrder)
            head.right = f(pl + headInOrder - il + 1, pr, headInOrder + 1, ir)

            return head
        }

        return f(0, preorder.size, 0, inorder.size)
    }
}
