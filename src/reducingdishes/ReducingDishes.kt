package problems

object ReducingDishes {
    class Solution {
        fun maxSatisfaction(satisfaction: IntArray): Int {
            // satisfaction gained via 1 tick delay
            val oneTickSatisfaction = satisfaction.filter { it > 0 }.sum()

            val negative = satisfaction.filter { it < 0 }.sortedDescending()
            val positive = satisfaction.filter { it >= 0 }.sorted()

            if (negative.isEmpty()) {
                var result = 0
                for (i in 1..positive.size) {
                    result += i * positive[i - 1]
                }
                return result
            }

            val psum = IntArray(negative.size)
            psum[0] = negative[0]
            for (i in 1 until negative.size) {
                psum[i] = psum[i - 1] + negative[i]
            }

            var bestTakeNegative = 0
            var bestGain = 0
            var lastLoss = 0
            for (i in negative.indices) {
                val thisLoss = lastLoss + psum[i]
                val thisGain = thisLoss + (i + 1) * oneTickSatisfaction
                if (thisGain > bestGain) {
                    bestGain = thisGain
                    bestTakeNegative = i + 1
                }
                lastLoss = thisLoss
            }

            var result = 0
            for (i in 0 until bestTakeNegative) {
                result += (bestTakeNegative - i) * negative[i]
            }

            var i = bestTakeNegative
            for (p in positive) {
                result += (i + 1) * p
                i++
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(maxSatisfaction(intArrayOf(-1, -8, 0, 5, -9)))
            println(maxSatisfaction(intArrayOf(4, 3, 2)))
            println(maxSatisfaction(intArrayOf(-1, -4, -5)))
            println(maxSatisfaction(intArrayOf(2, -2, -3, 1)))
        }
    }
}