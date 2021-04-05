package maximumsumobtainedofanypermutation;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun maxSumRangeQuery(nums: IntArray, requests: Array<IntArray>): Int {
        val indexPopularity = IntArray(nums.size)


        requests.forEach { r ->
            indexPopularity[r[0]] += 1
            if (r[1] + 1 in nums.indices) {
                indexPopularity[r[1] + 1] -= 1
            }
        }

        var currentPopularity = 0
        for (i in nums.indices) {
            currentPopularity += indexPopularity[i]
            indexPopularity[i] = currentPopularity
        }

        indexPopularity.sort()
        nums.sort()

        var result = 0L
        val mod = 1_000_000_000 + 7

        nums.indices.forEach {
            val next = (nums[it].toLong() * indexPopularity[it] % mod) % mod
            result = (result + next) % mod
        }
        return result.toInt()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(19, s.maxSumRangeQuery(intArrayOf(1, 2, 3, 4, 5), arrayOf(intArrayOf(1, 3), intArrayOf(0, 1))))
    }

    @Test
    fun test2() {
        assertEquals(21, s.maxSumRangeQuery(intArrayOf(3, 2, 2, 2, 3, 4), arrayOf(intArrayOf(1, 3), intArrayOf(2, 3), intArrayOf(2, 2))))
    }
}
