package longestvalidparentheses

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun longestValidParentheses(s: String): Int {
        val dp = s.indices.map { 0 }.toIntArray()

        for (i in s.indices) {
            dp[i] = when {
                i - 1 < 0 -> 0
                s[i] == '(' -> 0
                s[i - 1] == '(' -> if (i >= 2) dp[i - 2] + 2 else 2
                i - dp[i - 1] - 1 < 0 -> 0
                s[i - dp[i - 1] - 1] == '(' -> {
                    if (dp[i - 1] > 0) {
                        (if (i - dp[i - 1] - 2 >= 0) dp[i - dp[i - 1] - 2] else 0) + dp[i - 1] + 2
                    } else {
                        0
                    }
                }
                else -> 0
            }
        }

        return dp.max() ?: 0
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test0() {
        assertEquals(4, s.longestValidParentheses("()()"))
    }

    @Test
    fun test1() {
        assertEquals(2, s.longestValidParentheses("(()"))
    }

    @Test
    fun test2() {
        assertEquals(4, s.longestValidParentheses(")()())"))
    }

    @Test
    fun test3() {
        assertEquals(0, s.longestValidParentheses(""))
    }

    @Test
    fun test4() {
        assertEquals(2, s.longestValidParentheses("()(()"))
    }

    @Test
    fun test5() {
        assertEquals(6, s.longestValidParentheses("()(())"))
    }
}
