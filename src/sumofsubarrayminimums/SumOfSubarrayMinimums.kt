package problems

import java.util.*


object SumOfSubarrayMinimums {

    object V1 {
        class Solution {

            val mod = 1_000_000_000 + 7
            fun sumSubarrayMins(arr: IntArray): Int {
                val pq = PriorityQueue<Int>(compareBy { arr[it] })
                arr.indices.forEach { pq.offer(it) }
                val borders = TreeSet<Int>()
                borders.add(-1)
                borders.add(arr.size)
                var res = 0L
                while (pq.isNotEmpty()) {
                    val seed = pq.poll()
                    val rightBorder = borders.higher(seed)
                    val leftBorder = borders.lower(seed)
                    val min = arr[seed]
                    val leftCount = seed - leftBorder
                    val rightCount = rightBorder - seed
                    val thisRes = 1L * leftCount * rightCount * min % mod
                    res += thisRes
                    res %= mod
                    borders.add(seed)
                }
                return res.toInt()
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            with(Solution()) {
                println(sumSubarrayMins(intArrayOf(3, 1, 2, 4)))
                println(sumSubarrayMins(intArrayOf(11, 81, 94, 43, 3)))
            }
        }
    }

    object V2 {

        class Solution {
            val mod = 1_000_000_000 + 7
            fun sumSubarrayMins(arr: IntArray): Int {
                val prevMinimums = LinkedList<Int>()
                var res = 0L
                for (i in 0 .. arr.size) {

                    // arr[stack[-2]] < arr[stack[i]] >= arr[i]
                    // stack[i] contributes with this range to result
                    while (prevMinimums.isNotEmpty() && (i == arr.size || arr[prevMinimums.peek()] >= arr[i])) {
                        val mid = prevMinimums.pop()
                        val left = prevMinimums.peek() ?: -1
                        val right = i
                        val count: Long = (mid - left).toLong() * (right - mid) % mod
                        res += count * arr[mid]
                        res %= mod
                    }

                    // before and have arr[stack[-1]] < arr[i]
                    prevMinimums.push(i)
                }
                return res.toInt()
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            with(V2.Solution()) {
                println(sumSubarrayMins(intArrayOf(3, 1, 2, 4)))
                println(sumSubarrayMins(intArrayOf(11, 81, 94, 43, 3)))
            }
        }
    }
}