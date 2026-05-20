package findtheprefixcommonarrayoftwoarrays

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution1 {
    fun findThePrefixCommonArray(a: IntArray, b: IntArray): IntArray {
        val n = a.size
        var acc = 0

        val r = IntArray(n) { 0 }

        val met = mutableSetOf<Int>()

        for (i in 0 until n) {
            if (a[i] in met) {
                met -= a[i]
                acc++
            } else {
                met += a[i]
            }
            if (b[i] in met) {
                met -= b[i]
                acc++
            } else {
                met += b[i]
            }
            r[i] = acc
        }

        return r
    }
}

class Solution {
    fun findThePrefixCommonArray(a: IntArray, b: IntArray): IntArray {
        val n = a.size
        var acc = 0

        val r = IntArray(n) { 0 }

        val met = mutableSetOf<Int>()

        for (i in 0 until n) {
            if (a[i] in met) {
                met -= a[i]
                acc++
            } else {
                met += a[i]
            }
            if (b[i] in met) {
                met -= b[i]
                acc++
            } else {
                met += b[i]
            }
            r[i] = acc
        }

        return r
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        check(
            intArrayOf(0, 2, 3, 4), s.findThePrefixCommonArray(
                intArrayOf(1, 3, 2, 4),
                intArrayOf(3, 1, 2, 4),
            )
        )
    }

    @Test
    fun test2() {
        check(
            intArrayOf(0, 1, 3), s.findThePrefixCommonArray(
                intArrayOf(2, 3, 1),
                intArrayOf(3, 1, 2),
            )
        )
    }

    private fun check(a: IntArray, b: IntArray) {
        assertEquals(a.toList(), b.toList())
    }

}
