package _24game

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    private val e = 0.00001

    fun judgePoint24(cards: IntArray): Boolean {
        fun bt(cards: List<Int>, startInclusive: Int, endExclusive: Int): List<Double> {
            if (endExclusive - startInclusive == 1) return listOf(cards[startInclusive].toDouble())

            val result = mutableListOf<Double>()

            for (i in startInclusive + 1 until endExclusive) {
                val leftPossibilities = bt(cards, startInclusive, i)
                val rightPossibilities = bt(cards, i, endExclusive)

                for (l in leftPossibilities) {
                    for (r in rightPossibilities) {
                        result.add(l + r)
                        result.add(l - r)
                        result.add(l * r)
                        if (Math.abs(r) >= e) result.add(l / r)
                    }
                }
            }

            return result
        }

        fun btOuter(from: MutableList<Int>, to: MutableList<Int>): Boolean {
            if (from.isEmpty()) {
                val r = bt(to, 0, to.size)
                return r.any { Math.abs(it - 24) <= e }
            }
            for (i in 0 until from.size) {
                val v = from[i]
                to.add(v)
                from.removeAt(i)

                if (btOuter(from, to)) return true

                from.add(i, v)
                to.removeAt(to.size - 1)
            }
            return false
        }

        return btOuter(cards.toMutableList(), mutableListOf())
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.judgePoint24(intArrayOf(4, 1, 8, 7)))
    }

}
