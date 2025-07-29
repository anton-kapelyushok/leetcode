package minimummovestomakearraycomplementary

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minMoves(nums: IntArray, l: Int): Int {
        val n = nums.size
        val map = mutableMapOf<Int, Int>()

        for (i in 0 until n / 2) {
            var a = nums[i]
            var b = nums[n - i - 1]

            if (b < a) b = a.also { a = b }

            // 1 <= a <= b <= l
            map[2] = (map[2] ?: 0) + 2
            map[a + 1] = (map[a + 1] ?: 0) - 1
            map[a + b] = (map[a + b] ?: 0) - 1
            map[a + b + 1] = (map[a + b + 1] ?: 0) + 1
            map[b + l + 1] = (map[b + l + 1] ?: 0) + 1
        }

        val ee = map.entries.sortedBy { it.key }
        val e = ee.map { it.value }.toMutableList()
        for (i in 1 until e.size) {
            e[i] = e[i] + e[i - 1]
        }

        return e.min()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(1, s.minMoves(intArrayOf(1, 2, 4, 3), 3))
    }

    @Test
    fun test2() {
        assertEquals(2, s.minMoves(intArrayOf(1, 2, 2, 1), 2))
    }

    @Test
    fun test3() {
        assertEquals(0, s.minMoves(intArrayOf(1, 2, 1, 2), 2))
    }
}
