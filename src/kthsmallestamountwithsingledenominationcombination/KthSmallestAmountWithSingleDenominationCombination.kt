package kthsmallestamountwithsingledenominationcombination

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/*
Idea: generate 2^15 numbers - too long
 */

class Solution {
    fun findKthSmallest(coins: IntArray, k: Int): Long {
        coins.sort()
        var l = 1L
        var r = Long.MAX_VALUE
        val k = k.toLong()

        while (l < r) {
            val res = (l + r) / 2
            val kRes = k(coins, res)
            val kPrev = k(coins, res - 1)

            if (kRes >= k && kPrev < k) return res // we are on spot

            if (kRes >= k) r = res
            else l = res + 1
        }

        return l
    }

    fun k(coins: IntArray, r: Long): Long {
        var res = 0L
        for (m in 1 until(1 shl coins.size)) {
            var cc = 0
            var lcm= 1L
            for (i in coins.indices) {
                if (m and (1 shl i) > 0) {
                    lcm = lcm(lcm, coins[i].toLong())
                    cc++
                }
            }
            if (cc % 2 == 1) {
                res += r / lcm
            } else {
                res -= r / lcm
            }
        }
        return res.toLong()
    }

    fun gcd(a: Long, b: Long): Long {
        var x = kotlin.math.abs(a)
        var y = kotlin.math.abs(b)

        while (y != 0L) {
            val remainder = x % y
            x = y
            y = remainder
        }

        return x
    }

    fun lcm(a: Long, b: Long): Long {
        if (a == 0L || b == 0L) return 0
        return kotlin.math.abs(a / gcd(a, b) * b)
    }
}

class SolutionTest {

    private val s = Solution()

//
//    @Test
//    fun test1() {
//        assertEquals(9L, s.findKthSmallest(intArrayOf(3, 6, 9), 3))
//    }
//
//    @Test
//    fun test2() {
//        assertEquals(12L, s.findKthSmallest(intArrayOf(5, 2), 7))
//    }
//
//    @Test
//    fun test3() {
//        assertEquals(12L, s.findKthSmallest(intArrayOf(2, 10), 6))
//    }

    @Test
    fun test4() {
        assertEquals(4, s.findKthSmallest(intArrayOf(6, 1, 2, 4), 4))
    }
    @Test
    fun test5() {
//        println(s.k(intArrayOf(1, 2), 100))
        assertEquals(2242115680, s.findKthSmallest(intArrayOf(21, 5, 18, 17, 8, 11, 19, 25, 13, 10, 24, 23, 15), 1218177966))
    }
}
