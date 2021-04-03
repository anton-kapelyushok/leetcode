package removeinvalidparentheses;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

    fun removeInvalidParentheses(s: String): List<String> {

        val indicesToRemove = calculateIndices(s)

        val parts = indicesToRemove.map { (c, start, end, count) ->
            generate(s.substring(start until end), c, count)
        }

        return compose(0, parts)
    }


    fun calculateIndices(s: String): List<ToRemove> {
        val indicesToRemove = mutableListOf<ToRemove>()
        var openCount = 0
        var i = 0
        var lastUnmatched = 0
        while (i < s.length) {
            if (s[i] == ')') openCount--
            else if (s[i] == '(') openCount++

            if (openCount < 0) {
                i++
                while (i < s.length && s[i] != '(') {
                    if (s[i] == ')') {
                        openCount--
                    }
                    i++
                }

                indicesToRemove.add(ToRemove(')', lastUnmatched, i, -openCount))
                openCount = 0
                lastUnmatched = i
            } else {
                i++
            }
        }

        if (openCount == 0) {
            indicesToRemove.add(ToRemove('(', lastUnmatched, s.length, 0))
        } else { // open brackets left, need to delete openCount ( brackets somehow
            val newS = s.substring(lastUnmatched until s.length)
                    .replace('(', '_')
                    .replace(')', '(')
                    .replace('_', ')')
                    .reversed()
            val nextIndices = calculateIndices(newS)
            indicesToRemove.addAll(
                    nextIndices.map { r ->
                        ToRemove(
                                c = if (r.c == '(') ')' else '(',
                                startInclusive = newS.length - r.endExclusive + lastUnmatched,
                                endExclusive = newS.length - r.startInclusive + lastUnmatched,
                                count = r.count)
                    }.reversed()
            )
        }

        return indicesToRemove
    }

    fun generate(s: String, c: Char, count: Int): Set<String> {
        val cache = mutableMapOf<String, MutableMap<Int, Set<String>>>()
        return generate(s, c, count, cache)
    }

    fun generate(s: String, c: Char, count: Int, cache: MutableMap<String, MutableMap<Int, Set<String>>>): Set<String> {
        val cacheS = cache[s] ?: mutableMapOf()
        if (cacheS[count] != null) return cacheS[count]!!
        val result =
                if (count == 0) return setOf(s)
                else s.indices.filter { s[it] == c }
                        .map { s.substring(0 until it) + s.substring(it + 1 until s.length) }
                        .flatMap { generate(it, c, count - 1) }
                        .toSet()
        cacheS[count] = result
        cache[s] = cacheS
        return result
    }

    fun compose(start: Int, parts: List<Set<String>>): List<String> {
        if (start == parts.size) return listOf()
        else if (start == parts.size - 1) return parts.last().toList()
        else return parts[start].flatMap { l ->
            compose(start + 1, parts).map { r -> l + r }
        }
    }

    data class ToRemove(
            val c: Char,
            val startInclusive: Int,
            val endExclusive: Int,
            val count: Int
    )
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
        assertEquals(setOf("()()","(())"), s.removeInvalidParentheses("(((()(()").toSet())
    }

    @Test
    fun test10() {
        assertEquals(setOf("()()","(())"), s.removeInvalidParentheses("(())))))").toSet())
    }

}
