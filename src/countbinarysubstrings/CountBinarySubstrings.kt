package countbinarysubstrings

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun countBinarySubstrings(s: String): Int {
        var zeros = 0
        var ones = 0

        var currentIsZero = s[0] == '0'
        var result = 0

        for (c in s) {
            if (currentIsZero) {
                if (c == '0') {
                    zeros++
                    if (zeros <= ones) result++
                } else {
                    ones = 1
                    if (ones <= zeros) result++
                    currentIsZero = false
                }
            } else {
                if (c == '1') {
                    ones++
                    if (ones <= zeros) result++
                } else {
                    zeros = 1
                    if (zeros <= ones) result++
                    currentIsZero = true
                }
            }

        }

        return result
    }
}
