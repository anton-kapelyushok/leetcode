package coursescheduleii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val result = mutableListOf<Int>()
        val taken = mutableSetOf<Int>()

        val dependencies = mutableMapOf<Int, MutableSet<Int>>()
        for ((a, b) in prerequisites) { // a depends on b
            dependencies[a] = (dependencies[a] ?: mutableSetOf())
            dependencies[a]!! += b
        }

        val required = mutableSetOf<Int>()


        var possible = true
        fun take(e: Int) {
            if (e in taken) return
            if (e in required) {
                possible = false
                return
            }
            required += e
            val deps = dependencies[e] ?: setOf()
            for (d in deps) {
                take(d)
            }
            taken += e
            result += e
        }

        (0 until numCourses).forEach { take(it) }

        if (!possible) {
            return intArrayOf()
        }

        return result.toIntArray()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.solve())
    }
}
