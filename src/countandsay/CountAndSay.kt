package countandsay

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun countAndSay(n: Int): String {
        if (n == 1) return "1"

        val s = countAndSay(n - 1)

        val result = StringBuilder()
        var c = s[0]
        var k = 1
        for (i in 1 until s.length) {
            if (s[i] == c) {
                k++
            } else {
                result.append(k.toString())
                result.append(c)
                c = s[i]
                k = 1
            }
        }

        result.append(k.toString())
        result.append(c)

        return result.toString()
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals("1", s.countAndSay(1))
    }

    @Test
    fun test2() {
        assertEquals("1211", s.countAndSay(4))
    }

    @Test
    fun test3() {
        (1..30).forEach { println(s.countAndSay(it)) }
    }
}
