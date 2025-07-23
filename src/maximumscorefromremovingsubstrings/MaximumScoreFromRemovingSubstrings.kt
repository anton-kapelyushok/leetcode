package maximumscorefromremovingsubstrings

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maximumGain(s: String, ab: Int, ba: Int): Int {
        val parts = s.split(Regex("[^ab]+"))

        // assume b > a
        return parts.sumOf { part ->
            var bBeforeACount = 0

            var bAcc = 0
            for (c in part) {
                if (c == 'a') {
                    if (bAcc > 0) {
                        bAcc--
                        bBeforeACount++
                    }
                }
                if (c == 'b') {
                    bAcc++
                }
            }

            var aBeforeBCount = 0
            var aAcc = 0
            for (c in part) {
                if (c == 'b') {
                    if (aAcc > 0) {
                        aAcc--
                        aBeforeBCount++
                    }
                }
                if (c == 'a') {
                    aAcc++
                }
            }


            var aCount = 0
            var bCount = 0
            for (c in part) {
                if (c == 'a') {
                    aCount++
                }
                if (c == 'b') {
                    bCount++
                }
            }
            val maxPairs = minOf(aCount, bCount)

            val result = maxOf(
                aBeforeBCount * ab + (maxPairs - aBeforeBCount) * ba,
                bBeforeACount * ba + (maxPairs - bBeforeACount) * ab,
            )

            result
        }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(19, s.maximumGain("cdbcbbaaabab", 4, 5))
    }

    @Test
    fun test2() {
        assertEquals(20, s.maximumGain("aabbaaxybbaabb", 5, 4))
    }
}
