package minimumdifferenceinsumsafterremovalofelements

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals

class Solution {
    fun minimumDifference(nums: IntArray): Long {
        val n = nums.size / 3

        var leftSum = 0L
        val leftPq = PriorityQueue<Int>(compareBy { -it })
        for (i in 0 until n) {
            leftSum += nums[i]
            leftPq += nums[i]
        }
        val leftSums = LongArray(n + 1)
        leftSums[0] = leftSum

        for (m in 0 until n) {
            // if minimal element from left is less than middle -> swap
            if (leftPq.peek() > nums[n + m]) {
                leftSum -= leftPq.poll()
                leftSum += nums[n + m]

                leftPq += nums[n + m]
            }

            leftSums[m + 1] = leftSum
        }

        var rightSum = 0L
        val rightPq = PriorityQueue<Int>(compareBy { it })
        val rightSums = LongArray(n + 1)
        for (i in 2 * n until nums.size) {
            rightSum += nums[i]
            rightPq += nums[i]
        }
        rightSums[n] = rightSum
        for (m in 0 until n) {
            if (rightPq.peek() < nums[2 * n - m - 1]) {
                rightSum -= rightPq.poll()
                rightPq += nums[2 * n - m - 1]
                rightSum += nums[2 * n - m - 1]
            }

            rightSums[n - m - 1] = rightSum
        }

        return leftSums.zip(rightSums).minOf { (l, r) -> l - r }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(-1, s.minimumDifference(intArrayOf(3, 1, 2)))
    }

    @Test
    fun test2() {
        assertEquals(1, s.minimumDifference(intArrayOf(7, 9, 5, 8, 1, 3)))
    }

    @Test
    fun test3() {
        val n = 50 * 3
        val array = (0 until n).map { Random.nextInt() }.toIntArray()
        s.minimumDifference(array)
    }


    @Test
    fun test4() {
        // @formatter:off
        assertEquals(-14, s.minimumDifference(intArrayOf(16,46,43,41,42,14,36,49,50,28,38,25,17,5,18,11,14,21,23,39,23)))
        // @formatter:on
    }

    @Test
    fun test5() {
        intArrayOf(
            16, 46, 43, 41, 42, 14, 36,
            49, 50, 28, 38, 25, 17, 5,
            18, 11, 14, 21, 23, 39, 23
        )


        intArrayOf(
            16, 46, 43, 41, 42, 14, 36,
            49, 50, 28, 38, 25, 17, 5,
            18, 21, 23, 39, 23
        )
    }
}
