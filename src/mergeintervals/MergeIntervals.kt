package mergeintervals

import org.junit.jupiter.api.Test

class Solution {
    data class Interval(val start: Int, var end: Int)

    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        var intervals = intervals.map { (start, end) -> Interval(start, end) }
        intervals = intervals.sortedWith(compareBy({ it.start }, { it.end }))

        val result = mutableListOf<Interval>()
        var currentInterval = intervals[0]
        for (interval in intervals) {
            if (interval.start in currentInterval.start..currentInterval.end) {
                currentInterval.end = maxOf(currentInterval.end, interval.end)
            } else {
                result += currentInterval
                currentInterval = interval
            }
        }
        result += currentInterval
        return result.map { intArrayOf(it.start, it.end) }.toTypedArray()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }

}
