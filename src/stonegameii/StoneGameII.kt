package problems

object StoneGameII {

    class Solution {
        fun stoneGameII(piles: IntArray): Int {
            data class Stones(val alice: Int, val bob: Int)
            data class DpKey(val m: Int, val i: Int, val isAlice: Boolean)

            val dp = mutableMapOf<DpKey, Stones>()

            fun f(m: Int, i: Int, isAlice: Boolean): Stones { // called o(n^2) times
                if (i == piles.size) return Stones(0, 0)
                val dpKey = DpKey(m, i, isAlice)
                if (dpKey in dp) return dp[dpKey]!!
                var max = -1
                var res = Stones(0, 0)
                var stones = 0
                for (x in 1..2 * m) { // o(n)
                    if (i + x - 1 >= piles.size) break
                    stones += piles[i + x - 1]
                    val nextM = maxOf(m, x)
                    val next = f(nextM, i + x, isAlice = !isAlice)
                    val nextStonesMe = (if (isAlice) next.alice else next.bob) + stones
                    val nextStonesEnemy = if (isAlice) next.bob else next.alice
                    if (nextStonesMe > max) {
                        max = nextStonesMe
                        res = if (isAlice)
                            Stones(alice = nextStonesMe, bob = nextStonesEnemy)
                        else
                            Stones(alice = nextStonesEnemy, bob = nextStonesMe)
                    }
                }

                dp[dpKey] = res

                return res
            }

            return f(1, 0, isAlice = true).alice
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(stoneGameII(intArrayOf(2, 7, 9, 4, 4)))
            println(stoneGameII(intArrayOf(1, 2, 3, 4, 5, 100)))
        }
    }
}