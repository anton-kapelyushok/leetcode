package hitcounter

import hitcounter.Solution.HitCounter
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals


class Solution {
    class HitCounter {
        var hits = 0

        // in follow up we would store the Ts(ts: Int, count: Int)
        val q = LinkedList<Int>()

        fun hit(ts: Int) {
            moveToTs(ts)
            q.addLast(ts)
            hits++
        }

        // returns hits in (ts-300; ts] (check the off by one here)
        fun getHits(ts: Int): Int {
            moveToTs(ts)
            return hits
        }

        private fun moveToTs(ts: Int) {
            while (q.peekFirst() != null && q.peekFirst() <= ts - 300) {
                q.pollFirst()
                hits--
            }
        }
    }
}

class SolutionTest {
    @Test
    fun test1() {
        val counter = HitCounter()

        counter.hit(1)
        counter.hit(2)
        counter.hit(3)

        counter.getHits(4)
        assertEquals(3, counter.getHits(4))

        counter.hit(300)
        assertEquals(4, counter.getHits(300))
        assertEquals(3, counter.getHits(301))
    }
}