package freedomtrail;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {

    fun findRotateSteps(ring: String, key: String): Int {
        val dp = Array(key.length + 1) { IntArray(ring.length) }

        val closestLeftCache = Array(ring.length) { IntArray('z' - 'a' + 1) { -1 }}
        val closestRightCache = Array(ring.length) { IntArray('z' - 'a' + 1) { -1 }}

        fun stepsToClosestLeft(i: Int, c: Char): Int {
            if (closestLeftCache[i][c - 'a'] != -1) return closestLeftCache[i][c - 'a']

            var j = i
            var steps = 0

            while (ring[j] != c) {
                steps++
                j = (j - 1 + ring.length) % ring.length
            }
            closestLeftCache[i][c - 'a'] = steps
            return closestLeftCache[i][c - 'a']

        } // O(1)

        fun stepsToClosestRight(i: Int, c: Char): Int {
            if (closestRightCache[i][c - 'a'] != -1) return closestRightCache[i][c - 'a']

            var j = i
            var steps = 0

            while (ring[j] != c) {
                steps++
                j = (j + 1) % ring.length
            }
            closestRightCache[i][c - 'a'] = steps
            return closestRightCache[i][c - 'a']

        } // O(1)

        for (keyStart in key.length downTo 0) {
            for (ringPos in ring.indices) {
                if (keyStart == key.length) {
                    dp[keyStart][ringPos] = 0
                    continue
                }

                val requiredChar = key[keyStart]

                val stepsToClosestLeft = stepsToClosestLeft(ringPos, requiredChar)
                val stepsToClosestRight = stepsToClosestRight(ringPos, requiredChar)

                val left = dp[keyStart + 1][(ringPos - stepsToClosestLeft + ring.length) % ring.length] + stepsToClosestLeft

                val right = dp[keyStart + 1][(ringPos + stepsToClosestRight) % ring.length] + stepsToClosestRight

                dp[keyStart][ringPos] = Math.min(left, right) + 1
            }
        }

        return dp[0][0]
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.findRotateSteps("godding", "gd"))
    }

}
