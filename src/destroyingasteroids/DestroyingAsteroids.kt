package destroyingasteroids

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun asteroidsDestroyed(mass: Int, asteroids: IntArray): Boolean {
        var mass = mass.toLong()
        asteroids.sort()
        for (a in asteroids) {
            if (a >= mass) return false
            mass += a
        }
        return true
    }
}

