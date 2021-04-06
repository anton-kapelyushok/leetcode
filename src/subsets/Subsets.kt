package subsets;

class Solution {
    fun subsets(nums: IntArray): List<List<Int>> {
        val counts = IntArray(21)
        nums.forEach { n -> counts[n + 10]++ }

        return emit(0, counts)
    }

    fun emit(start: Int, counts: IntArray): List<List<Int>> {
        if (start == counts.size) return listOf(listOf())

        val next = emit(start + 1, counts)
        return (0..Math.min(counts[start], 1)).flatMap { i ->
            val left = (0 until i).map { start - 10 }
            next.map { right -> left + right }
        }
    }
}
