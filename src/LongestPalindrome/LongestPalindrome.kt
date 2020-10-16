package LongestPalindrome

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun longestPalindrome(s: String): String {
        if (s.isEmpty()) return s

        var maxLeft = 0
        var maxRight = 0

        for (i in s.indices) {
            // i is center
            var left = i
            var right = i
            while (true) {
                if (left - 1 inside s && right + 1 inside s && s[left - 1] == s[right + 1]) {
                    left--
                    right++
                } else {
                    break
                }
            }

            if (right - left > maxRight - maxLeft) {
                maxLeft = left
                maxRight = right
            }

            // i + 0.5 = center
            left = i
            right = i + 1
            if (right inside s && s[left] == s[right]) {
                while (true) {
                    if (left - 1 inside s && right + 1 inside s && s[left - 1] == s[right + 1]) {
                        left--
                        right++
                    } else {
                        break
                    }
                }

                if (right - left > maxRight - maxLeft) {
                    maxLeft = left
                    maxRight = right
                }
            }
        }

        return s.substring(maxLeft..maxRight)
    }

    infix fun Int.inside(s: String): Boolean =
            this >= 0 && this <= s.length - 1
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("ccc", s.longestPalindrome("ccc"))
    }

    @Test
    fun test2() {
        assertEquals("baaaab", s.longestPalindrome("baaaab"))
    }

    @Test
    fun test3() {
        assertEquals("baaaab", s.longestPalindrome("baaaab"))
    }

    @Test
    fun test4() {
        assertEquals("", s.longestPalindrome(""))
    }

    @Test
    fun test5() {
        assertEquals("bb", s.longestPalindrome("bb"))
    }

    @Test
    fun test6() {
        assertEquals("bacab", s.longestPalindrome("aabacabd"))
    }

}

/*
 (small1)(big)(small2)(small3)
 */

/*
aabacab // a a b a c a b aa aba aca bacab
 */
