package problems

import java.util.*

object IPO {
    class Solution {
        fun findMaximizedCapital(k: Int, w: Int, profits: IntArray, capital: IntArray): Int {
            data class Project(val profit: Int, val capital: Int)

            val notAvailableProjects = PriorityQueue<Project>(compareBy { it.capital })
            val availableProjects = PriorityQueue<Project>(compareBy { -it.profit })

            profits.zip(capital)
                .forEach { (profit, capital) -> notAvailableProjects += Project(profit, capital) }

            var currentCapital = w

            for (i in 1..k) {
                while (notAvailableProjects.isNotEmpty() && notAvailableProjects.peek().capital <= currentCapital) {
                    availableProjects += notAvailableProjects.poll()
                }
                if (availableProjects.isEmpty()) break
                currentCapital += availableProjects.poll().profit
            }

            return currentCapital
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(findMaximizedCapital(k = 2, w = 0, profits = intArrayOf(1, 2, 3), capital = intArrayOf(0, 1, 1)))
            println(findMaximizedCapital(k = 3, w = 0, profits = intArrayOf(1, 2, 3), capital = intArrayOf(0, 1, 2)))
            println(findMaximizedCapital(k = 10, w = 0, profits = intArrayOf(1, 2, 3), capital = intArrayOf(0, 1, 2)))
        }
    }
}