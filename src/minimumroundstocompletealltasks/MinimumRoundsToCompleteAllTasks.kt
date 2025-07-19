package problems

object MinimumRoundsToCompleteAllTasks {
    class Solution {
        fun minimumRounds(tasks: IntArray): Int {
            val grouped = tasks.groupBy { it }.mapValues { (_, vs) -> vs.size }
            if (grouped.values.any { it == 1 }) return -1
            return grouped.values.sumBy { (it + 2) / 3 }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(minimumRounds(intArrayOf(2, 2, 3, 3, 2, 4, 4, 4, 4, 4)))
            println(minimumRounds(IntArray(5)))
            println(minimumRounds(IntArray(6)))
            println(minimumRounds(IntArray(7)))
            println(minimumRounds(IntArray(11)))
            println(minimumRounds(IntArray(12)))
        }
    }
}