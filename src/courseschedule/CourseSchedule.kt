package problems

object CourseSchedule {

    class Solution {
        fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
            var prerequisites = prerequisites.toList()
            while (prerequisites.isNotEmpty()) {
                val prevSize = prerequisites.size
                val froms = prerequisites.map { (from) -> from }.toSet()
                prerequisites = prerequisites.filter { (_, to) -> to in froms }
                if (prerequisites.size == prevSize) return false
            }
            return true
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(canFinish(2, arrayOf(intArrayOf(1, 0))))
            println(canFinish(2, arrayOf(intArrayOf(0, 1), intArrayOf(1, 0))))
        }
    }
}