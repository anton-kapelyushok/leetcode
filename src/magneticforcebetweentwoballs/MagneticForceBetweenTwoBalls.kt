package problems

object MagneticForceBetweenTwoBalls {
    class Solution { // o(n log(n))
        fun maxDistance(position: IntArray, m: Int): Int {
            position.sort()
            val maxPossible = (position.last() - position.first()) / (m - 1)
            var l = 0
            var r = maxPossible + 1


            while (l < r) {
                val d = (l + r) / 2
                if (!checkCondition(position, m, d)) {
                    r = d
                } else {
                    l = d + 1
                }
            }

            return l - 1
        }

        fun checkCondition(position: IntArray, m: Int, d: Int): Boolean {
            var mm = 1 // count of placed
            var lastI = 0
            for (i in 1 until position.size) {
                if (position[i] - position[lastI] >= d) {
                    lastI = i
                    mm++
                }
            }
            return mm >= m
        }
    }

    class Solution2 { // o(n^2)
        fun maxDistance(position: IntArray, m: Int): Int {
            position.sort()

            data class Key(val i: Int, val m: Int)

            val dp = mutableMapOf<Key, Int>()

            fun f(i: Int, m: Int): Int {
                val key = Key(i, m)
                if (m == 0) {
                    return Int.MAX_VALUE
                }
                val bucketsLeft = position.size - i
                if (bucketsLeft < m) { // nowhere to place
                    return 0
                }
                if (m == 2) {
                    return position.last() - position[i]
                }
                if (key in dp) {
                    return dp.getValue(key)
                }
                if (bucketsLeft == m) {
                    val result = minOf(
                        position[i + 1] - position[i],
                        f(i + 1, m - 1)
                    )
                    dp[key] = result
                    return result
                }
                var max = 0
                for (j in i + 1 until position.size) {
                    val next = minOf(
                        position[j] - position[i],
                        f(j, m - 1),
                    )
                    if (next > max) {
                        max = next
                    } else {
                        break
                    }
                }

                dp[key] = max
                return max
            }

            return f(0, m)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
//            println(checkCondition(intArrayOf(1, 2, 3, 4, 7), 2, 3))
//            println(checkCondition(intArrayOf(1, 2, 3, 4, 7), 2, 4))
//            println(checkCondition(intArrayOf(5, 4, 3, 2, 1, 1000000000).sortedArray(), 2, 499))
//            println(checkCondition(intArrayOf(22, 57, 74, 79).sortedArray(), 4, 6)) // 5

            println(maxDistance(intArrayOf(1, 2, 3, 4, 7), 3)) // 3
            println(maxDistance(intArrayOf(5, 4, 3, 2, 1, 1000000000), 2)) // 999999999
            println(maxDistance(intArrayOf(79, 74, 57, 22), 4)) // 5
//            println(maxDistance(intArrayOf())
        }
    }
}