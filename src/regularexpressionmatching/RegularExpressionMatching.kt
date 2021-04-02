package regularexpressionmatching

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun isMatch(s: String, p: String): Boolean {
        val tokens = tokenize(p)
        println(tokens)

        val dp = Array(s.length + 1) { Array(tokens.size + 1) { false } }


        for (i in s.length downTo 0) {
            for (j in tokens.size downTo 0) {
                if (i == s.length) {
                    if (j == tokens.size) {
                        dp[i][j] = true
                    } else {
                        dp[i][j] = tokens[j].m && dp[i][j+1]
                    }
                } else if (j == tokens.size) {
                    dp[i][j] = false
                } else {
                    val token = tokens[j]
                    if (!token.m) {
                        if (token.c == null) {
                            dp[i][j] = dp[i + 1][j + 1]
                        } else {
                            dp[i][j] = s[i] == token.c && dp[i + 1][j + 1]
                        }
                    } else {
                        dp[i][j] = dp[i][j + 1] // try not to match on current symbol
                        if (!dp[i][j]) { // if we still have to match try to consume current symbol
                            if (token.c == null) {
                                dp[i][j] = dp[i + 1][j + 1] || dp[i + 1][j]
                            } else {
                                dp[i][j] = s[i] == token.c && (dp[i + 1][j + 1] || dp[i + 1][j])
                            }
                        }
                    }
                }
            }
        }

        return dp[0][0]
    }

    fun tokenize(p: String): List<Token> {
        val result = mutableListOf<Token>()
        var i = p.length - 1
        while (i >= 0) {
            var c: Char? = null
            var m = false

            if (p[i] == '*') {
                m = true
                i--
            }

            if (p[i] != '.') {
                c = p[i]
            }

            i--
            result.add(0, Token(c, m))
        }
        return result
    }

    // a*, .*, a, .
    data class Token(val c: Char?, val m: Boolean) {
        override fun toString(): String {
            return "${c ?: "."}${if (m) "*" else ""}"
        }
    }
}

class DpUpBottomSolution {
    fun isMatch(s: String, p: String): Boolean {
        val tokens = tokenize(p)
        println(tokens)
        return isMatch(s, tokens, mutableMapOf())
    }

    fun isMatch(s: String, tokens: List<Token>, cache: MutableMap<Int, MutableMap<Int, Boolean>>): Boolean {
        val stringCache = cache[s.length] ?: mutableMapOf()
        if (stringCache[tokens.size] != null) return stringCache[tokens.size]!!
        var result = false

        when {
            // no string left and no tokens left - correct
            s.isEmpty() && tokens.isEmpty() -> result = true
            // no string to match but tokens left - correct only if all tokens are *
            s.isEmpty() && tokens.isNotEmpty() -> result = tokens.all { it.m }
            // some string left but no more tokens -> incorrect
            s.isNotEmpty() && tokens.isEmpty() -> result = false
            // current token is single letter
            !tokens[0].m -> result = when (val tokenC = tokens[0].c) {
                null -> isMatch(s.substring(1), tokens.slice(1 until tokens.size), cache)
                else -> tokenC == s[0] && isMatch(s.substring(1), tokens.slice(1 until tokens.size), cache)
            }
            // current token is a*
            else -> result = when (val tokenC = tokens[0].c) {
                null -> (0..s.length).any {
                    isMatch(s.substring(it), tokens.slice(1 until tokens.size), cache)
                }
                else -> (0..s.length).any {
                    s.substring(0 until it).all { c -> c == tokenC }
                            && isMatch(s.substring(it), tokens.slice(1 until tokens.size), cache)
                }
            }
        }


        stringCache[tokens.size] = result
        cache[s.length] = stringCache
        return result
    }

    fun tokenize(p: String): List<Token> {
        val result = mutableListOf<Token>()
        var i = p.length - 1
        while (i >= 0) {
            var c: Char? = null
            var m = false

            if (p[i] == '*') {
                m = true
                i--
            }

            if (p[i] != '.') {
                c = p[i]
            }

            i--
            result.add(0, Token(c, m))
        }
        return result
    }

    // a*, .*, a, .
    data class Token(val c: Char?, val m: Boolean) {
        override fun toString(): String {
            return "${c ?: "."}${if (m) "*" else ""}"
        }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(false, s.isMatch("aa", "a"))
    }

    @Test
    fun test2() {
        assertEquals(true, s.isMatch("aa", "a*"))
    }

    @Test
    fun test3() {
        assertEquals(true, s.isMatch("ab", ".*"))
    }

    @Test
    fun test4() {
        assertEquals(true, s.isMatch("aab", "c*a*b"))
    }

    @Test
    fun test5() {
        assertEquals(false, s.isMatch("mississippi", "mis*is*p*."))
    }

    @Test
    fun test6() {
        assertEquals(true, s.isMatch("mississippi", "mis*is*ip*."))
    }
}
