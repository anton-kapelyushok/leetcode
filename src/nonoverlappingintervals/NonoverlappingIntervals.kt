package nonoverlappingintervals

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        val q = PriorityQueue<IntArray>(compareBy { it[1] })

        var lastEnd = Int.MIN_VALUE
        var taken = 0

        intervals.forEach { q += it }
        while (q.isNotEmpty()) {
            val i = q.poll()
            if (i[0] >= lastEnd) {
                taken++
                lastEnd = i[1]
            }
        }

        return intervals.size - taken
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            1, s.eraseOverlapIntervals(
                arrayOf(
                    intArrayOf(1, 2),
                    intArrayOf(2, 3),
                    intArrayOf(3, 4),
                    intArrayOf(1, 3),
                )
            )
        )
    }

}
