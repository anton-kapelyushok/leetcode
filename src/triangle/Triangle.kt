package triangle

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minimumTotal(triangle: List<List<Int>>): Int {
        var accum1 = IntArray(triangle.size) { triangle[triangle.size - 1][it] }
        var accum2 = IntArray(triangle.size)
        for (i in (triangle.size - 2) downTo 0) {
            for (j in triangle[i].indices) {
                val n = triangle[i][j]
                accum2[j] = Math.min(accum1[j], accum1[j + 1]) + n
            }
            accum1 = accum2.also { accum2 = accum1 }
        }

        return accum1[0]
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(11, s.minimumTotal(listOf(
                listOf(2),
                listOf(3, 4),
                listOf(6, 5, 7),
                listOf(4, 1, 8, 3)
        )))
    }

}
