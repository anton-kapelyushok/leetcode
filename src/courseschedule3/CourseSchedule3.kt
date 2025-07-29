package courseschedule3

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun scheduleCourse(courses: Array<IntArray>): Int {
        courses.sortBy { it[1] }

        val pq = PriorityQueue<Int>(compareBy { -it })

        var currentDuration = 0
        var currentCourses = 0
        for (course in courses) {
            val (duration, deadline) = course
            val newDuration = currentDuration + duration
            if (newDuration <= deadline) {
                pq.offer(duration)
                currentDuration = newDuration
                currentCourses++
            } else if (pq.isNotEmpty()) {
                val topDuration = pq.peek()
                if (duration < topDuration) {
                    pq.poll()
                    pq.offer(duration)
                    currentDuration = currentDuration - topDuration + duration
                }
            }
        }

        return currentCourses
    }

}


class DpSolution {
    fun scheduleCourse(courses: Array<IntArray>): Int {
        courses.sortBy { it[1] }

        var prevDay = IntArray(courses.size + 1)

        for (day in 1..courses.size) {
            val duration = courses[day - 1][0]
            val deadline = courses[day - 1][1]

            val newDay = IntArray(courses.size + 1)

            for (coursesTaken in 1..day) {
                var a = 0
                if (prevDay[coursesTaken - 1] != 0 || coursesTaken == 1) {
                    val newTime = prevDay[coursesTaken - 1] + duration
                    if (newTime <= deadline) a = newTime
                }
                val b = prevDay[coursesTaken]
                newDay[coursesTaken] = listOf(a, b).filter { it != 0 }.minOrNull() ?: 0
            }

            prevDay = newDay
        }

        return (courses.size downTo 0).firstOrNull { prevDay[it] != 0 } ?: 0
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(3, s.scheduleCourse(arrayOf(intArrayOf(100, 200), intArrayOf(200, 1300), intArrayOf(1000, 1250), intArrayOf(2000, 3200))))
    }

    @Test
    fun test2() {
        assertEquals(2, s.scheduleCourse(arrayOf(intArrayOf(1, 2), intArrayOf(2, 3))))
    }

}
