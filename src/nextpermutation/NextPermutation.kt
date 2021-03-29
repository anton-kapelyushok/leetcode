package nextpermutation

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun nextPermutation(nums: IntArray): Unit {
        var i = nums.size - 1
        while (i > 0) {
            if (nums[i] > nums[i - 1]) break
            i--
        }

        // swap (i - 1) with min(tail.filter { it > nums[i - 1]) }
        if (i > 0) {
            var minIdx = -1
            for (j in (i until nums.size).reversed())
                if (nums[j] > nums[i - 1] && (minIdx == -1 || nums[j] < nums[minIdx]))
                    minIdx = j
            val tmp = nums[i - 1]
            nums[i - 1] = nums[minIdx]
            nums[minIdx] = tmp
        }

        // reverse tail
        for (j in 0 until (nums.size - i) / 2) {
            val tmp = nums[nums.size - j - 1]
            nums[nums.size - j - 1] = nums[i + j]
            nums[i + j] = tmp
        }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        val input = intArrayOf(1, 2, 3)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(1, 3, 2)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test2() {
        val input = intArrayOf(3, 2, 1)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(1, 2, 3)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test3() {
        val input = intArrayOf(1, 1, 5)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(1, 5, 1)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test4() {
        val input = intArrayOf(1)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(1)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test5() {
        val input = intArrayOf(2, 3, 1)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(3, 1, 2)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test6() {
        val input = intArrayOf(3, 2, 1)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(1, 2, 3)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test7() {
        val input = intArrayOf(1, 3, 2)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(2, 1, 3)

        assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test8() {
        val input = intArrayOf(2, 3, 1, 3, 3)
        s.nextPermutation(input)
        val actual = input
        val expected = intArrayOf(2, 3, 3, 1, 3)

        assertEquals(expected.asString(), actual.asString())
    }

    fun IntArray.asString() = this.joinToString(",") { it.toString() }
}
