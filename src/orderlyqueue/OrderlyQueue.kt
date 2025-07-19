package problems

object OrderlyQueue {

    class Solution {
        fun orderlyQueue(s: String, k: Int): String {
            if (k >= 2) return String(s.toCharArray().sortedArray())
            return s.indices
                .map { idx -> s.slice(idx until s.length) + s.slice(0 until idx) }
                .min()!!
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(orderlyQueue("acb", 1))
            println(orderlyQueue("baaca", 3))
        }
    }
}