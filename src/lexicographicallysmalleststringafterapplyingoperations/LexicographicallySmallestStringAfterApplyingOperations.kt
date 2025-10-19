package lexicographicallysmalleststringafterapplyingoperations

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findLexSmallestString(s: String, a: Int, b: Int): String {
        var min = s
        val visited = mutableSetOf<String>()
        fun f(s: String) {
            if (s in visited) {
                return
            }
            if (min > s) {
                min = s
            }
            visited += s
            val nexta = s.toCharArray().mapIndexed { idx, c ->
                if (idx % 2 == 0) c else
                    (c.digitToInt() + a) % 10
            }.joinToString("")
            val nextb = s.drop(b) + s.take(b)
            f(nexta)
            f(nextb)
        }

        f(s)
        return min
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("2050", s.findLexSmallestString("5525", 9, 2))
    }

}
