package problems

object CarFleet {

    class Solution {

        data class Car(val position: Int, val speed: Int)

        fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
            val cars = position
                .zip(speed)
                .map { (pos, speed) -> Car(pos, speed) }
                .sortedBy { -it.position }

            val timesToGetToTarget = cars.map { (position, speed) ->
                (target - position).toDouble() / speed
            }

            var clusters = 1
            var currentTime = timesToGetToTarget[0]

            for (i in 1 until timesToGetToTarget.size) {
                if (timesToGetToTarget[i] > currentTime) {
                    clusters++
                    currentTime = timesToGetToTarget[i]
                }
            }

            return clusters
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(carFleet(12, intArrayOf(10, 8, 0, 5, 3), intArrayOf(2, 4, 1, 1, 3)))
            println(carFleet(10, intArrayOf(3), intArrayOf(3)))
            println(carFleet(100, intArrayOf(0, 2, 4), intArrayOf(4, 2, 1)))
        }
    }
}