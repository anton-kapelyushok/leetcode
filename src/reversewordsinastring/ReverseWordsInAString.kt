package problems

object ReverseWordsInAString {
    class Solution {
        fun reverseWords(s: String): String {
            return s.split(" ")
                .filter { it.isNotBlank() }
                .asReversed()
                .joinToString(" ")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(reverseWords("the sky is blue"))
        }
    }

}