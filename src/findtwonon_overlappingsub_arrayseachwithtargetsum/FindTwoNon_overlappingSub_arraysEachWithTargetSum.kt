package findtwonon_overlappingsub_arrayseachwithtargetsum

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun minSumOfLengths(arr: IntArray, target: Int): Int {
        var result = Int.MAX_VALUE

        // min len on the [0..i]
        val minLens = IntArray(arr.size) { Int.MAX_VALUE }

        var currentSum = 0

        var l = 0

        for (r in arr.indices) {

            // EXPAND - add r to the current state
            currentSum += arr[r]

            // SHRINK
            while (currentSum > target) {
                currentSum -= arr[l]
                l++
            }

            // RECORD
            if (currentSum == target) {
                if (l - 1 >= 0 && minLens[l - 1] != Int.MAX_VALUE) {
                    result = minOf(
                        result,
                        minLens[l - 1] + r - l + 1
                    )
                }
                if (r - 1 >= 0) {
                    minLens[r] = minOf(minLens[r - 1], r - l + 1)
                } else {
                    minLens[r] = r - l + 1
                }
            } else {
                if (r - 1 >= 0) {
                    minLens[r] = minLens[r - 1]
                }
            }
        }


        return result.takeIf { it != Int.MAX_VALUE } ?: -1
    }
}

class Solution2 {
    fun minSumOfLengths(arr: IntArray, target: Int): Int {
        // find min sum of lengths
        // arr is positive
        val psum = IntArray(arr.size)
        psum[0] = arr[0]
        for (i in 1 until arr.size) {
            psum[i] = psum[i - 1] + arr[i]
        }

        val indicesByPsum = psum.indices.groupBy { psum[it] }.mapValues { (k, vs) -> TreeSet(vs) }

        val dp = IntArray(arr.size) { -2 } // -2 == unknown, -1 = null

        // finds an array summing to target or null, with the start at i
        fun findArray(i: Int): Int? {
            if (i >= arr.size) return null
            if (arr[i] == target) return i // probably not necessary
            if (dp[i] != -2) {
                return dp[i].takeIf { it != -1 }
            }

            // idx  = 0 1 2 3  4  5
            // arr  = 1 2 3 4  5  12, target = 12
            // psum = 1 3 6 10 15 27
            // i = 2
            val p = if (i > 0) psum[i - 1] else 0
            val rs = indicesByPsum[target + p]
            val r = rs?.ceiling(i)
            if (r != null) {
                dp[i] = r
                return r
            }
            dp[i] = -1
            return null
        }


        val arrLengths = mutableMapOf<Int, Int>()
        for (l in arr.indices) {
            val r = findArray(l)

            if (r != null) {
                arrLengths[l] = r - l + 1
            }
        }


        data class N(val idx: Int, val length: Int)

        val pq = PriorityQueue<N>(compareBy { it.length })
        arrLengths.entries.forEach { (k, v) -> pq.add(N(k, v)) }

        val minLengths = IntArray(arr.size) { -1 }
        var currentMin = pq.peek()?.length ?: return -1
        for (i in arr.indices) {
            while (pq.peek() != null && pq.peek().idx < i) {
                pq.poll()
            }
            if (pq.isEmpty()) break
            currentMin = pq.peek().length
            minLengths[i] = currentMin
        }


        // minimal array length on the [i..
        fun findMinArrayLength(i: Int): Int? {
            if (i >= arr.size) return null
            return minLengths[i].takeIf { it != -1 }
        }


        var minLength = Int.MAX_VALUE
        for (l1 in arr.indices) {
            val r1 = findArray(l1) ?: continue

            val length1 = r1 - l1 + 1
            val length2 = findMinArrayLength(r1 + 1) ?: continue

            minLength = minOf(minLength, length1 + length2)
        }

        if (minLength == Int.MAX_VALUE) return -1
        return minLength
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(2, s.minSumOfLengths(intArrayOf(3, 2, 2, 4, 3), 3))
    }

    @Test
    fun test2() {
        assertEquals(2, s.minSumOfLengths(intArrayOf(7, 3, 4, 7), 7))
    }

    @Test
    fun test3() {
        assertEquals(-1, s.minSumOfLengths(intArrayOf(4, 3, 2, 6, 2, 3, 4), 6))
    }

    @Test
    fun test4() {
        assertEquals(-1, s.minSumOfLengths(intArrayOf(1, 6, 1), 7))
    }

    @Test
    fun test5() {
        assertEquals(-1, s.minSumOfLengths(intArrayOf(5, 5, 4, 4, 5), 3))
    }
}
