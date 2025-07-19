package problems

object CountCompleteTreeNodes {
    class Solution {
        fun countNodes(root: TreeNode?): Int {
            if (root == null) return 0

            fun h(node: TreeNode?): Int {
                if (node == null) return 0
                return h(node.left) + 1
            }

            val h = h(root) // height of the tree

            if (h == 1) return 1

            val n = 1 shl (h - 1) // max elements in the last row

            fun e(i: Int): TreeNode? {
                fun e(node: TreeNode?, l: Int): TreeNode? {
                    if (l == h) return node
                    node!!
                    val m = i and (1 shl (h - l - 1))
                    return if (m != 0) e(node.right, l + 1)
                    else e(node.left, l + 1)
                }

                return e(root, 1)
            }

            var l = 0 
            var r = n
            while (l < r) {
                val m = (l + r) / 2
                if (e(m) == null) {
                    r = m
                } else {
                    l = m + 1
                }
            }

            return l + (1 shl (h - 1)) - 1
        }
    }

    data class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    @JvmStatic
    fun main(args: Array<String>) {



        with(Solution()) {
            println(countNodes(
                TreeNode(-1).apply {
                    left = TreeNode(-1).apply {
                        left = TreeNode(0)
                        right = TreeNode(1)
                    }
                    right = TreeNode(-1).apply {
                        left = TreeNode(2)
//                        right = TreeNode(3)
                    }
                }
            ))
        }
    }
}

