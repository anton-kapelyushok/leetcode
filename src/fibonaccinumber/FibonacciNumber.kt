package fibonaccinumber

class Solution {
    fun fib(n: Int): Int {
        var n1 = 0
        var n2 = 1

        for (i in 1..n) {
            n2 = (n1 + n2).also { n1 = n2 }
        }

        return n1
    }
}
