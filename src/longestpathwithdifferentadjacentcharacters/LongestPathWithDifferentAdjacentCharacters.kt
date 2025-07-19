package problems

object LongestPathWithDifferentAdjacentCharacters {
    class Solution {

        class Node(val id: Int, val v: Char, val children: MutableList<Node> = mutableListOf())
        data class Maximums(val global: Int, val endingInRoot: Int)

        fun longestPath(parent: IntArray, s: String): Int {
            val root = buildTree(parent, s)
            return treeMaximums(root).global
        }

        private fun treeMaximums(node: Node): Maximums {
            val childMaximums = node.children.associateWith { treeMaximums(it) }
            val possibleContinuations = childMaximums
                .filterKeys { it.v != node.v }
                .values
                .map { it.endingInRoot }
                .sortedBy { -it }

            val maxThroughThis = possibleContinuations.take(2).sum() + 1
            val maxEndingInRoot = possibleContinuations.take(1).sum() + 1
            val maxGlobal = maxOf(
                maxThroughThis,
                childMaximums.values.maxOfOrNull { it.global } ?: 0
            )
            return Maximums(maxGlobal, maxEndingInRoot)
        }

        private fun buildTree(parent: IntArray, s: String): Node {
            val nodes = mutableMapOf<Int, Node>()
            fun node(id: Int) = nodes.computeIfAbsent(id) { Node(id, s[id]) }
            val root = node(0)
            for (i in 1 until parent.size) {
                val p = node(parent[i])
                p.children += node(i)
            }
            return root
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                longestPath(
                    intArrayOf(-1, 0, 0, 1, 1, 2),
                    "abacbe"
                )
            )

            println(
                longestPath(
                    intArrayOf(-1, 0, 0, 0),
                    "aabc"
                )
            )
        }
    }
}