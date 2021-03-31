package maximumnumberofvowelsinasubstringofgivenlength;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

    fun maxVowels(s: String, k: Int): Int {
        val vowels = "aeiou".toSet()
        var current = 0
        for (i in 0 until k) {
            if (s[i] in vowels) current++
        }

        var max = current
        for (i in k until s.length) {
            if (s[i-k] in vowels) current--
            if (s[i] in vowels) current++
            if (current > max) max = current
        }

        return max
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.maxVowels("abciiidef", 3))
    }

}
