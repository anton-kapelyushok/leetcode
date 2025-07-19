package problems

object DetermineIfTwoStringsAreClose {

    class Solution {
        fun closeStrings(word1: String, word2: String): Boolean {
            val m1 = word1.toList().groupingBy { it }.eachCount()
            val m2 = word2.toList().groupingBy { it }.eachCount()

            if (m1.keys != m2.keys) return false
            if (m1.values.sorted() != m2.values.sorted()) return false

            return true
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(closeStrings("abc", "bca"))
            println(closeStrings("a", "aa"))
            println(closeStrings("cabbba", "abbccc"))
        }
    }

}