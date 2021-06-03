package maximumareaofapieceofcakeafterhorizontalandverticalcuts

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    val m = 1_000_000_000 + 7

    fun maxArea(h: Int, w: Int, horizontalCuts: IntArray, verticalCuts: IntArray): Int {
        Arrays.sort(horizontalCuts)
        Arrays.sort(verticalCuts)

        var maxH = 0
        var prevH = 0
        for (horCut in horizontalCuts) {
            val diff = horCut - prevH
            prevH = horCut
            if (diff > maxH) maxH = diff
        }
        if (h - prevH > maxH) maxH = h - prevH

        var maxV = 0
        var prevV = 0
        for (verCut in verticalCuts) {
            val diff = verCut - prevV
            prevV = verCut
            if (diff > maxV) maxV = diff
        }
        if (w - prevV > maxV) maxV = w - prevV

        return (((maxV % m).toLong() * (maxH % m).toLong()) % m).toInt()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(6, s.maxArea(5, 4, intArrayOf(3, 1), intArrayOf(1)))
    }

}
