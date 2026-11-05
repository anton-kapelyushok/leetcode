package findthedistancevaluebetweentwoarrays

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // # of elements in arr1 s.t.
    // no element in arr2 where |arr1[i]-arr2[j]| <= d
    // all elements in arr2 where |arr1[i]-arr2[j]| > d
    fun findTheDistanceValue(arr1: IntArray, arr2: IntArray, d: Int): Int {
        arr1.sort()
        arr2.sort()

        if (arr2.isEmpty()) {
            return arr1.size
        }

        var result = 0
        var i = 0
        for (v in arr1) {
            // INVARIANT - v < arr2[i] (or out of bounds)
            while (i in arr2.indices && arr2[i] <= v) {
                i++
            }

            if (i - 1 in arr2.indices && v - arr2[i - 1] <= d) {
//                result++
            } else if (i in arr2.indices && arr2[i] - v <= d) {
//                result++
            } else {
                result++
            }
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            2,
            s.findTheDistanceValue(intArrayOf(4, 5, 8), intArrayOf(10, 9, 1, 8), 2),
        )
    }
}
