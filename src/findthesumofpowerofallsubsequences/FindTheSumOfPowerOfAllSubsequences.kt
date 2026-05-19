package findthesumofpowerofallsubsequences

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    companion object {
        val m = 1_000_000_000 + 7
    }

    // n[i] = count of subsequences summing to i
    class R(val n: LongArray = LongArray(101) { 0 }) {
        override fun toString(): String {
            return "R${n.toList().dropLastWhile { it == 0L }}"
        }

        fun shift(a: Int): R {
            val r = R()
            for (i in a..100) {
                r.n[i] = n[i - a]
            }
            return r
        }

        operator fun plus(o: R): R {
            val r = R()
            for (i in 0..100) {
                r.n[i] = n[i] + o.n[i]
                r.n[i] %= m
            }
            return r
        }

    }

    fun f(nums: IntArray, i: Int): R {
        if (i == nums.size) return R().apply { n[0] = 1 }
        val r = f(nums, i + 1)
        val a = nums[i]

        return r + r.shift(a)
    }

    fun g(nums: IntArray, i: Int): R {
        if (i == nums.size) return R().apply { n[0] = 1 }
        val r = g(nums, i + 1)
        val a = nums[i]
        return r + r + r.shift(a)
    }

    fun sumOfPower(nums: IntArray, k: Int): Int {
        return g(nums, 0).n[k].toInt()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(6, s.sumOfPower(intArrayOf(1, 2, 3), 3))
    }

    @Test
    fun test2() {
        assertEquals(4, s.sumOfPower(intArrayOf(2, 3, 3), 5))
    }

    @Test
    fun test3() {
        assertEquals(0, s.sumOfPower(intArrayOf(1, 2, 3), 7))
    }

    @Test
    fun test4() {
        println(s.f(intArrayOf(1, 1, 2, 1, 1), 3))
        println(s.f(intArrayOf(1, 1, 2, 1, 1), 2))
        println(s.f(intArrayOf(1, 1, 2, 1, 1), 1))
        println(s.f(intArrayOf(1, 1, 2, 1, 1), 0))
    }

    @Test
    fun test5() {
        println(s.g(intArrayOf(1,2,3), 3))
        println(s.g(intArrayOf(1,2,3), 2))
        println(s.g(intArrayOf(1,2,3), 1))
        println(s.g(intArrayOf(1,2,3), 0))
    }
}
