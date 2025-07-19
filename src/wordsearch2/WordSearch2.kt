package problems

object WordSearch2 {

    class Solution {
        fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
            class BTree(var word: String? = null, val children: MutableMap<Char, BTree> = mutableMapOf())

            val root = BTree()
            for (word in words) {
                var cur = root
                for (c in word) {
                    cur = cur.children.computeIfAbsent(c) { BTree() }
                }
                cur.word = word
            }

            val result = mutableSetOf<String>()

            for (i in board.indices) {
                for (j in board[0].indices) {
                    fun c(i: Int, j: Int): Char? {
                        return if (i !in board.indices || j !in board[0].indices) null
                        else board[i][j]
                    }

                    fun dfs(i: Int, j: Int, cur: BTree, path: MutableSet<Pair<Int, Int>>) {
                        if (i to j in path) return
                        if (cur.word != null) {
                            result += cur.word!!
                        }
                        path += i to j
                        listOf(
                            i + 1 to j,
                            i - 1 to j,
                            i to j + 1,
                            i to j - 1,
                        ).forEach { (ii, jj) ->
                            val c = c(ii, jj) ?: return@forEach
                            if (c !in cur.children) return@forEach
                            dfs(ii, jj, cur.children[c]!!, path)
                        }
                        path -= i to j
                    }

                    val c = board[i][j]
                    if (c !in root.children) continue
                    dfs(i, j, root.children[c]!!, mutableSetOf())
                }
            }

            return result.toList()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                findWords(
                    board = listOf(
                        "oaan",
                        "etae",
                        "ihkr",
                        "iflv"
                    ).map { it.toCharArray() }.toTypedArray(),
                    words = arrayOf("oath", "pea", "eat", "rain")
                )
            )
            println(
                findWords(
                    board = listOf(
                        "a",
                        "a"
                    ).map { it.toCharArray() }.toTypedArray(),
                    words = arrayOf("aaa")
                )
            )
        }
    }
}