package maximumuniquesubarraysumafterdeletion

class Solution {
    fun maxSum(nums: IntArray): Int {
        val max = nums.max()
        if (max <= 0) return max
        return nums.toSet().filter { it > 0 }.sum()
    }
}

class SolutionTest {

    private val s = Solution()

}
