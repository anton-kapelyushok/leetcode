package problems

object NumberOfStepsReduceANumberInBinaryRepresentationToOne {
    class Solution {
        fun numSteps(s: String): Int {
            var s = s
            var steps = 0
            val sb = StringBuilder()
            while (s != "1") {
                steps++
                if (s.endsWith("0")) {
                    s = s.dropLast(1)
                } else {
                    sb.clear()
                    var carry = true
                    for (c in s.reversed()) {
                        val n = if (carry && c == '1') {
                            '0'
                        } else if (carry && c == '0') {
                            carry = false
                            '1'
                        } else c
                        sb.append(n)
                    }
                    if (carry) sb.append('1')

                    s = sb.reversed().toString()
                }
            }
            return steps
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(numSteps("1101"))
            println(numSteps("10"))
        }
    }
}