package dailytemperatures2

import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size) { 0 }
        val waitingIndices = LinkedList<Int>()
        for (i in temperatures.indices) {
            val t = temperatures[i]
            while (waitingIndices.isNotEmpty() && waitingIndices.peek().let { temperatures[it] } < t) {
                val j = waitingIndices.pollFirst()
                result[j] = i - j
            }
            waitingIndices.addFirst(i)
        }
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }
}
