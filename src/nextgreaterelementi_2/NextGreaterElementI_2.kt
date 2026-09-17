package nextgreaterelementi_2

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
        val indicesByNum2 = nums2.indices.associateBy { nums2[it] }

        val nextGreater2 = IntArray(nums2.size) { -1 }
        val indicesLookingForAGreaterElement = LinkedList<Int>()
        for (i in nums2.indices) {
            while (indicesLookingForAGreaterElement.isNotEmpty() &&
                nums2[indicesLookingForAGreaterElement.peek()] < nums2[i]
            ) {
                nextGreater2[indicesLookingForAGreaterElement.poll()] = nums2[i]
            }

            indicesLookingForAGreaterElement.addFirst(i)
        }

        return nums1.map {
            indicesByNum2[it]!!
        }.map {
            nextGreater2[it]
        }.toIntArray()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(intArrayOf(), s.nextGreaterElement(intArrayOf(), intArrayOf()))
    }
}
