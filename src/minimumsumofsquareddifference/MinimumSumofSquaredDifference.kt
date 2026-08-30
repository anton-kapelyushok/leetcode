package minimumsumofsquareddifference

import org.junit.jupiter.api.Test

class Solution {
    fun minSumSquareDiff(nums1: IntArray, nums2: IntArray, k1: Int, k2: Int): Long {
        var k = k1 + k2

        fun diff(idx: Int): Int {
            val diff = nums1[idx] - nums2[idx]
            return kotlin.math.abs(diff)
        }

        val differences = IntArray(1_000_000) { 0 } // 10^5 * 2
        nums1.indices.forEach { idx ->
            differences[diff(idx)]++
        }

        for (i in differences.indices.reversed()) {
            if (i == 0) break
            if (k == 0) break
            val dec = minOf(differences[i], k)
            k -= dec
            differences[i] -= dec
            differences[i - 1] += dec
        }

        return differences.indices.sumOf { 1L * it * it * differences[it] }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }

}
