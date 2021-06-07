package minimumwindowsubstring

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun minWindow(s: String, t: String): String {
        var l = 0 // inclusive
        var r = 0 // exclusive

        var minL = 0
        var minR = 0

        val rem = t.groupBy { it }.mapValues { (_, vs) -> vs.size }.toMutableMap()
        val remSet = rem.keys.toMutableSet()

        fun updateMin() {
            val currentMin = minR - minL
            val suggestedMin = r - l

            if (currentMin == 0 || suggestedMin < currentMin) {
                minL = l
                minR = r
            }

        }

        fun condition(): Boolean {
            return remSet.isEmpty()
        }

        fun moveRightPointer() {
            val c = s[r]

            if (c in rem) {
                rem[c] = rem[c]!! - 1
                if (rem[c] == 0) {
                    remSet -= c
                }
            }

            r++
        }

        fun moveLeftPointer() {
            val c = s[l]

            if (c in rem) {
                rem[c] = rem[c]!! + 1
                if (rem[c] == 1) {
                    remSet += c
                }
            }

            l++
        }

        while (!condition() && r < s.length) {
            moveRightPointer()

            while (condition() && l < r) {
                updateMin()
                moveLeftPointer()
            }
        }

        return s.substring(minL until minR)
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("BANC", s.minWindow("ADOBECODEBANC", "ABC"))
    }

}
