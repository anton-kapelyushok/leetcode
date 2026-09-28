package bulbswitcher

import org.junit.jupiter.api.Test

class Solution {

    fun bulbSwitch(n: Int): Int {
        return kotlin.math.sqrt(n.toDouble()).toInt()
    }

    fun simulate(n: Int) {
        val arr = IntArray(n)

        println(arr.toList().joinToString(""))

        for (i in 1..n) {
            for (j in arr.indices) {
                if ((j + 1) % i == 0) {
                    if (arr[j] == 0) arr[j] = 1
                    else arr[j] = 0
                }
            }
            print("$i: ")
            println(arr.take(i).toList().joinToString(""))
            print("$i: ")
            println(arr.take(i).withIndex().filter { (i, v) -> v == 1 }.map { (i) -> i + 1 }.joinToString(" "))
            print("$i: ")
            println(arr.take(i).count { it > 0})
            print("$i: ")
            println(kotlin.math.sqrt(i.toDouble()).toInt())
            println()
        }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        s.simulate(500)
//        assertEquals(0, s.solve())
    }
}
