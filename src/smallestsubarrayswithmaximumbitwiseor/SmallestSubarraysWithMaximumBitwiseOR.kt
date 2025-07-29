package smallestsubarrayswithmaximumbitwiseor

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun smallestSubarrays(nums: IntArray): IntArray {
        if (nums.isEmpty()) return nums

        val bitCount = IntArray(32) { 0 }
        var bitsSet = 0

        fun add(v: Int) {
            for (i in 0 until 32) {
                val mask = 1 shl i
                val isSet = (v and mask) > 0
                if (isSet) {
                    bitCount[i]++
                    if (bitCount[i] == 1) {
                        bitsSet++
                    }
                }
            }
        }

        fun remove(v: Int) {
            for (i in 0 until 32) {
                val mask = 1 shl i
                val isSet = (v and mask) > 0
                if (isSet) {
                    bitCount[i]--
                    if (bitCount[i] == 0) {
                        bitsSet--
                    }
                }
            }
        }

        val res = IntArray(nums.size) { 0 }

        fun f(l: Int): Int {
//            println("f(l=$l)")

            if (l == nums.size - 1) {
                add(nums[l])
                res[l] = 1
                return l
            }
            var r = f(l + 1)
            add(nums[l])
            val initialBitsSet = bitsSet
            while (l < r) {
                remove(nums[r])
                if (initialBitsSet > bitsSet) {
                    add(nums[r])
                    res[l] = r - l + 1
                    return r
                }
                r--
            }
            res[l] = r - l + 1
            return r
        }

        f(0)
        return res
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(intArrayOf().toList(), s.smallestSubarrays(intArrayOf()).toList())
    }

    @Test
    fun test2() {
        assertEquals(intArrayOf(3, 3, 2, 2, 1).toList(), s.smallestSubarrays(intArrayOf(1, 0, 2, 1, 3)).toList())
    }

    @Test
    fun test3() {
        assertEquals(intArrayOf(2, 1, 1).toList(), s.smallestSubarrays(intArrayOf(8, 10, 8)).toList())
    }

}
