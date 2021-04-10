package tagvalidator;

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun isValid(code: String): Boolean {
        val tokens = tokenize(code) ?: return false
        if (tokens.isEmpty()) return true

        if (tokens[0] !is L.OpenTag) return false
        if (tokens[tokens.size -1] !is L.ClosingTag) return false

        val l = tokens[0] as L.OpenTag
        val r = tokens[tokens.size -1]as L.ClosingTag
        if (l.v != r.v) return false

        val stack = LinkedList<String>()
        for (t in 1 until tokens.size - 1) {
            val token = tokens[t]
            if (token is L.OpenTag) stack.push(token.v)
            if (token is L.ClosingTag) {
                if (stack.isEmpty()) return false
                val open = stack.pop()
                if (open != token.v) return false
            }
        }
        if (stack.isNotEmpty()) return false

        return true
    }

    fun tokenize(s: String): List<L>? {
        val result = mutableListOf<L>()
        var i = 0

        loop@ while (i < s.length) {
            var c = s[i]
            if (c == '<') {
                i++
                if (i !in s.indices) return null

                c = s[i]
                if (c == '!') {
                    // CDATA
                    i++
                    for (j in "[CDATA[") {
                        if (i !in s.indices) return null
                        if (s[i] != j) return null
                        i++
                    }
                    var prevprev = 'A'
                    var prev = '['
                    while (true) {
                        if (i >= s.length) return null
                        if (prevprev == ']' && prev == ']' && s[i] == '>') {
                            result.add(L.CData)
                            i++
                            continue@loop
                        }
                        prevprev = prev
                        prev = s[i]
                        i++
                    }
                } else if (c == '/') {
                    // CLOSING TAG
                    val content = StringBuilder()
                    i++
                    while (true) {
                        if (i >= s.length) return null
                        c = s[i]
                        if (c == '>') {
                            if (content.length !in (1..9)) return null
                            result.add(L.ClosingTag(content.toString()))
                            i++
                            continue@loop
                        }
                        if (c in 'A'..'Z') {
                            content.append(c)
                            i++
                        } else {
                            return null
                        }
                    }
                } else if (c in 'A'..'Z') {
                    // OPEN TAG
                    val content = StringBuilder()
                    content.append(c)
                    i++
                    while (true) {
                        if (i >= s.length) return null
                        c = s[i]
                        if (c == '>') {
                            if (content.length !in (1..9)) return null
                            result.add(L.OpenTag(content.toString()))
                            i++
                            continue@loop
                        }
                        if (c in 'A'..'Z') {
                            content.append(c)
                            i++
                        } else {
                            return null
                        }
                    }
                } else {
                    return null
                }
            } else {
                // TEXT
                i++
                while (true) {
                    if (i >= s.length) {
                        result.add(L.Text)
                        break@loop
                    }
                    if (s[i] == '<') {
                        result.add(L.Text)
                        continue@loop
                    }
                    i++
                }
            }
        }

        return result
    }


    sealed class L {
        data class OpenTag(val v: String) : L()
        data class ClosingTag(val v: String) : L()
        object CData : L() {
            override fun toString(): String {
                return "CData"
            }
        }
        object Text : L() {
            override fun toString(): String {
                return "Text"
            }
        }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.isValid("<DIV>This is the first line <![CDATA[<div>]]></DIV>"))
    }
    @Test
    fun test2() {
        assertEquals(true, s.isValid("<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"))
    }

    @Test
    fun test3() {
        assertEquals(true, s.isValid("<TRUE><![CDATA[wahaha]]]><![CDATA[]> wahaha]]></TRUE>"))
    }

    @Test
    fun test4() {
        assertEquals(false, s.isValid("<TRUe><![CDATA[123123]]]]><![CDATA[>123123]]></TRUe>"))
    }

}
