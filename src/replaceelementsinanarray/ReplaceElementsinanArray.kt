package replaceelementsinanarray

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun arrayChange(nums: IntArray, operations: Array<IntArray>): IntArray {
        val indexByNum = nums.indices.associateBy { nums[it] }.toMutableMap()

        operations.forEach { (num, replaceWith) ->
            val idx = indexByNum[num]!!
            indexByNum.remove(num)
            indexByNum[replaceWith] = idx
            nums[idx] = replaceWith
        }

        return nums
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(intArrayOf(), s.arrayChange(intArrayOf(), arrayOf()))
    }

}
