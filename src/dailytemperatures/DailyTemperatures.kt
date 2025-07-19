package problems

import java.util.*

object DailyTemperatures {
    class Solution {
        fun dailyTemperatures(temperatures: IntArray): IntArray {
            val map = TreeMap<Int, Int>()
            map[temperatures[temperatures.size - 1]] = temperatures.size - 1

            val result = IntArray(temperatures.size)

            for (i in temperatures.size - 2 downTo 0) {
                val higher = map.higherEntry(temperatures[i])
                if (higher == null) result[i] = 0
                else result[i] = higher.value - i
                while (map.isNotEmpty() && map.firstKey() < temperatures[i]) {
                    map.remove(map.firstKey())
                }
                map[temperatures[i]] = i
            }

            return result
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(dailyTemperatures(intArrayOf(73, 74, 75, 71, 69, 72, 76, 73)).toList())
            println(dailyTemperatures(intArrayOf(30, 40, 50, 60)).toList())
        }
    }
}