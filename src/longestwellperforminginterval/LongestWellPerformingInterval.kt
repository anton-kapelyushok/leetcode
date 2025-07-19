package problems

import java.util.*

object LongestWellPerformingInterval {
    class Solution {
        fun longestWPI(hours: IntArray): Int {
            val mapped = hours.map { if (it > 8) 1 else -1 }
            val psum = IntArray(hours.size)
            psum[hours.lastIndex] = mapped.last()
            for (i in mapped.lastIndex - 1 downTo 0) {
                psum[i] = psum[i + 1] + mapped[i]
            }

            // r to l decreasing, no push/pop
            val stack = LinkedList<Int>()
            stack.push(hours.size)

            fun psum(i: Int): Int {
                if (i == psum.size) return 0
                if (i in psum.indices) return psum[i]
                error("")
            }

            for (i in hours.lastIndex downTo 0) {
                if (stack.isEmpty() || psum(i) < psum(stack.peek())) {
                    stack.push(i)
                }
            }

            var max = 0
            for (i in psum.indices) {
                while (stack.isNotEmpty() && stack.peek() <= i) stack.pop()
                val current = psum(i)
                while (stack.isNotEmpty()
                    && current - psum(stack.peek()) > 0
                ) {
                    max = maxOf(stack.pop() - i, max)
                }
            }

            return max
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(longestWPI(intArrayOf(9, 9, 6, 0, 6, 6, 9)))
            println(longestWPI(intArrayOf(9, 9, 9)))
        }
    }
}