package problems

object MaximumIceCreamBars {
    class Solution {
        fun maxIceCream(costs: IntArray, coins: Int): Int {
            costs.sort()

            var coinsLeft = coins
            var res = 0
            for (c in costs) {
                if (c > coinsLeft) break
                res += 1
                coinsLeft -= c
            }

            return res
        }
    }
}