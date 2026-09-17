package _132pattern1

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun find132pattern(nums: IntArray): Boolean {

        // valK is the valid k, meaning there is a j already
        // if we find something less than that we are the best

        // valJ must be the largest thing we have so far
        // valK must be the second largest thing we have
        var valK = Int.MIN_VALUE

        // I know it is a stack problem, let's have it
        // queue of what exactly though?
        // - these are candidates to become K!
        // that means as soon as we meet J > K
        // we can use the candidate as K
        val valKCandidates = LinkedList<Int>()

        for (idx in nums.indices.reversed()) {
            val valI = nums[idx]
            if (valI < valK) return true

            // could not make nums[idx] as valid i

            // it still can be J!
            // and hence allow some more K candidates
            val valJ = valI
            while (valKCandidates.isNotEmpty() && valKCandidates.peekFirst() < valJ) {
                valK = valKCandidates.pollFirst()
            }

            // now that we exhausted all valid K candidates, offer oursleves as a candidate
            // we know we will be the lowest one, so add ourselves first!
            valKCandidates.offerFirst(valJ)
        }

        return false
    }
}


class Solution2 {
    // all reversed
    fun find132pattern(nums: IntArray): Boolean {
        val nextGreater = IntArray(nums.size) { -1 }

        val nextGreaterQ = LinkedList<Int>()
        for (i in nums.indices.reversed()) {
            val n = nums[i]
            // we are the next greater element for the next greater q, while we are bigger then the first one
            while (nextGreaterQ.isNotEmpty() && nums[nextGreaterQ.peek()] < n) {
                nextGreater[nextGreaterQ.poll()] = i
            }

            nextGreaterQ.addFirst(i)
        }

        // smallest on [-i-1..-1]
        val smallest = IntArray(nums.size) { -1 }
        smallest[0] = 0
        for (i in 1 until smallest.size) {
            smallest[i] = smallest[i - 1]
            if (nums[i] < nums[smallest[i - 1]]) {
                smallest[i] = i
            }
        }

        for (k in nums.indices.reversed()) {
            val j = nextGreater[k]
            if (j <= 0) continue // first element or not found
            val i = smallest[j - 1]

            if (nums[i] < nums[k] && nums[k] < nums[j]) {
                return true
            }
        }

        return false
    }
}

class Solution1 {
    fun find132pattern(nums: IntArray): Boolean {
        var currentDetachedL: Int? = null

        data class Interval(val l: Int, val r: Int)

        val queue = LinkedList<Interval>()

        outer@ for (n in nums) {
            // create an interval from detached, it (probably) goes correctly through the interval handling
            if (currentDetachedL != null && n > currentDetachedL) {
                queue.addFirst(Interval(currentDetachedL, n))
                currentDetachedL = null
            }

            if (queue.isNotEmpty() && n > queue.peekFirst().l) {
                val l = queue.peek().l

                inner@ while (queue.isNotEmpty()) {
                    val (il, ir) = queue.peekFirst()
                    if (n in il + 1..ir - 1) return true

                    if (n >= ir) queue.pollFirst()
                    else break@inner
                }

                queue.addFirst(Interval(l, n))

                continue@outer
            } else {
                currentDetachedL = n
            }
        }

        return false
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.find132pattern(intArrayOf(3, 1, 4, 2)))
    }

    @Test
    fun test2() {
        assertEquals(true, s.find132pattern(intArrayOf(-1, 3, 2, 0)))
    }

    @Test
    fun test3() {
        assertEquals(false, s.find132pattern(intArrayOf(1, 2, 3, 4)))
    }

    @Test
    fun test4() {
        assertEquals(
            true, s.find132pattern(
                intArrayOf(
                    20, 25,
                    15, 20,
                    5, 10,
                    12, 8
                )
            )
        )
    }

    @Test
    fun test5() {
        assertEquals(
            true, s.find132pattern(
                intArrayOf(
                    3, 5,
                    0, 3,

                    4 // 3 5 4
                )
            )
        )
    }

    @Test
    fun test6() {
        assertEquals(
            true, s.find132pattern(
                intArrayOf(
                    40, 50,
                    25, 35,
                    15, 35,

                    20 // 15..35
                )
            )
        )
    }

    @Test
    fun test7() {
        assertEquals(
            false, s.find132pattern(
                intArrayOf(
                    1,
                    0, 1,
                    -4, -3
                )
            )
        )
    }
}
