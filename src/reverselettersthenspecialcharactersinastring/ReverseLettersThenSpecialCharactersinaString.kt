package reverselettersthenspecialcharactersinastring

import org.junit.jupiter.api.Test

class Solution {
    fun reverseByType(s: String): String {
        val normal = s.filter { it in 'a'..'z' }
        val special = s.filter {it !in 'a'..'z' }

        val sb = StringBuilder()

        var normalP = normal.lastIndex
        var specialP = special.lastIndex

        for (c in s) {
            if (c in 'a'..'z') {
                sb.append(normal[normalP--])
            } else {
                sb.append(special[specialP--])
            }
        }

        return sb.toString()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
//        assertEquals(0, s.solve())
    }
}
