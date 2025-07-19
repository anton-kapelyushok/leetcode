package problems

import java.util.*

object RemovingStarsFromAString {
    class Solution {
        fun removeStars(s: String): String {

            val stack = LinkedList<Char>()
            for (c in s) {
                if (c == '*') {
                    stack.pop()
                } else {
                    stack.push(c)
                }
            }

            return stack.joinToString("").reversed()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(removeStars("leet**cod*e"))
        }
    }

}