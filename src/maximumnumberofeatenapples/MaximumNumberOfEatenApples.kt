package maximumnumberofeatenapples;

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun eatenApples(apples: IntArray, days: IntArray): Int {
        val pq = PriorityQueue<Apples>()

        var eaten = 0
        var day = 0
        while (pq.isNotEmpty() || day in days.indices) {

            if (day in days.indices && days[day] != 0 && apples[day] != 0) {
                pq.add(Apples(apples[day], days[day] + day))
            }

            while (pq.isNotEmpty()) {
                val next = pq.peek()
                if (day >= next.consumeBefore) {
                    pq.poll()
                } else break
            }

            val next = pq.peek()
            if (next == null) {
                day++
                continue
            }

            next.count--
            eaten++
            if (next.count == 0) {
                pq.poll()
            }

            day++
        }

        return eaten
    }

    data class Apples(
            var count: Int,
            var consumeBefore: Int
    ) : Comparable<Apples> {
        override fun compareTo(other: Apples): Int {
            return this.consumeBefore.compareTo(other.consumeBefore)
        }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(7, s.eatenApples(intArrayOf(1, 2, 3, 5, 2), intArrayOf(3, 2, 1, 4, 2)))
    }


    @Test
    fun test2() {
        assertEquals(8, s.eatenApples(intArrayOf(2, 1, 1, 4, 5), intArrayOf(10, 10, 6, 4, 2)))
    }

}
