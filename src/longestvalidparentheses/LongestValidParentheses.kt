package longestvalidparentheses

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

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

class MonthlyChallengeSolution {
    fun longestValidParentheses(s: String): Int {
        val dp = IntArray(s.length + 1)

        var max = 0
        for (i in s.length downTo 0) {
            if (i == s.length) continue
            if (s[i] == '(') {
                if (i + 1 in s.indices) {
                    if (s[i + 1] == ')') {
                        dp[i] = dp[i + 2] + 2
                        if (dp[i] > max) max = dp[i]
                    } else {
                        if (dp[i + 1] != 0) {
                            if (i + 1 + dp[i + 1] in s.indices) {
                                if (s[i + 1 + dp[i + 1]] == ')') {
                                    dp[i] = dp[i + 1] + 2 + dp[i + 1 + dp[i + 1] + 1]
                                    if (dp[i] > max) max = dp[i]
                                }
                            }
                        }
                    }
                }
            }
        }

        return max
    }
}

class StackSolution {
    fun longestValidParentheses(s: String): Int {
        val stack = LinkedList<Int>()
        var max = 0

        stack.addLast(-1) // last unmatched index
        for (i in s.indices) {
            if (s[i] == '(' || stack.peekFirst() < 0 || s[stack.peekFirst()] == ')') {
                stack.push(i)
            } else {
                stack.pop() // matched one brace
                val lastUnmatchedIndex = stack.peekFirst()
                max = Math.max(i - lastUnmatchedIndex, max)
            }
        }

        return max
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
