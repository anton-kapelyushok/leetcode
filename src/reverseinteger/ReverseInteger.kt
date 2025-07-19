package problems

object ReverseInteger {

    class Solution {
        fun reverse(x: Int): Int {
            val xs = x.toString()
            if (x >= 0) {
                val newS = xs.reversed()
                if (newS.padStart(10, '0') > "2147483647") return 0
                return newS.toInt()
            } else {
                val newS = xs.takeLast(xs.length - 1).reversed()
                if (newS.padStart(10, '0') > "2147483648") return 0
                return "-$newS".toInt()
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(reverse(123))
            println(reverse(2111111113))
            println(reverse(2111111111))
            println(reverse(-123))
            println(reverse(-2111111113))
            println(reverse(-2111111111))
        }
    }
}