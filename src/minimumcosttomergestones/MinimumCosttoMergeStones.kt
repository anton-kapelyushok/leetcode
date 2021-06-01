package minimumcosttomergestones

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun mergeStones(stones: IntArray, k: Int): Int {

        if (k != 2 && stones.size % (k - 1) != 1) return -1

        val cache = mutableMapOf<List<Int>, FR>()

        fun f(arr: List<Int>): FR {
            val n = arr.size
            if (arr in cache) {
                return cache[arr]!!
            }
            if (n == 1) {
                return FR(0, arr[0])
            }
            if (n == k) {
                var sum = 0
                for (i in 0 until k) sum += arr[i]
                cache[arr] = FR(sum, sum)
                return FR(sum, sum)
            }

            fun bt(s: Int, l: Int): FR? {
                if (l == 1) {
                    val sizeLeft = n - s
                    if (k != 2 && sizeLeft % (k - 1) != 1) return null
                    return f(arr.subList(s, n))
                }

                var minFR: FR? = null

                for (i in s until n - l + 1 step k - 1) {
                    val left = f(arr.subList(s, i + 1))
                    val tail = bt(i + 1, l - 1)
                    if (tail != null) {
                        val totalR = tail.r + left.r
                        val totalF = tail.f + left.f

                        if (minFR == null) {
                            minFR = FR(totalF, totalR)
                        } else if (minFR.f + minFR.r > totalF + totalR) {
                            minFR = FR(totalF, totalR)
                        }
                    }
                }

                return minFR
            }

            val result = bt(0, k)!!.let { FR(it.f + it.r, it.r) }
            cache[arr] = result!!
            return result!!
        }

        val r = f(stones.toList())

        return r.f
    }

    data class FR(
            val f: Int,
            val r: Int
    )
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test0() {
        assertEquals(9, s.mergeStones(intArrayOf(1, 2, 3), 2))
    }

    @Test
    fun test1() {
        assertEquals(10, s.mergeStones(intArrayOf(1, 1, 1, 2), 2))
    }

    @Test
    fun test2() {
        assertEquals(20, s.mergeStones(intArrayOf(3, 2, 4, 1), 2))
    }

    @Test
    fun test3() {
        assertEquals(25, s.mergeStones(intArrayOf(3, 5, 1, 2, 6), 3))
    }

    @Test
    fun testIntArray() {
        assertEquals(listOf(1, 2), listOf(1, 2))

        assertEquals(listOf(1, 2), listOf(1, 2, 3).subList(0, 2))
    }

    @Test
    fun testFor() {
        val s = 2
        val l = 2
        val n = 9
        val k = 3
        for (i in s until n - l + 1 step k - 1) {
            println(i)
        }
    }
}
