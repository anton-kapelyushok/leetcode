package removeinvalidparentheses;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

    fun removeInvalidParentheses(s: String): List<String> {
        val sb = StringBuilder()

        val result = mutableListOf<String>()
        backTrack(result, s, sb, 0, 0, 0, 0)
        return result.toSet().toList()
    }

    fun backTrack(result: MutableList<String>, s: String, sb: StringBuilder, start: Int, openCount: Int, closeCount: Int, removed: Int) {
        val currentRemoved = if (result.isEmpty()) Int.MAX_VALUE else s.length - result[result.size - 1].length

        if (removed > currentRemoved) return

        if (start == s.length) {
            if (openCount == closeCount) {
                if (removed < currentRemoved) {
                    result.clear()
                }
                result.add(sb.toString())
            }
            return
        }

        when {
            s[start] == '(' -> {
                sb.append(s[start])
                backTrack(result, s, sb, start + 1, openCount + 1, closeCount, removed)
                sb.deleteCharAt(sb.length - 1)

                backTrack(result, s, sb, start + 1, openCount, closeCount, removed + 1)
            }
            s[start] == ')' -> {
                if (openCount > closeCount) {
                    sb.append(s[start])
                    backTrack(result, s, sb, start + 1, openCount, closeCount + 1, removed)
                    sb.deleteCharAt(sb.length - 1)
                }

                backTrack(result, s, sb, start + 1, openCount, closeCount, removed + 1)
            }
            else -> {
                sb.append(s[start])
                backTrack(result, s, sb, start + 1, openCount, closeCount, removed)
                sb.deleteCharAt(sb.length - 1)
            }
        }

    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(setOf("(())()", "()()()"), s.removeInvalidParentheses("()())()").toSet())
    }

    @Test
    fun test2() {
        assertEquals(setOf("(a())()", "(a)()()"), s.removeInvalidParentheses("(a)())()").toSet())
    }

    @Test
    fun test3() {
        assertEquals(setOf(""), s.removeInvalidParentheses(")(").toSet())
    }

    @Test
    fun test4() {
        assertEquals(setOf(""), s.removeInvalidParentheses("))").toSet())
    }

    @Test
    fun test5() {
        assertEquals(setOf("()"), s.removeInvalidParentheses(")()(").toSet())
    }

    @Test
    fun test6() {
        assertEquals(setOf("()"), s.removeInvalidParentheses("()((((").toSet())
    }

    @Test
    fun test7() {
        assertEquals(setOf("k()", "(k)"), s.removeInvalidParentheses("(((k()((").toSet())
    }

    @Test
    fun test8() {
        assertEquals(setOf("()k()", "()(k)"), s.removeInvalidParentheses("()(((k()((").toSet())
    }

    @Test
    fun test9() {
        assertEquals(setOf("()()", "(())"), s.removeInvalidParentheses("(((()(()").toSet())
    }

}
