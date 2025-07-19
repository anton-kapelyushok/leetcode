package problems

import java.util.*

object SingleThreadedCPU {

    class Solution {
        fun getOrder(tasks: Array<IntArray>): IntArray {
            if (tasks.isEmpty()) return intArrayOf()

            data class Task(val idx: Int, val enqueueTime: Int, val processingTime: Int)

            val taskQueue = tasks.mapIndexed { idx, (a, b) -> Task(idx, a, b) }
                .sortedBy { it.enqueueTime }
                .toCollection(LinkedList())

            val taskPool = PriorityQueue<Task>(compareBy({ it.processingTime }, { it.idx }))

            var t = 0
            val result = LinkedList<Int>()

            while (taskPool.isNotEmpty() || taskQueue.isNotEmpty()) {
                if (taskPool.isNotEmpty()) {
                    val top = taskPool.poll()
                    result += top.idx
                    t += top.processingTime
                } else {
                    val topTask = taskQueue.poll()
                    taskPool.offer(topTask)

                    t = topTask.enqueueTime
                }

                while (taskQueue.isNotEmpty() && taskQueue.peek().enqueueTime <= t) {
                    taskPool.offer(taskQueue.poll())
                }
            }

            return result.toIntArray()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(
                getOrder(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(2, 4),
                        intArrayOf(3, 2),
                        intArrayOf(4, 1),
                    )
                ).toList()
            )
            println(
                getOrder(
                    arrayOf(
                        intArrayOf(7, 10),
                        intArrayOf(7, 12),
                        intArrayOf(7, 5),
                        intArrayOf(7, 4),
                        intArrayOf(7, 2)
                    )
                ).toList()
            )
        }
    }
}