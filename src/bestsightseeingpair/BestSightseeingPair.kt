package bestsightseeingpair;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun maxScoreSightseeingPair(values: IntArray): Int {
        var l = values[0]
        var max = 0
        for (i in 1 until values.size) {
            val r = values[i]
            l--
            if (l + r > max) max = l + r
            if (r > l) l = r
        }
        return max
    }
}
