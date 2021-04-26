package furthestbuildingyoucanreach

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun furthestBuilding(heights: IntArray, bricks: Int, ladders: Int): Int {
        val q = PriorityQueue<Int> { l, r -> r.compareTo(l) }
        var bricksLeft = bricks
        var laddersLeft = ladders
        var maxReached = 0

        loop@ for (i in 1 until heights.size) {
            val bricksNeeded = heights[i] - heights[i - 1]

            when {
                bricksNeeded <= 0 -> Unit
                bricksNeeded <= bricksLeft -> {
                    bricksLeft -= bricksNeeded
                    q.offer(bricksNeeded)
                }
                laddersLeft == 0 -> {
                    break@loop
                }
                q.isEmpty() -> {
                    laddersLeft -= 1
                }
                else -> {
                    val top = q.peek()
                    if (top > bricksNeeded) {
                        q.poll()
                        q.offer(bricksNeeded)
                        bricksLeft = bricksLeft - bricksNeeded + top
                    }
                    laddersLeft -= 1
                }
            }

            maxReached++
        }

        return maxReached
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(4, s.furthestBuilding(intArrayOf(4, 2, 7, 6, 9, 14, 12), 5, 1))
    }

    @Test
    fun test2() {
        assertEquals(7, s.furthestBuilding(intArrayOf(4,12,2,7,3,18,20,3,19), 10, 2))
    }

}
