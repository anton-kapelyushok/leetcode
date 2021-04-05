package longestrepeatingcharacterreplacement;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun characterReplacement(s: String, k: Int): Int {
        var max = 0
        ('A'..'Z').forEach { c ->
            var l = 0 // inclusive
            var r = 0 // exclusive
            var currentK = k
            while (l in s.indices) {
                if (r in s.indices && (s[r] == c || currentK > 0)) {
                    if (s[r] != c) currentK -= 1
                    r++
                } else {
                    if (r - l > max) max = r - l

                    if (s[l] != c) currentK += 1
                    l++
                }
            }
        }

        return max
    }
}

class NaiveSolution {
    fun characterReplacement(s: String, k: Int): Int {
        var max = 0
        ('A'..'Z').forEach { c ->
            var i = 0
            while (i < s.length) {
                if (s[i] != c) {
                    i++
                    continue
                }
                var currentK = k
                var currentRes = 0

                var j = i
                while (j < s.length && (currentK > 0 || s[j] == c)) {
                    currentRes++
                    if (s[j] != c) currentK--
                    j++
                }

                currentRes += Math.min(i, currentK)
                if (currentRes > max) max = currentRes

                while (i < s.length && s[i] == c) i++
            }
        }

        return max
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.characterReplacement("AABABBA", 1))
    }

}
