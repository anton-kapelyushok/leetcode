package stringcompression

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun compress(chars: CharArray): Int {
        var wp = 0
        fun write(s: String) {
            val schars = s.toCharArray()
            schars.forEach { c ->
                chars[wp++] = c
            }
        }

        fun commit(c: Char, count: Int) {
            if (count == 0) return
            write(c.toString())
            if (count > 1) write(count.toString())
        }

        var current = 'a'
        var currentCount = 0
        for (c in chars) {
            if (c == current) currentCount++
            else {
                commit(current, currentCount)
                currentCount = 1
                current = c
            }
        }
        commit(current, currentCount)

        return wp
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            6, s.compress(
                listOf("a", "a", "b", "b", "c", "c", "c").map { it[0] }.toCharArray()
            )
        )
    }
}
