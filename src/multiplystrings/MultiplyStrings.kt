package problems

object MultiplyStrings {
    class Solution {
        fun multiply(num1: String, num2: String): String {
            return (BigInt(num1) * BigInt(num2)).v
        }

        data class BigInt(val v: String)

        operator fun BigInt.times(r: BigInt): BigInt {
            val l = this
            if (l.v.length < r.v.length) {
                return r * l
            }
            var result = BigInt("0")
            var shift = ""
            for (i in r.v.indices.reversed()) {
                val s = l * r.v[i]
                val ss = BigInt(s.v + shift)
                result += ss
                shift += "0"
            }
            return result
        }

        operator fun BigInt.times(r: Char): BigInt {
            val l = this
            val new = StringBuilder()
            val rc = r - '0'
            var carry = 0
            for (i in l.v.indices) {
                val lc = if (i in l.v.indices) l.v[l.v.length - i - 1] - '0' else 0
                val nc = lc * rc + carry
                carry = if (nc >= 10) nc / 10
                else 0
                new.append(nc % 10)
            }
            if (carry > 0) new.append(carry)
            return BigInt(stripLeadingZeros(new.reversed().toString()))
        }

        operator fun BigInt.plus(r: BigInt): BigInt {
            val l = this
            if (l.v.length < r.v.length) {
                return r + l
            }
            val new = StringBuilder()
            var carry = 0
            for (i in l.v.indices) {
                val lc = if (i in l.v.indices) l.v[l.v.length - i - 1] - '0' else 0
                val rc = if (i in r.v.indices) r.v[r.v.length - i - 1] - '0' else 0
                val nc = lc + rc + carry
                carry = if (nc >= 10) 1
                else 0
                new.append(nc % 10)
            }
            if (carry > 0) new.append('1')
            return BigInt(stripLeadingZeros(new.reversed().toString()))
        }

        private fun stripLeadingZeros(s: String): String {
            val actualNew = StringBuilder()
            var started = false
            for (c in s) {
                if (c == '0' && !started) {
                    continue
                }
                actualNew.append(c)
                started = true
            }
            if (!started) actualNew.append('0')
            return actualNew.toString()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
//            println(multiply("24", "2"))
//            println(multiply("24", "0"))
//            println(multiply("9", "1"))
            println(multiply("24", "109"))
            println(multiply("123456789", "987654321"))
//            println(multiply("0", "0"))
            println(Solution.BigInt("0") + Solution.BigInt("0"))
            println(Solution.BigInt("55") * '0')
        }
    }
}