package findfirstandlastpositionofelementinsortedarray

class Solution {
    fun searchRange(nums: IntArray, target: Int): IntArray {
        if (nums.isEmpty()) return intArrayOf(-1, -1)

        val l = run {
            var l = 0
            var r = nums.size - 1
            while (l < r) {
                val m = (l + r) / 2
                if (nums[m] >= target) {
                    r = m
                } else {
                    l = m + 1
                }
            }

            if (nums[l] == target) l else -1
        }

        if (l == -1) return intArrayOf(-1, -1)

        val r = run {
            var l = 0
            var r = nums.size - 1
            while (l < r) {
                val m = (l + r) / 2
                if (nums[m] > target) {
                    r = m
                } else {
                    l = m + 1
                }
            }

            if (nums[l] == target) l else l - 1
        }

        return intArrayOf(l, r)
    }
}
