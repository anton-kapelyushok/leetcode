package permutations

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun permute(nums: IntArray): List<List<Int>> {
        if (nums.size == 1) {
            return listOf(listOf(nums[0]))
        }

        val result = mutableListOf<List<Int>>()

        for (i in nums) {
            permute(nums.filter { it != i }.toIntArray()).forEach { result.add(listOf(*(intArrayOf(i) + it).toTypedArray())) }
        }


        return result
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf(listOf(1, 2, 3), listOf(1, 3, 2), listOf(2, 1, 3), listOf(2, 3, 1), listOf(3, 1, 2), listOf(3, 2, 1)),
                s.permute(intArrayOf(1, 2, 3)))
    }

    @Test
    fun test2() {
    }
}
