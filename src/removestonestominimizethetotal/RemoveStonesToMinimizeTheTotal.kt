package problems

import java.util.*

object RemoveStonesToMinimizeTheTotal {
    class Solution {
        fun minStoneSum(piles: IntArray, k: Int): Int {
            if (piles.isEmpty()) return 0

            var total = piles.sum()

            val pq = PriorityQueue<Int>(compareBy { -it })
            piles.forEach { pq.offer(it) }
            for (i in 1..k) {
                var top = pq.poll()
                total -= top
                top -= top / 2
                total += top
                pq.offer(top)
            }

            return total
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(minStoneSum(intArrayOf(5, 4, 9), 2))
        }
    }
}