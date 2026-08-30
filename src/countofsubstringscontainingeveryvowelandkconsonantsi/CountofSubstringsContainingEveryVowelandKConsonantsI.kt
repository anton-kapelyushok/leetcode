package countofsubstringscontainingeveryvowelandkconsonantsi

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    val vowels = "aeiou"

    fun countOfSubstrings(word: String, k: Int): Int {
        var result = 0

        l@ for (l in word.indices) {
            val coreVowels = mutableMapOf<Char, Int>()
            var coreConsonantsCount = 0

            var r = l
            r@ while (r < word.length && (vowels.any { (coreVowels[it] ?: 0) < 1 } || coreConsonantsCount != k)) {
                val c = word[r]
                when {
                    c in vowels -> coreVowels[c] = (coreVowels[c] ?: 0) + 1
                    else -> coreConsonantsCount++
                }

                if (coreConsonantsCount > k) continue@l
                r++
            }
            if (!vowels.all { (coreVowels[it] ?: 0) >= 1 }) continue@l
            if (coreConsonantsCount != k) continue@l

            result++

            while (r < word.length) {
                val c = word[r]
                if (c !in vowels) coreConsonantsCount++
                if (coreConsonantsCount > k) break
                else if (coreConsonantsCount == k) result++
                r++
            }
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.countOfSubstrings("iqeaouqi", 2))
    }

}
