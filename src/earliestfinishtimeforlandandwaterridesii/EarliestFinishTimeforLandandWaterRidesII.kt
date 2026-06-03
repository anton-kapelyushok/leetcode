package earliestfinishtimeforlandandwaterridesii

import org.junit.jupiter.api.Test

class Solution {
    fun earliestFinishTime(
        landStartTime: IntArray,
        landDuration: IntArray,

        waterStartTime: IntArray,
        waterDuration: IntArray
    ): Int {

        fun f(aStart: IntArray, aDuration: IntArray, bStart: IntArray, bDuration: IntArray): Int {
            val minADuration = aStart.indices.minOf { aStart[it] + aDuration[it] }

            val alreadyOpenedDurations = bStart.indices.filter { bStart[it] <= minADuration }.map { bDuration[it] }
            val waitForOpenDurations = bStart.indices.filter { bStart[it] > minADuration }.map { bDuration[it] + bStart[it] - minADuration }

            val minBDuration = (alreadyOpenedDurations + waitForOpenDurations).min()

            return minADuration + minBDuration
        }

        return minOf(
            f(landStartTime, landDuration, waterStartTime, waterDuration),
            f(waterStartTime, waterDuration, landStartTime, landDuration),
        )
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        val result = s.earliestFinishTime(
            intArrayOf(2, 8),
            intArrayOf(4, 1),
            intArrayOf(6),
            intArrayOf(3),
        )
        println(result)
    }

    @Test
    fun test2() {
        val result = s.earliestFinishTime(
            intArrayOf(5),
            intArrayOf(3),
            intArrayOf(1),
            intArrayOf(10),
        )
        println(result)
    }



    @Test
    fun test3() {
        val result = s.earliestFinishTime(
            intArrayOf(82, 14),
            intArrayOf(42, 30),
            intArrayOf(6, 54),
            intArrayOf(91, 71),
        )
        println(result)
    }

}
