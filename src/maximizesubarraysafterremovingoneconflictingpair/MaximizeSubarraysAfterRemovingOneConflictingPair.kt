package maximizesubarraysafterremovingoneconflictingpair

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun maxSubarrays(n: Int, conflictingPairs: Array<IntArray>): Long {
        conflictingPairs.forEach { it.sort() }
        val byRight =
            conflictingPairs.groupBy { it.last() }.mapValues { (_, vs) -> vs.map { it.first() }.sortedDescending() }

        var sum = 0L + n
        var r = 1

        val pairs = PriorityQueue<Pair<Int, Int>>(compareBy<Pair<Int, Int>> { (l, r) -> -l })
        val scores = mutableMapOf<Pair<Int, Int>, Long>()

        while (true) {
            (byRight[r] ?: mutableListOf()).forEach { pairs.offer(it to r) }

            // get the nastiest pairs
            val dominating = pairs.poll()
            val dominated = pairs.poll()

            // put them back
            if (dominated != null) pairs.offer(dominated)
            if (dominating != null) pairs.offer(dominating)

            val l = (dominating?.first ?: 0) + 1

            if (dominating != null) {
                val dominatedL = dominated?.first ?: 0
                scores[dominating] = (scores[dominating] ?: 0L) + dominating.first - dominatedL
            }

//            println("($l-$r)")
            sum += r - l
            if (r == n) break
            else r++
        }

        return sum + scores.maxOf { it.value }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            9, s.maxSubarrays(
                4,
                arrayOf(
                    intArrayOf(2, 3),
                    intArrayOf(1, 4),
                )
            )
        )
    }

    @Test
    fun test2() {
        println(s.maxSubarrays(5, arrayOf(intArrayOf(2, 5))))
        println(s.maxSubarrays(5, arrayOf(intArrayOf(3, 4))))
        println(s.maxSubarrays(5, arrayOf(intArrayOf(2, 5), intArrayOf(3, 4))))
        println(s.maxSubarrays(5, arrayOf(intArrayOf(1, 2), intArrayOf(2, 5), intArrayOf(3, 5))))
        assertEquals(
            12, s.maxSubarrays(
                5, arrayOf(
                    intArrayOf(1, 2),
                    intArrayOf(2, 5),
                    intArrayOf(3, 5),
                )
            )
        )
    }

    @Test
    fun test3() {
        assertEquals(
            4999950001,
            s.maxSubarrays(
                100000,
                arrayOf(
                    intArrayOf(50000, 50001),
                    intArrayOf(99999, 100000),
                )
            )
        )
    }
}
