package problems

import java.util.*

object MostProfitAssigningWork {
    class Solution {
        fun maxProfitAssignment(difficulty: IntArray, profit: IntArray, worker: IntArray): Int {
            if (difficulty.isEmpty()) return 0
            if (worker.isEmpty()) return 0

            data class Job(val difficulty: Int, val profit: Int)

            val jobs = difficulty.zip(profit) { d, p -> Job(d, p) }
            val sortedJobs = jobs.sortedWith(compareBy({ -it.profit }, { it.difficulty }))// most profitable first
            val sensibleJobs = mutableListOf<Job>()
            sensibleJobs += sortedJobs[0]

            for (i in 1 until sortedJobs.size) {
                val lastJob = sensibleJobs.last()
                val thatJob = sortedJobs[i]
                if (lastJob.profit == thatJob.profit) continue // difficulty is higher
                if (thatJob.difficulty >= lastJob.difficulty) continue
                sensibleJobs += thatJob
            }

            val jobsByDifficulty = TreeMap(sensibleJobs.associateBy { it.difficulty })
            var result = 0

            for (skill in worker) {
                val d = jobsByDifficulty.floorKey(skill) ?: continue
                result += jobsByDifficulty.getValue(d).profit
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                maxProfitAssignment(
                    intArrayOf(2, 4, 6, 8, 10),
                    intArrayOf(10, 20, 30, 40, 50),
                    intArrayOf(4, 5, 6, 7),
                )
            )
            println(
                maxProfitAssignment(
                    intArrayOf(5, 4, 10),
                    intArrayOf(10, 10, 9),
                    intArrayOf(4, 5, 6, 7),
                )
            )
        }
    }
}