package sumgame

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // num.length == 10^5
    fun sumGame(num: String): Boolean {
        val leftNum = num.take(num.length / 2)
        val rightNum = num.drop(num.length / 2)

        var leftMoves = leftNum.count { it == '?' }
        var rightMoves = rightNum.count { it == '?' }

        var leftSum = leftNum.filter { it != '?' }.sumOf { it.digitToInt() }
        var rightSum = rightNum.filter { it != '?' }.sumOf { it.digitToInt() }


        // alice want to maximize, bob wants to minimize?
        // or may be it is a game maximizeLeft | minimizeLeft
        // bob can always mirror what alice does and viceversa
        // not always - if they have moves on the opposite half

        // Insight 1: if alice has the last move - she will always win

        // for bob to win on the last move:
        // 1) the other half must be not smaller
        // 2) the difference must be <= 9

        // can bob win when the sums are not equal from the start and the amount of gaps are different?
        // 55?? -> 550? -> bob loses
        // 54?? -> 549? -> 5490 -> alice loses
        //      -> 540? -> 5409 -> alice loses
        // yes
        // 53?? -> 539? -> bob loses
        // 56?? -> 530? -> bob loses

        // So bob can win if he has a last move and either:
        // sums are equal and ? are balanced
        // - if sums are unequal and ? are balanced - alice can always put 9 on the bigger side
        // sums differ by 9 and the side with less ? is bigger by 9 exactly?

        // can alice close the 9 gap?
        // 54?? ???? -> 54?? 9??? -> 549? 9??? -> 549? 99?? -> 5499 99??
        // no - bob can always mirror

        // actually, may be 9 is wrong?
        // 5400 ???? -> 5400 9??? -> bob loses
        // but
        // 5454 ???? -> 5454 9??? -> 5454 90??

        // it is actually unmatched


        // alice always wins if she has a last move
        if ((leftMoves + rightMoves) % 2 == 1) return true

        // if moves are balanced and sums are equal from the start bob just mirrors and wins
        if (leftMoves == rightMoves && leftSum == rightSum) return false


        if (rightMoves > leftMoves && leftSum == rightSum + 9 * (rightMoves - leftMoves) / 2) return false
        if (leftMoves > rightMoves && rightSum == leftSum + 9 * (leftMoves - rightMoves) / 2) return false


        return true
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.sumGame(""))
    }

}


object Ns1 {
    class Solution {
        data class Edge(val from: Int, val to: Int)

        fun remainingMethods(n: Int, k: Int, invocations: Array<IntArray>): List<Int> {
            val edges = invocations.map { (from, to) -> Edge(from, to) }
            val edgesByFrom = edges.groupBy { (from, to) -> from }.mapKeys { it.key }

            val visitedSuspicious = mutableSetOf<Int>()
            val suspicious = mutableSetOf<Int>()

            fun markSuspicious(v: Int) {
                if (v in visitedSuspicious) return
                visitedSuspicious.add(v)
                suspicious += v

                val edges = edgesByFrom[v] ?: listOf()
                edges.forEach { markSuspicious(it.to) }
            }
            markSuspicious(k)

            val visitedReachable = mutableSetOf<Int>()
            val reachable = mutableSetOf<Int>()

            fun markReachable(v: Int) {
                if (v in visitedReachable) return
                visitedReachable += v
                reachable += v
                val edges = edgesByFrom[v] ?: listOf()
                edges.forEach { markReachable(it.to) }
            }
            (0 until n).filter { it !in suspicious }.forEach { markReachable(it) }

            return (0 until n)
                .filterNot { it in suspicious && it !in reachable }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }
}