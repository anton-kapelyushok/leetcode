package maximumnumberofoperationstomoveonestotheend

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maxOperations(s: String): Int {
        var holes = 0
        var onesAccum = 0
        var isHoles = false

        var result = 0
        for (c in s.reversed()) {
            when {
                c == '0' && isHoles -> {
                    continue
                }

                c == '0' && !isHoles -> {
                    isHoles = true
                    result += onesAccum * holes
                    onesAccum = 0
                    holes++
                }

                c == '1' && isHoles -> {
                    isHoles = false
                    onesAccum = 1
                }

                c == '1' && !isHoles -> {
                    onesAccum++
                }

                else -> error("")
            }
        }
        result += onesAccum * holes

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.maxOperations("1001101"))
    }

    @Test
    fun test2() {
        assertEquals(4, s.maxOperations("10011001"))
    }

    @Test
    fun test3() {
        assertEquals(3, s.maxOperations("10100"))
    }
}
