package problems

object AppendCharactersToStringToSubsequence {
    class Solution {
        fun appendCharacters(s: String, t: String): Int {
            var l = 0
            var r = 0
            while (l < s.length && r < t.length) {
                if (s[l] == t[r]) {
                    l += 1
                    r += 1
                } else {
                    l += 1
                }
            }
            return t.length - r
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(appendCharacters("coaching", "coding")) // 4
            println(appendCharacters("coaching", "coaing")) // 0
            println(appendCharacters("z", "abcde")) // 5
        }
    }
}