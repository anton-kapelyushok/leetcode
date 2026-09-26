package evaluatethebracketpairsofastring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun evaluate(s: String, knowledge: List<List<String>>): String {
        val m = knowledge.associate { (k, v) -> k to v }

        val out = StringBuilder()
        var i = 0
        while (i in s.indices) {
            if (s[i] != '(') {
                out.append(s[i])
                i++
                continue
            } else {
                require(s[i] == '(')
                i++
                val w = StringBuilder()
                while (s[i] != ')') {
                    w.append(s[i])
                    i++
                }
                i++
                out.append(m[w.toString()] ?: "?")
            }
        }

        return out.toString()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test2() {
        assertEquals("bobistwoyearsold", s.evaluate("(name)is(age)yearsold", knowledge = listOf(listOf("name", "bob"), listOf("age", "two"))))
    }

    @Test
    fun test1() {
        assertEquals("yesyesyesaaa", s.evaluate("(a)(a)(a)aaa", knowledge = listOf(listOf("a", "yes"))))
    }

    @Test
    fun test3() {
        assertEquals("hi?", s.evaluate("hi(name)", knowledge = listOf(listOf("a", "yes"))))
    }
}
