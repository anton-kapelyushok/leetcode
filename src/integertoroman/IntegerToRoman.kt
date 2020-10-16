package integertoroman

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun intToRoman(num: Int): String {
        var n = num
        var result = ""

        listOf(
                1000 to "M",
                900 to "CM",
                500 to "D",
                400 to "CD",
                100 to "C",
                90 to "XC",
                50 to "L",
                40 to "XL",
                10 to "X",
                9 to "IX",
                5 to "V",
                4 to "IV",
                1 to "I")
                .forEach { (v, s) ->
                    while (n >= v) {
                        result += s
                        n -= v
                    }
                }
        return result
    }
}

class SolutionTest() {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("III", s.intToRoman(3))
    }

    @Test
    fun test2() {
        assertEquals("IV", s.intToRoman(4))
    }

    @Test
    fun test3() {
        assertEquals("IX", s.intToRoman(9))
    }

    @Test
    fun test4() {
        assertEquals("LVIII", s.intToRoman(58))
    }

    @Test
    fun test5() {
        assertEquals("MCMXCIV", s.intToRoman(1994))
    }
}
