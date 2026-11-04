package minimumoperationstomakeelementswithinksubarraysequal

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun minOperations(nums: IntArray, x: Int, k: Int): Long {
        val ops = LongArray(nums.size) { -1 }

        val m = Median()

        var l = 0
        for (r in nums.indices) {
            // EXPAND
            m.add(nums[r])

            // SHRINK
            while (r - l + 1 > x) {
                m.removeFirst()
                l++
            }

            // RECORD
            if (r - l + 1 == x) {
                ops[l] = m.getDiff()
            }
        }

        println(ops.toList())

        // -1 - impossible
        // null unknown
        val dp = Array(k + 1) { Array(nums.size) { null as Long? } }

        // null - impossible
        fun f(i: Int, k: Int): Long? {
            if (k == 0) return 0L
            if (i >= nums.size) return null
            if (dp[k][i] != null) {
                return dp[k][i].takeIf { it != -1L }
            }
            val skip = f(i + 1, k)
            val take = if (ops[i] != -1L) f(i + x, k - 1)?.let { it + ops[i] } else null
            println("i: $i, k: $k, skip: $skip, take: $take")
            val result = listOfNotNull(skip, take).minOrNull()
            dp[k][i] = result ?: -1L
            return result
        }

        val res =  f(0, k)!!
        return res
    }

    data class IndexedValue(val idx: Int, val value: Int, var isMore: Boolean)

    class Median {
        val lessOrEqual = PriorityQueue<IndexedValue>(compareBy({ -it.value }, { it.idx }))
        val more = PriorityQueue<IndexedValue>(compareBy({ it.value }, { it.idx }))
        var aliveLessOrEqual = 0
        var aliveMore = 0
        var diffLessOrEqual = 0L
        var diffMore = 0L
        var currentMedian = 0

        var elements = LinkedList<IndexedValue>()
        var lastIdx = -1

        fun getDiff(): Long {
            balance()
            val diff = diffLessOrEqual + diffMore
//            println(elements.map { it.value })
//            println("median $currentMedian, diff: $diff")
//            println()
            return diff
        }

        fun add(e: Int) {
            val el = IndexedValue(++lastIdx, e, false)
            if (e <= currentMedian) {
                lessOrEqual += el
                aliveLessOrEqual += 1
                diffLessOrEqual += currentMedian - e
                el.isMore = false
            } else {
                more += el
                aliveMore += 1
                diffMore += e - currentMedian
                el.isMore = true
            }

            elements += el
        }

        fun removeFirst() {
            val (idx, e, isMore) = elements.removeFirst()
            if (!isMore) {
                aliveLessOrEqual -= 1
                diffLessOrEqual -= currentMedian - e
            } else {
                aliveMore -= 1
                diffMore -= e - currentMedian
            }
        }

        private fun balance() {
            // ensure that aliveLessOrEqual == aliveMore
            // or aliveLessOrEqual + 1 == aliveMore
            val currentLiveIdx = elements[0].idx

            // move from right to left
            while (aliveLessOrEqual < aliveMore) {
                val el = more.poll()
                if (el.idx < currentLiveIdx) {
                    continue
                }

                // remove from right
                aliveMore--
                diffMore -= el.value - currentMedian

                // update median
                val newMedian = maxOf(el.value, currentMedian)
                if (newMedian > currentMedian) {
                    diffMore -= aliveMore * (newMedian - currentMedian)
                    diffLessOrEqual += aliveLessOrEqual * (newMedian - currentMedian)
                    currentMedian = newMedian
                }

                // add to left
                lessOrEqual += el
                aliveLessOrEqual++
                diffLessOrEqual += currentMedian - el.value
                el.isMore = false
            }

            // move from left to right
            while (aliveLessOrEqual > aliveMore + 1) {
                val el = lessOrEqual.poll()
                if (el.idx < currentLiveIdx) {
                    continue
                }

                // remove from left
                aliveLessOrEqual--
                diffLessOrEqual -= currentMedian - el.value

                // update median
                val newMedian = minOf(el.value, currentMedian)
                if (newMedian < currentMedian) {
                    diffMore += aliveMore * (currentMedian - newMedian)
                    diffLessOrEqual -= aliveLessOrEqual * (currentMedian - newMedian)
                    currentMedian = newMedian
                }

                // add to right
                more += el
                aliveMore++
                diffMore += el.value - currentMedian
                el.isMore = true
            }

            while (lessOrEqual.peek().idx < currentLiveIdx) {
                lessOrEqual.poll()
            }

            val newMedian = lessOrEqual.peek().value
            if (newMedian < currentMedian) {
                diffMore += aliveMore * (currentMedian - newMedian)
                diffLessOrEqual -= aliveLessOrEqual * (currentMedian - newMedian)
                currentMedian = newMedian
            }
        }
    }


}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(8, s.minOperations(intArrayOf(5, -2, 1, 3, 7, 3, 6, 4, -1), x = 3, k = 2))
    }

    @Test
    fun test2() {
        assertEquals(3, s.minOperations(intArrayOf(9,-2,-2,-2,1,5), x = 2, k = 2))
    }


    @Test
    fun test17() {
        val m = Solution.Median()
        m.add(7)
        m.add(3)
        m.add(6)
        m.getDiff()
        m.removeFirst()
        m.getDiff()
        m.add(4)
        m.getDiff()
    }

    @Test
    fun test8() {
        val m = Solution.Median()
        m.add(3)
        assertEquals(0, m.getDiff())
        m.add(2)
        assertEquals(1, m.getDiff())
        m.add(5)
        assertEquals(3, m.getDiff())
        m.removeFirst()
        assertEquals(3, m.getDiff())
    }

    @Test
    fun tes209() {
        val m = Solution.Median()
        m.add(-2)
        m.add(-2)
        m.getDiff()
        m.removeFirst()
        m.add(1)
        m.getDiff()
    }
}
