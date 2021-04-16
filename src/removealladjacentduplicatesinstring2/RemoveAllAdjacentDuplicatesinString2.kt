package removealladjacentduplicatesinstring2

import java.util.*

class Solution {
    fun removeDuplicates(s: String, k: Int): String {
        val q = LinkedList<LC>()

        for (c in s) {
            if (q.isEmpty() || q.peekLast().l != c) {
                q.offer(LC(c, 0))
            }

            val current = q.peekLast()
            current.c = current.c + 1

            if (current.c == k) {
                q.removeLast()
            }
        }

        val sb = StringBuilder()
        for (lc in q) {
            for (i in 0 until lc.c) {
                sb.append(lc.l)
            }
        }

        return sb.toString()
    }

    data class LC(var l: Char, var c: Int)
}
