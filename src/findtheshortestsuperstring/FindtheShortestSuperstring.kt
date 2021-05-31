package findtheshortestsuperstring

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun shortestSuperstring(words: Array<String>): String {

        val len = words.size

        val squashSizes = Array(words.size) { IntArray(words.size) { -1 } }

        for (i in words.indices) {
            inner@ for (j in words.indices) {
                if (i == j) continue
                val l = words[i]
                val r = words[j]

                for (k in r.indices) {
                    if (l.endsWith(r.substring(0 until r.length - k))) {
                        squashSizes[i][j] = r.length - k
                        continue@inner
                    }
                }

                squashSizes[i][j] = 0
            }
        }


        val cache = Array(1 shl (len + 1)) { Array(len) { null as DfsResult? } }

        fun dfs(mask: Int, lastIdx: Int): DfsResult {
            if (cache[mask][lastIdx] != null) {
                return cache[mask][lastIdx]!!
            }
            if (mask == (1 shl (len)) - 1) {
                return DfsResult(0, -1)
            }

            var max = 0
            var maxNext = -1
            for (i in words.indices) {
                if (mask and (1 shl i) > 0) continue
                val nextMask = mask or (1 shl i)
                val sq = if (mask == 0) 0 else squashSizes[lastIdx][i]

                val res = dfs(nextMask, i)

                if (max <= res.v + sq) {
                    max = res.v + sq
                    maxNext = i
                }
            }

            cache[mask][lastIdx] = DfsResult(max, maxNext)
            return DfsResult(max, maxNext)
        }

        dfs(0, 0)

        var mask = 0
        var next = 0
//        val result = mutableListOf<String>()

        val sResult = StringBuilder()

        while (mask != (1 shl (len)) - 1) {
            val prev = next
            next = cache[mask][next]!!.next

            val sq = if (mask == 0) 0 else squashSizes[prev][next]
//            result.add(words[next])
            sResult.append(words[next].substring(sq until words[next].length))
            mask = mask or (1 shl next)
        }

        return sResult.toString()
    }

    data class DfsResult(
            val v: Int,
            val next: Int
    )
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("gctaagttcatgcatc", s.shortestSuperstring(arrayOf("catg", "ctaagt", "gcta", "ttca", "atgcatc")))
    }

    @Test
    fun test2() {
        assertEquals("efgabca", s.shortestSuperstring(arrayOf("abc", "bca", "efg")))
    }

    @Test
    fun test3() {
        assertEquals("efgabca", s.shortestSuperstring(arrayOf("alex", "loves", "leetcode")))
    }

    @Test
    fun test4() {
        val start = System.currentTimeMillis()
        assertEquals("hvhhihwdffmxnczmysoeeyugbiylsomoktkyfgljcfbkqcppgortnmsyjtvkrornzynedhoiunkponbfzpppvhbjydtx", s.shortestSuperstring(arrayOf("ppgortnmsy", "czmysoeeyugbiylso", "nbfzpppvhbjydtx", "rnzynedhoiunkpon", "ornzynedhoiunkpo", "ylsomoktkyfgljcf", "jtvkrornzynedhoiunk", "hvhhihwdffmxnczmyso", "ktkyfgljcfbkqcpp", "nzynedhoiunkponbfz", "nedhoiunkponbfzpppvh")))
        println(System.currentTimeMillis() - start)
    }

}
