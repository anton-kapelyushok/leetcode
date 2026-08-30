package countintegersinintervals

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class CountIntervals() {
    val intervals = TreeMap<Int, Int>()
    var count = 0

    fun add(start: Int, end: Int) {
        var start = start
        var end = end

        while (true) {
            val interval = intervals.floorEntry(end) ?: break
            if (interval.value < start) break
            start = minOf(start, interval.key)
            end = maxOf(end, interval.value)
            intervals.remove(interval.key)
            count -= interval.value - interval.key + 1
        }

        intervals[start] = end
        count += end - start + 1
    }

    fun count(): Int {
        return count
    }

}

class SolutionTest {

    @Test
    fun test1() {
        val c = CountIntervals()

        c.add(2, 3)
        c.add(7, 10)
        assertEquals(6, c.count())
        c.add(5, 8)
        assertEquals(8, c.count())
    }

    @Test
    fun test2() {
        val c = CountIntervals()

        c.add(8, 43)
        c.add(13, 16)
        c.add(26, 33)
        c.add(28, 36)
        c.add(29, 37)
        assertEquals(36, c.count())
    }

}
