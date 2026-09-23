package maximumnumberofnon_overlappingsubstrings

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {

    fun maxNumOfSubstrings(s: String): List<String> {
        data class Interval(val l: Int, val r: Int)


        // find boundaries
        val lBoundary = IntArray(26) { -1 }
        val rBoundary = IntArray(26) { -1 }
        for (i in s.indices) {
            val c = s[i]
            if (lBoundary[c - 'a'] == -1) lBoundary[c - 'a'] = i
            rBoundary[c - 'a'] = i
        }

        val validSubstrings = mutableListOf<Interval>()
        outer@ for (c in 'a'..'z') {
            if (lBoundary[c - 'a'] == -1) continue
            val l = lBoundary[c - 'a']
            var r = rBoundary[c - 'a']
            var j = l + 1
            while (j <= r) {
                val cc = s[j]
                if (lBoundary[cc - 'a'] < l) {
                    continue@outer
                }
                r = maxOf(r, rBoundary[cc - 'a'])
                j++
            }
            validSubstrings += Interval(l, r)
        }

        val result = mutableListOf<String>()
        val sorted = validSubstrings.sortedBy { it.r }
        var r = -1
        for (i in sorted) {
            if (i.l > r) {
                result += s.substring(i.l..i.r)
                r = i.r
            }
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf("bcbc", "dd"), s.maxNumOfSubstrings("abcbcdda"))
    }

    @Test
    fun test2() {
        assertEquals(listOf("e", "f", "ccc"), s.maxNumOfSubstrings("adefaddaccc"))
    }

    @Test
    fun test3() {
        assertEquals(listOf("bb", "cc", "d"), s.maxNumOfSubstrings("abbaccd"))
    }

    @Test
    fun test4() {
        assertEquals(listOf("ababa"), s.maxNumOfSubstrings("ababa"))
    }
}
