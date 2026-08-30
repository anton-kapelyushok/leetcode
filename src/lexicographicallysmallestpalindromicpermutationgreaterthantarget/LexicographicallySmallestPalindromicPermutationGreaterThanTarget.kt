package lexicographicallysmallestpalindromicpermutationgreaterthantarget

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun lexPalindromicPermutation(s: String, target: String): String {
        val charCounts = IntArray(26)
        s.forEach { c -> charCounts[c - 'a']++ }

        val notEven = charCounts.count { it % 2 != 0 }
        if (notEven > 1) return ""
        val hasMiddle = notEven == 1
        val middle = if (hasMiddle) ('a' + charCounts.indices.find { charCounts[it] % 2 != 0 }!!).toString() else ""

        fun f(i: Int, isBigger: Boolean, charCounts: IntArray, acc: MutableList<Char>): String? {
            if (i == s.length) return null
            if (i >= s.length / 2) {
                // check tail
                if (isBigger) return acc.joinToString("")
                if (hasMiddle && i == s.length / 2) {
                    val middleC = charCounts.indices.find { charCounts[it] == 1 }!!
                    if ('a' + middleC > target[i]) return acc.joinToString("")
                    if ('a' + middleC < target[i]) return null
                    return f(i + 1, isBigger, charCounts, acc)
                }

                val accC = acc[s.length - i - 1]
                if (accC > target[i]) return acc.joinToString("")
                if (accC < target[i]) return null

                return f(i + 1, isBigger, charCounts, acc)
            }

            // accumulating the numbers
            if (isBigger) {
                // already bigger - just take the smallest
                val smallest = charCounts.indices.find { charCounts[it] >= 2 }!!
                acc.add('a' + smallest)
                charCounts[smallest] -= 2
                return f(i + 1, isBigger, charCounts, acc)
            }


            val smallestPossible = charCounts.indices.find { 'a' + it >= target[i] && charCounts[it] >= 2 } ?: return null
            val smallestPossibleC = 'a' + smallestPossible

            acc.add(smallestPossibleC)
            charCounts[smallestPossible] -= 2
            var res = f(i + 1, smallestPossibleC > target[i], charCounts, acc)
            charCounts[smallestPossible] += 2
            acc.removeLast()
            if (res != null) return res

            if (smallestPossibleC == target[i]) {
                val nextPossible = charCounts.indices.find { 'a' + it > target[i] && charCounts[it] >= 2 } ?: return null
                val nextPossibleC = 'a' + nextPossible
                acc.add(nextPossibleC)
                charCounts[nextPossible] -= 2
                res = f(i + 1, true, charCounts, acc)
                charCounts[nextPossible] += 2
                acc.removeLast()
                return res
            }
            return null
        }

        val res = f(0, false, charCounts, mutableListOf()) ?: return ""

        return res + middle + res.reversed()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("baab", s.lexPalindromicPermutation("baba", "abba"))
    }

    @Test
    fun test2() {
        assertEquals("", s.lexPalindromicPermutation("baba", "bbaa"))
    }

    @Test
    fun test3() {
        assertEquals("", s.lexPalindromicPermutation("abc", "abb"))
    }

    @Test
    fun test4() {
        assertEquals("aabaa", s.lexPalindromicPermutation("aaaab", "aaaaa"))
    }

    @Test
    fun test5() {
        assertEquals("abbba", s.lexPalindromicPermutation("babab", "abbbb"))
    }

    @Test
    fun test6() {
        assertEquals("", s.lexPalindromicPermutation("aabb", "baba"))
    }

}
