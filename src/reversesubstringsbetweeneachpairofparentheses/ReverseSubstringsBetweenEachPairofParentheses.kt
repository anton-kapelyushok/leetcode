package reversesubstringsbetweeneachpairofparentheses

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun reverseParentheses(s: String): String {
        val stack = LinkedList<StringBuilder>()
        var current = StringBuilder()
        for (c in s) {
            when (c) {
                '(' -> {
                    stack.push(current)
                    current = StringBuilder()
                }

                ')' -> {
                    val s = current.reverse()
                    current = stack.pop()
                    current.append(s)
                }

                else -> {
                    current.append(c)
                }
            }
        }
        return current.toString()
    }
}

class Solution1 {
    var i = 0
    var s = ""

    sealed class Expr
    class Sym(val c: Char) : Expr()
    class Par(val children: List<Expr>) : Expr()

    fun parse(): List<Expr> {
        val result = mutableListOf<Expr>()
        while (i in s.indices) {
            result += parseTerm()!!
        }
        return result
    }

    fun parseTerm(): Expr? {
        if (i !in s.indices) return null
        return parsePar() ?: parseSym()
    }

    fun parseSym(): Sym? {
        if (i !in s.indices) return null
        return Sym(s[i++])
    }

    fun parsePar(): Par? {
        if (i !in s.indices) return null
        if (s[i] != '(') return null
        i++
        val children = mutableListOf<Expr>()
        while (s[i] != ')') {
            children += parseTerm()!!
        }
        i++
        return Par(children)
    }


    fun reverseParentheses(s: String): String {
        this.s = s
        fun eval(e: Expr): String {
            return when (e) {
                is Par -> e.children.map { eval(it) }.joinToString("").reversed()
                is Sym -> e.c.toString()
            }
        }

        val parsed = parse()
        return parsed.map { eval(it) }.joinToString("")
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("iloveu", s.reverseParentheses("(u(love)i)"))
    }

    @Test
    fun test2() {
        assertEquals("doiloveu", s.reverseParentheses("do(u(love)i)"))
    }
}
