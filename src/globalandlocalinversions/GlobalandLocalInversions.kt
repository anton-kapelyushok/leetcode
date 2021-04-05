package globalandlocalinversions;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun isIdealPermutation(nums: IntArray): Boolean {
        for (i in nums.indices) {
            val n = nums[i]
            val expectedIndices = (n-1)..(n+1)
            if (i !in expectedIndices) return false
        }
        return true
    }
}

class NaiveSolution {
    fun isIdealPermutation(nums: IntArray): Boolean {
        var nGlobal = 0
        var nLocal = 0
        for (i in 0 until nums.size - 1) {
            for (j in i + 1 until nums.size) {
                if (nums[i] > nums[j]) nGlobal++
            }
            if (nums[i] > nums[i + 1]) nLocal++
        }

        return nGlobal == nLocal
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.isIdealPermutation(intArrayOf(1, 0, 2)))
    }

    @Test
    fun test2() {
        assertEquals(false, s.isIdealPermutation(intArrayOf(1, 2, 0)))
    }

}
