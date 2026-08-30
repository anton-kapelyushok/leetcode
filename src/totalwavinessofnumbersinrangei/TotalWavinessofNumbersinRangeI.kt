package totalwavinessofnumbersinrangei

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution2 {
    fun totalWaviness(num1: Int, num2: Int): Int {

        return 0
    }

    // count of waves in (0..n)
    fun g(n: Int): Int {
        val digits = n.toString()
        val l = digits.length
        if (l <= 2) return 0
        val f = digits[0].digitToInt()

        var r = 0

        // count 999
        r += g((1..l - 1).joinToString("") { "9" }.toInt())

        // count 1xxx until f0000
        for (i in 1 until f) {
            r += f(i, l - 1)
        }


        // count xxx
        return r
    }

    // count of waves in all numbers in a shape of dxxx (x times n)
    // d000 (0xn)
    // d999 (0xn)
    fun f(d: Int, n: Int): Int {
        if (n <= 1) return 0

        var w = 0

        var s = 1
        for (i in 0 until n - 2) {
            s *= 10
        }

        for (b in 0..9) {
            if (d > b) { // ie 43xxx
                w += (9 - b) * s // for 3 - 4,5,6,7,8,9
            }
            if (d < b) { // ie 34xxx
                w += b * s // for 4, 0,1,2,3
            }

            w += f(b, n - 1)
        }

        return w
    }
}

class Solution {
    fun totalWaviness(num1: Int, num2: Int): Int =
        (num1..num2)
            .sumOf(::waviness)

    private fun waviness(num: Int): Int =
        digits(num)
            .zipWithNext(Int::compareTo)
            .zipWithNext()
            .count { (a, b) -> a != 0 && b != 0 && a != b }

    private fun digits(num: Int): Sequence<Int> = sequence {
        var x = num
        while (x > 0) {
            yield(x % 10)
            x /= 10
        }
    }
}

class SolutionTest {

    private val s = Solution()
    private val s2 = Solution2()

    @Test
    fun test1() {
        val s1 = Solution()
//        assertEquals(
//            s1.totalWaviness(100, 199),
//            s.f(1, 2)
//        )

        assertEquals(
            s1.totalWaviness(10000, 19999),
            s2.f(1, 4)
        )
    }

    @Test
    fun test3() {
        val s1 = Solution()
        assertEquals(
            s1.totalWaviness(0, 121),
            s2.g(121),
        )
    }

    @Test
    fun test2() {
        assertEquals(3, s.totalWaviness(120, 130))
        assertEquals(3, s.totalWaviness(198, 202))
    }

    @Test
    fun test5() {
        assertEquals(5614, s.totalWaviness(123, 5328))
    }

    @Test
    fun test8() {
        assertEquals(11, s.totalWaviness(0, 121))
        assertEquals(1776, s.totalWaviness(0, 2124))
        assertEquals(13056, s.totalWaviness(0, 11242))
    }
}
