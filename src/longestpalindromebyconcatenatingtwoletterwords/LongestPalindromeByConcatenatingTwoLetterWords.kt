package longestpalindromebyconcatenatingtwoletterwords

import org.junit.jupiter.api.Test

class Solution {
    fun longestPalindrome(words: Array<String>): Int {
        val map = words.groupBy { it }.mapValues { (k, vs) -> vs.size }.toMutableMap()

        var length = 0
        // group pairs
        for (w in map.keys) {
            val wv = map[w] ?: 0
            if (wv == 0) {
                continue
            }
            if (w[0] == w[1]) {
                continue
            }
            val r = w.reversed()
            val rv = map[r] ?: 0
            if (rv == 0) {
                continue
            }
            val matchedCount = minOf(wv, rv)
            length += matchedCount * 4
            map[w] = wv - matchedCount
            map[r] = rv - matchedCount
        }

        var countedMiddle = false

        for (w in map.keys) {
            if (w[0] != w[1]) {
                continue
            }
            var v = map[w] ?: 0
            if (v == 0) {
                continue
            }

            val matchedCount = v / 2
            length += matchedCount * 4
            v = v - (v / 2) * 2

            if (!countedMiddle && v== 1) {
                countedMiddle = true
                length += 2
            }
        }

        return length
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }

}
