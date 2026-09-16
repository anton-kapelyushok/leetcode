package maximumscorefromremovingstones

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maximumScore(a: Int, b: Int, c: Int): Int {
        var a = a
        var b = b
        var c = c
        fun canRemove(): Boolean {
            return listOf(a, b, c).count { it > 0 } > 1
        }

        var score = 0
        while (canRemove()) {
            val (_a, _b, _c) = listOf(a, b, c).sorted()
            a = _a;
            b = _b - 1;
            c = _c - 1;
            score++
        }

        return score
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(6, s.maximumScore(2, 4, 6))
    }

}
