package problems

object ReverseVowelsOfAString {
    class Solution {
        fun reverseVowels(s: String): String {
            val vowels = "aeiouAEIOU".toSet()
            val rev = s.filter { it in vowels }.reversed()
            var i = 0
            return s.toCharArray().map { c ->
                if (c in vowels) rev[i++]
                else c
            }.let { String(it.toCharArray()) }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

    }
}