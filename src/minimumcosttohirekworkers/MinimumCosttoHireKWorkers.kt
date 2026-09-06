package minimumcosttohirekworkers

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun mincostToHireWorkers(quality: IntArray, wage: IntArray, k: Int): Double {
        val coeffs = wage.indices.map { wage[it].toDouble() / quality[it] }.sorted()

        data class Worker(
            val quality: Int,
            val coeff: Double,
        )

        val availableWorkers = PriorityQueue<Worker>(compareBy { it.quality })
        wage.indices.forEach {
            val w = wage[it]
            val q = quality[it]
            availableWorkers += Worker(q, w.toDouble() / q)
        }


        val currentWorkers = PriorityQueue<Worker>(compareBy { -it.coeff })
        var currentQuality = 0
        var result = Double.MAX_VALUE
        for (coeff in coeffs.asReversed()) {
            // remove workers that we cannot afford anymore
            while (currentWorkers.isNotEmpty() && currentWorkers.peek().coeff > coeff) {
                val w = currentWorkers.poll()
                currentQuality -= w.quality
            }

            // add replacement workers
            while (currentWorkers.size != k && availableWorkers.isNotEmpty()) {
                val a = availableWorkers.poll()
                if (a.coeff > coeff) continue
                currentWorkers += a
                currentQuality += a.quality
            }

            if (currentWorkers.size != k) break
            result = minOf(result, coeff * currentQuality)
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            105.00000, s.mincostToHireWorkers(
                intArrayOf(10, 20, 5),
                intArrayOf(70, 50, 30),
                2
            ),
            absoluteTolerance = 0.00001
        )
    }

    @Test
    fun test2() {
        assertEquals(
            30.66667, s.mincostToHireWorkers(
                intArrayOf(3, 1, 10, 10, 1),
                intArrayOf(4, 8, 2, 2, 7),
                3
            ),
            absoluteTolerance = 0.00001
        )
    }

}
