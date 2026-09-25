package braceexpansionii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    sealed class Expr
    class And(val children: List<Expr>) : Expr() {
        override fun toString(): String {
            return "And{${children}}"
        }
    }

    class Or(val children: List<Expr>) : Expr() {
        override fun toString(): String {
            return "Or{${children}}"
        }
    }

    class Sym(val c: Char) : Expr() {
        override fun toString(): String {
            return "$c"
        }
    }


    var s = ""
    var i = 0

    fun readAnd(): Expr {
        val children = mutableListOf<Expr>()
        while (i in s.indices && s[i] != ',' && s[i] != '}') {
            if (s[i] in 'a'..'z') {
                children += Sym(s[i])
                i++
            } else {
                children += readOr()
            }
        }
        require(children.isNotEmpty())
        if (children.size == 1) return children[0]
        return And(children)
    }

    fun readOr(): Or {
        require(s[i] == '{')
        i++
        val children = mutableListOf<Expr>()
        var first = true
        while (s[i] != '}') {
            if (!first) {
                require(s[i] == ',')
                i++
            }
            children += readAnd()
            first = false
        }
        i++
        require(children.isNotEmpty())
        return Or(children)
    }

    fun evalAnd(expr: And, i: Int): List<String> {
        if (i == expr.children.size - 1) {
            return eval(expr.children[i])
        }

        val current = eval(expr.children[i])
        val next = evalAnd(expr, i + 1)

        val result = mutableListOf<String>()
        for (c in current) {
            for (n in next) {
                result += "$c$n"
            }
        }

        return result
    }

    fun eval(expr: Expr): List<String> {
        when (expr) {
            is And -> {
                return evalAnd(expr, 0)
            }

            is Or -> {
                val result = mutableListOf<String>()
                for (c in expr.children) {
                    result += eval(c)
                }
                return result
            }

            is Sym -> {
                return listOf(expr.c.toString())
            }
        }
    }

    fun braceExpansionII(expression: String): List<String> {
        s = expression
        val parsed = readAnd()
        val emitted = eval(parsed)
        return emitted.distinct().sorted()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf("ac","ad","ae","bc","bd","be"), s.braceExpansionII("{a,b}{c,{d,e}}"))
    }

    @Test
    fun test2() {
        assertEquals(listOf("a","ab","ac","z"), s.braceExpansionII("{{a,z},a{b,c},{ab,z}}"))
    }
}
