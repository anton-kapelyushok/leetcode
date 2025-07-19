package problems

object NthDigit {
    class Solution {
        fun findNthDigit(n: Int): Int {
            var length = 1
            var first = 1L // = Math.pow(10.toDouble(), (length - 1).toDouble()).toLong()
            var left = n.toLong() - 1
            while (true) {
                if (left < length * first * 9) break
                left -= length * first * 9
                first *= 10
                length += 1
            }

            // length = #digits in number
            // left = index of digit of $length sized numbers
            val number = left / length + first
            val digit = number.toString()[(left % length).toInt()] - '0'

            return digit
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("(1..9).count() = " + (1..9).count())
        println("(10..99).count() = " + (10..99).count())
        println("(100..999).count() = " + (100..999).count())
        val chars = (1..10000000).joinToString("")

        fun test(n: Int, expected: Int? = null) {
            val _expected = if (n - 1 in chars.indices) chars[n - 1].toString() else expected ?: "?"
            println("$n: expected: ${_expected}, actual: ${Solution().findNthDigit(n)}")
        }

        test(3)
        test(9)
        test(10)
        test(11)
        test(189)
        test(190)
        test(191)
        test(600)
        test(10000000)
        test(100000000, expected = 8)
        test(1000000000, expected = 1)
    }
}