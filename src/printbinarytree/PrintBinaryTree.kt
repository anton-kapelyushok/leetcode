package problems

object PrintBinaryTree {

    class Solution {
        fun printTree(root: TreeNode?): List<List<String>> {
            val h = h(root)
            return t(root, h)
        }

        fun h(node: TreeNode?): Int {
            if (node == null) return 0
            return maxOf(h(node.left), h(node.right)) + 1
        }

        fun t(node: TreeNode?, h: Int): List<List<String>> {
            if (h == 1) return listOf(listOf(v(node)))
            val lt = t(node?.left, h - 1)
            val rt = t(node?.right, h - 1)
            val result = mutableListOf<List<String>>()
            result += List(lt[0].size) { "" } + v(node) + List(rt[0].size) { "" }
            for (i in lt.indices) {
                result += lt[i] + "" + rt[i]
            }
            return result
        }

        fun v(node: TreeNode?): String {
            return node?.`val`?.toString() ?: ""
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(printTree(
                TreeNode(1).apply {
                    left = TreeNode(2).apply {
                        right = TreeNode(4)
                    }
                    right = TreeNode(3)
                }
            ))
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}