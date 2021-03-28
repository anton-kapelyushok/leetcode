package stringtointeger

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun myAtoi(s: String): Int {
        var state = "ws"
        var sign = 1
        var value = 0
        loop@ for (c in s) {
            when (state) {
                "ws" -> when (c) {
                    ' ' -> Unit
                    '+' -> {
                        state = "digits"
                    }
                    '-' -> {
                        state = "digits"
                        sign = -1
                    }
                    in '0'..'9' -> {
                        state = "digits"
                        value = c - '0'
                    }
                    else -> break@loop
                }
                "digits" -> when (c) {
                    in '0'..'9' -> {
                        val digit = c - '0'
                        if (Int.MAX_VALUE / 10 < value) {
                            return if (sign > 0) Int.MAX_VALUE else Int.MIN_VALUE
                        }
                        if (Int.MAX_VALUE - digit >= value * 10) {
                            value = c - '0' + value * 10
                        } else {
                            return if (sign > 0) Int.MAX_VALUE else Int.MIN_VALUE
                        }
                    }
                    else -> break@loop
                }
            }

            if (value >= Int.MAX_VALUE) break@loop
        }
        return sign * value
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(42, s.myAtoi("42"))
    }

    @Test
    fun test2() {
        assertEquals(-42, s.myAtoi("   -42"))
    }

    @Test
    fun test3() {
        assertEquals(-2147483648, s.myAtoi("-91283472332"))
    }

    @Test
    fun test4() {
        assertEquals(-2147483648, s.myAtoi("-912834123123112321231231231231231272332"))
    }

    @Test
    fun test5() {
        assertEquals(2147483647, s.myAtoi("9128341231231231231231231231231231231231272332"))
    }

    @Test
    fun test6() {
        assertEquals(-2147483648, s.myAtoi("-2147483648"))
    }

    @Test
    fun test7() {
        assertEquals(-2147483647, s.myAtoi("-2147483647"))
    }

    @Test
    fun test8() {
        assertEquals(2147483647, s.myAtoi("2147483647"))
    }

    @Test
    fun test9() {
        assertEquals(2147483647, s.myAtoi("2147483648"))
    }
}
