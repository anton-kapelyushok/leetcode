package countpairsthatformacompletedayii

import org.junit.jupiter.api.Test

class Solution {
    fun c(n: Int): Long {
        return (n.toLong() * (n.toLong() - 1)) / 2
    }

    fun countCompleteDayPairs(hours: IntArray): Long {
        val groups = hours.groupBy { it % 24 }.mapValues { (k, v) -> v.size }

        var result = 0L
        for (i in 1..11) {
            val l = groups[i] ?: 0
            val r = groups[24 - i] ?: 0
            result += l * r
        }


        val g12 = (groups[12] ?: 0)
        if (g12 >= 2) {
            result += c(g12)
        }

        val g0 = (groups[0] ?: 0)
        if (g0 >= 2) {
            result += c(g0)
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        println(
            s.countCompleteDayPairs(intArrayOf(72, 48, 24, 3))
        )
    }

}
