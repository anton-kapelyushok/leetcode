package problems

import java.util.*

object MaximumBagsWithFullCapacityOfRocks {
    class Solution {
        fun maximumBags(capacity: IntArray, rocks: IntArray, additionalRocks: Int): Int {
            val pq = PriorityQueue<Int>(compareBy { (capacity[it] - rocks[it]) })
            var rocksLeft = additionalRocks
            var result = 0

            capacity.indices.forEach {
                if (capacity[it] - rocks[it] == 0) {
                    result++
                    return@forEach
                } else pq.offer(it)
            }

            while (rocksLeft != 0 && pq.isNotEmpty()) {
                val top = pq.poll()
                if (capacity[top] - rocks[top] > rocksLeft) return result
                rocksLeft -= capacity[top] - rocks[top]
                result += 1
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(maximumBags(intArrayOf(2, 3, 4, 5), intArrayOf(1, 2, 4, 4), 2))
        }
    }
}