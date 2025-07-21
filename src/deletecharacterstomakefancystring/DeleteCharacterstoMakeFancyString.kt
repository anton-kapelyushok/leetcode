package deletecharacterstomakefancystring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun makeFancyString(s: String): String {
        var currentCount = 0
        var lastC = '-'
        val result = mutableListOf<Char>()
        s.forEach { c ->
            if (c != lastC) {
                currentCount = 1
                result += c
                lastC = c
            } else {
                currentCount++
                if (currentCount <= 2) {
                    result += c
                }
            }
        }

        return result.joinToString("")
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("leetcode", s.makeFancyString("leeetcode"))
    }

}
