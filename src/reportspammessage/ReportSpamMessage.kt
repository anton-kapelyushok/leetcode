package reportspammessage

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun reportSpam(message: Array<String>, bannedWords: Array<String>): Boolean {
        val bannedSet = bannedWords.toSet()
        return message.count { it in bannedSet } >= 2
    }
}
class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
    }

}
