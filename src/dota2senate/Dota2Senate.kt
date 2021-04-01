package dota2senate;

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun predictPartyVictory(senate: String): String {

        var rCount = senate.count { it == 'R' }
        var dCount = senate.count { it == 'D' }

        val s = LinkedList(senate.toList())
        while (rCount != 0 && dCount != 0) {
            val senator = s.pollFirst()
            var currentCount = 1
            var difference = 1
            while (difference > 0 && s.isNotEmpty()) {
                val nextSenator = s.pollFirst()
                if (nextSenator == senator) {
                    currentCount++
                    difference++
                } else {
                    difference--
                    if (nextSenator == 'R') rCount-- else dCount--
                }
            }
            (0 until currentCount).forEach { s.offer(senator) }
        }

        return if (rCount == 0) "Dire" else "Radiant"
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("Dire", s.predictPartyVictory("RDD"))
    }

    @Test
    fun test2() {
        assertEquals("Radiant", s.predictPartyVictory("RD"))
    }

}
