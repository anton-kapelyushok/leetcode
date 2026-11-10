package climbingstairsii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // 0..n steps
    // costs - 1 indexed
    // cost[i]
    // i -> i+1/i+2/i+3
    // costs[i -> j] = costs[j] + (j - i)^2
    // @return minimum cost
    fun climbStairs(n: Int, costs: IntArray): Int {

        val dp = IntArray(n + 1) { -1 }

        fun f(i: Int): Int {
            if (i == n) {
                return 0
            }

            if (dp[i] != -1) {
                return dp[i]
            }

            var result = Int.MAX_VALUE

            fun jump(k: Int) {
                if (i + k <= n) {
                    val j = f(i + k) + costs[i + k - 1] + k * k
//                    println("jump  : $i -> ${i + k}: ${costs[i + k - 1] + k * k}")
//                    println("result: $i -> ${i + k}: $j")
                    result = minOf(
                        result,
                        j,
                    )

                }
            }

            jump(1)
            jump(2)
            jump(3)

            dp[i] = result

            return result
        }

        val result = f(0)
        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(13, s.climbStairs(4, intArrayOf(1, 2, 3, 4)))
    }
}
