package maximumnumberofnonoverlappingpalindromesubstrings

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // k_max = s_length_max = 2000
    fun maxPalindromes(s: String, k: Int): Int {
        // find minimal palindromes for each index -> o(n^3) actually
        // but we can expand from core -> o(n^2)

        // odd length palindromes:

        val lengths = IntArray(s.length) { Int.MAX_VALUE }
        fun updateLength(i: Int, newLength: Int) {
            if (newLength < k) return
            lengths[i] = minOf(lengths[i], newLength)
        }

        for (i in s.indices) { // ba_ab palindromes
            updateLength(i, 1)

            var offset = 1
            while (true) {
                if (i - offset !in s.indices) break
                if (i + offset !in s.indices) break
                if (s[i - offset] != s[i + offset]) break
                updateLength(i - offset, 1 + 2 * offset)
                offset++
            }
        }

        for (i in s.indices) { // baab palindromes
            var l = i
            var r = i + 1
            while (true) {
                if (l !in s.indices) break
                if (r !in s.indices) break
                if (s[l] != s[r]) break
                updateLength(l, r - l + 1)
                l--
                r++
            }
        }

        val dp = IntArray(s.length) { -1 }
        fun f(i: Int): Int {
            if (i >= s.length) return 0
            if (dp[i] != -1) return dp[i]

            // SKIP
            var result = f(i + 1)

            // TAKE
            if (lengths[i] != Int.MAX_VALUE) {
                val take = 1 + f(i + lengths[i]) // 0, length 2 = take 0,1 -> 0 + 2
                result = maxOf(result, take)
            }

            dp[i] = result
            return result
        }

        val result =  f(0)
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.maxPalindromes("abaccdbbd", 3))
    }

}
