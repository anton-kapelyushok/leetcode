package wildcardmatching

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Solution {
    fun isMatch(s: String, p: String): Boolean {
        var sPointer = 0
        var pPointer = 0

        var starSPointer = -1
        var starPPointer = -1

        // consume maximum of string with least amount of starts
        while (sPointer < s.length) {
            // go to next star
            if (pPointer < p.length && (s[sPointer] == p[pPointer] || p[pPointer] == '?')) {
                sPointer++
                pPointer++
                continue
            }

            // we got to checkpoint!
            if (pPointer < p.length && p[pPointer] == '*') {
                starSPointer = sPointer
                starPPointer = pPointer

                // guess that start eats 0
                sPointer = sPointer
                pPointer++
                continue
            }

            // could not match with current guess
            // - we had a guess
            if (starSPointer >= 0) {
                starSPointer++
                sPointer = starSPointer
                pPointer = starPPointer + 1
                continue
            }

            // we had not encountered stars -> pattern does not match
            return false
        }

        for (i in (pPointer until p.length)) {
            if (p[i] != '*') return false
        }

        return true
    }
}

class DpSolution {
    fun isMatch(s: String, p: String): Boolean {
        val dp = Array(s.length + 1) { Array<Boolean?>(p.length + 1) { null } }
        return isMatch(s, p, dp)
    }

    fun isMatch(s: String, p: String, dp: Array<Array<Boolean?>>): Boolean {
        dp[s.length][p.length]?.let { return it }

        val result = when {
            s.isEmpty() && p.isEmpty() -> true
            s.isNotEmpty() && p.isEmpty() -> false
            s.isEmpty() && p.isNotEmpty() -> p.all { it == '*' }
            else -> {
                when (val pChar = p[0]) {
                    '?' -> isMatch(s.substring(1), p.substring(1), dp)
                    '*' -> (0..s.length).any { isMatch(s.substring(it), p.substring(1), dp) }
                    else -> s[0] == pChar && isMatch(s.substring(1), p.substring(1), dp)
                }

            }
        }

        dp[s.length][p.length] = result

        return result
    }

}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertFalse(s.isMatch("aa", "a"))
    }

    @Test
    fun test2() {
        assertTrue(s.isMatch("aa", "*"))
    }

    @Test
    fun test3() {
        assertFalse(s.isMatch("cb", "?a"))
    }

    @Test
    fun test4() {
        assertTrue(s.isMatch("adceb", "*a*b"))
    }

    @Test
    fun test5() {
        assertFalse(s.isMatch("acdcb", "a*c?b"))
    }
}
