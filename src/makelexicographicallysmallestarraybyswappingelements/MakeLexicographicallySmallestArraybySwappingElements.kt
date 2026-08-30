package makelexicographicallysmallestarraybyswappingelements

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun lexicographicallySmallestArray(nums: IntArray, limit: Int): IntArray {
        // come up with clusters
        val groups = TreeMap<Int, Int>()
        for (el in nums) {
            var g = groups.floorEntry(el)?.toPair()
            if (g == null || el !in g.first..g.second) {
                g = (el) to (el + limit)
            } else {
                groups.remove(g.first)
            }

            var min = g.first
            var max = g.second

            min = minOf(g.first, el)
            max = maxOf(g.second, el + limit)

            val minG = groups.floorEntry(min)?.toPair()
            if (minG != null && min in minG.first..minG.second) {
                groups.remove(minG.first)
                min = minG.first
            }

            val maxG = groups.floorEntry(max)?.toPair()
            if (maxG != null && max in maxG.first..maxG.second) {
                groups.remove(maxG.first)
                max = maxG.second
            }

            groups[min] = max
        }

        val result = IntArray(nums.size)
        val groupedIndices = nums.indices.groupBy { groups.floorKey(nums[it]) }.values

        groupedIndices.forEach { groupIndices ->
            // 5 6 7 -> 7 5 6
            val sortedValues = groupIndices.map { nums[it] }.sorted()
            groupIndices.indices.forEach {
                val idx = groupIndices[it]
                val value = sortedValues[it]
                result[idx] = value
            }

        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        s.lexicographicallySmallestArray(intArrayOf(1, 5, 3, 9, 8), limit = 2)
    }

    @Test
    fun test2() {
        val nums = (1..99999 step 2).toList().toIntArray()
        s.lexicographicallySmallestArray(nums, limit = 1)
    }

}
