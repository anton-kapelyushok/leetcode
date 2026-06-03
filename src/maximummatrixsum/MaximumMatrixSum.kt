package maximummatrixsum

import org.junit.jupiter.api.Test

class Solution {
    fun maxMatrixSum(matrix: Array<IntArray>): Long {
        val n = matrix.size
        if (n == 1) return matrix[0][0].toLong()

        // the idea is we can switch 2n elements
        val flatMatrix = matrix.flatMap { it.toList() }

        var result = 0L
        flatMatrix.forEach { result += kotlin.math.abs(it) }

        val negativeCount = flatMatrix.count { it < 0 }

        // could not make all elements positive
        if (negativeCount % 2 != 0) {
            val absMin = flatMatrix.minOf { kotlin.math.abs(it) }
            result -= absMin * 2
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        println(
            s.maxMatrixSum(
                arrayOf(
                    intArrayOf(1, 2, 3),
                    intArrayOf(-1, -2, -3),
                    intArrayOf(1, 2, 3),
                )
            )
        )
    }

}
