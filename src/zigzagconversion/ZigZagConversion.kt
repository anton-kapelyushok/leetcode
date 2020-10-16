package zigzagconversion

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Solution {

    fun convert(s: String, n: Int): String {
        val len = s.length
        val result = CharArray(s.length)

        if (n == 1) return s

        var row = 0
        var i = 0
        while (true) {
            if (row > n - 1) break
            when (row) {
                0, n - 1 -> {
                    var k = 0
                    while (true) {
                        val next = (n + n - 2) * k + row
                        if (next >= len) {
                            row++
                            break
                        }
                        result[i] = s[next]
                        i++
                        k++
                    }
                }
                else -> {
                    var k = 0
                    while (true) {
                        val next = if (k % 2 == 0) {
                            (n + n - 2) * (k / 2) + row
                        } else {
                            (n + n - 2) * (k / 2) + (n - 1) + (n - 1) - row
                        }

                        if (next >= len) {
                            row++
                            break
                        }

                        result[i] = s[next]
                        i++
                        k++
                    }
                }
            }
        }
        return String(result)
    }
}


class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("0413526", s.convert("0123456", 3))
    }

    @Test
    fun test2() {
        assertEquals("PAHNAPLSIIGYIR", s.convert("PAYPALISHIRING", 3))
    }

    @Test
    fun test3() {
        assertEquals("0213", s.convert("0123", 2))
    }
}
