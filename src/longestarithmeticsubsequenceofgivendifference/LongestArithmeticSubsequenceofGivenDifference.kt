package longestarithmeticsubsequenceofgivendifference;

class Solution {
    fun longestSubsequence(arr: IntArray, difference: Int): Int {
        val map = mutableMapOf<Int, Int>()
        for (el in arr) {
            val prev = map[el - difference] ?: 0
            map[el] = prev + 1
        }
        return map.values.max()
    }
}
