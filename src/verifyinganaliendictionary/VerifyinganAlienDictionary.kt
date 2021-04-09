package verifyinganaliendictionary;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun isAlienSorted(words: Array<String>, order: String): Boolean {
        if (words.size <= 1) return true
        val orderOf = IntArray(order.length)

        order.indices.forEach { orderOf[order[it] - 'a'] = it }
        loop@for (i in 0..words.size-2) {
            val left = words[i]
            val right = words[i+1]

            var j = 0

            while (j in left.indices && j in right.indices) {
                if (orderOf[left[j] - 'a'] > orderOf[right[j] - 'a']) return false
                if (orderOf[left[j] - 'a'] < orderOf[right[j] - 'a']) continue@loop
                j++
            }
            return left.length <= right.length
        }
        return true
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.isAlienSorted(arrayOf("app","apple"), "abcdefghijklmnopqrstuvwxyz"))
    }

}
