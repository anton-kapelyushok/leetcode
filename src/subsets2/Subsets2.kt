package subsets2;

class Solution {
    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        val counts = IntArray(21)
        nums.forEach { n -> counts[n + 10]++ }


        return emit(0, counts)
    }

    fun emit(start: Int, counts: IntArray): List<List<Int>> {
        if (start == counts.size) return listOf(listOf())

        val next = emit(start + 1, counts)
        return (0..counts[start]).flatMap { i ->
            val left = (0 until i).map { start - 10 }
            next.map { right -> left + right }
        }
    }
}
