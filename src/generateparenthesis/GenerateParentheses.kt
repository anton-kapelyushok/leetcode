package generateparenthesis

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    private val cache = mutableMapOf<Int, Set<String>>()

    fun generateParenthesis(n: Int): List<String> {
        return _generateParenthesis(n).toList()
    }

    fun _generateParenthesis(n: Int): Set<String> {
        if (n == 1) return setOf("()")
        if (cache.containsKey(n)) return cache[n]!!
        val result = mutableSetOf<String>()

        _generateParenthesis(n - 1).forEach {
            result += "($it)"
        }

        for (i in 1 until n) {
            val left = _generateParenthesis(i)
            val right = _generateParenthesis(n - i)
            left.forEach { l ->
                right.forEach { r ->
                    result += "$l$r"
                }
            }
        }

        cache[n] = result
        return result
    }
}

class SolutionTest {

    private var solution = Solution()

    @Test
    fun test1() {
        assertEquals(
                listOf("((()))", "(()())", "(())()", "()(())", "()()()").toSet(),
                solution.generateParenthesis(3).toSet()
        )
    }

    @Test
    fun test2() {
        assertEquals(
                listOf(
                        "(((())))",
                        "((()()))",
                        "((())())",
                        "((()))()",
                        "(()(()))",
                        "(()()())",
                        "(()())()",
                        "(())(())",
                        "(())()()",
                        "()((()))",
                        "()(()())",
                        "()(())()",
                        "()()(())",
                        "()()()()"
                ).toSet(), solution.generateParenthesis(4).toSet())
    }
}
