package applyoperationsonarraytomaximizesumofsquares

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    val m = 1_000_000_000 + 7


    fun maxSum(nums: List<Int>, k: Int): Int {
        val counts = IntArray(32) { 0 }

        for (n in nums) {
            for ((i, v) in Integer.toBinaryString(n).padStart(32, '0').withIndex()) {
                if (v == '1') {
                    counts[32 - i - 1]++
                }
            }
        }


        var result = 0L
        for (i in 1..k) {
            var acc = 0L
            for (j in counts.indices) {
                if (counts[j] == 0) {
                    continue
                }
                counts[j]--
                acc += (1 shl j)
            }
            result += 1L * acc * acc
            result %= m
        }

        return result.toInt()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(261, s.maxSum(listOf(2, 6, 5, 8), 2))
    }
}
