package findallanagramsinastring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findAnagrams(s: String, p: String): List<Int> {
        if (s.length < p.length) return listOf()
        val letterCounts = IntArray('z' - 'a' + 1) { 0 }
        var diff = 0
        for (c in p) {
            letterCounts[c - 'a']--
            if (letterCounts[c - 'a'] == -1) {
                diff++
            }
        }

        for (i in p.indices) {
            val c = s[i]
            letterCounts[c - 'a']++
            if (letterCounts[c - 'a'] == 0) {
                diff--
            }
            if (letterCounts[c - 'a'] == 1) {
                diff++
            }
        }

        val result = mutableListOf<Int>()
        // TODO check the right border
        // we are supposed to check [...., s.length - 1] at the last step
        for (i in 0 until s.length - p.length + 1) {
//            println("$i: $diff")
            if (diff == 0) result += i

            // TODO: probably a last step?
            if (i == s.length - p.length) break

            val left = s[i]
            letterCounts[left - 'a']--
            if (letterCounts[left - 'a'] == 0) diff--
            if (letterCounts[left - 'a'] == -1) diff++

            val right = s[i + p.length]
            letterCounts[right - 'a']++
            if (letterCounts[right - 'a'] == 0) diff--
            if (letterCounts[right - 'a'] == 1) diff++
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf(0, 6), s.findAnagrams("cbaebabacd", "abc"))
    }

    @Test
    fun test2() {
        assertEquals(listOf(0, 1, 2), s.findAnagrams("abab", "ab"))
    }

    @Test
    fun test3() {
        assertEquals(listOf(1), s.findAnagrams("baa", "aa"))
    }

    @Test
    fun test4() {
        assertEquals(listOf(0), s.findAnagrams("ab", "ba"))
    }

}
