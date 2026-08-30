package longestsubstringwithoutrepeatingcharacters

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        val charCounts = mutableMapOf<Char, Int>()
        var duplicateCounts = 0

        var l = 0
        var longestSize = 0
        for (r in s.indices) {
            // 1. EXPAND [r]
            val c = s[r]
            charCounts[c] = (charCounts[c] ?: 0) + 1
            if (charCounts[c]!! == 2) {
                duplicateCounts++
            }

            // 2. SHRINK [l]
            while (duplicateCounts != 0) {
                val c = s[l]
                charCounts[c] = charCounts[c]!! - 1
                if (charCounts[c]!! == 1) {
                    duplicateCounts--
                }
                l++
            }

            // 3. RECORD
            longestSize = maxOf(longestSize, r - l + 1)
        }

        return longestSize
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.lengthOfLongestSubstring("pwwkew"))
    }

}
