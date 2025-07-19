package problems

object UglyNumber {

    class Solution {
        fun isUgly(n: Int): Boolean {
            var k = n

            while (k % 2 == 0) k /= 2
            while (k % 3 == 0) k /= 3
            while (k % 5 == 0) k /= 5

            return k == 1
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }
}