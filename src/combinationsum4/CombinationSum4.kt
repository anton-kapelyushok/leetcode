package combinationsum4

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun combinationSum4(nums: IntArray, target: Int): Int {

        val dp = Array(1001) { IntArray(201) }

        fun f(n: Int, k: Int): Int {
            if (k == 1) return 1
            if (n == 1) return k
            if (dp[n][k] != 0) return dp[n][k]
            dp[n][k] = (0..n).map { i -> f(n - i, k - 1) }.sum()
            return dp[n][k]
        }

        val dp2 = Array(nums.size + 1) { Array(1000) { null as Boolean? } }

        var result = 0
        fun bt(nums: IntArray, start: Int, target: Int, path: MutableList<Int>): Boolean {

            if (dp2[start][target] == false) return false

            if (target == 0) {
                var r = 1
                var c = 0
                for (p in path) {
                    if (p == 0) continue
                    r *= f(p, c + 1)
                    c += p
                }
                result += r
                dp2[start][target] = true
                return true
            }
            if (target < 0) {
                dp2[start][target] = false
                return false
            }

            if (start == nums.size) {
                dp2[start][target] = false
                return false
            }

            var i = 0
            var rr = false
            while (target - i * nums[start] >= 0) {
                path.add(i)
                val r = bt(nums, start + 1, target - i * nums[start], path)
                if (r) rr = true
                path.removeAt(path.size - 1)
                i++
            }

            dp2[start][target] = rr
            return rr
        }


        bt(nums, 0, target, LinkedList())

        return result
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(7, s.combinationSum4(intArrayOf(1, 2, 3), 4))
    }

    @Test
    fun test2() {
        assertEquals(9, s.combinationSum4(intArrayOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25), 10))
    }

    @Test
    fun test3() {
        assertEquals(1, s.combinationSum4(intArrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360, 370, 380, 390, 400, 410, 420, 430, 440, 450, 460, 470, 480, 490, 500, 510, 520, 530, 540, 550, 560, 570, 580, 590, 600, 610, 620, 630, 640, 650, 660, 670, 680, 690, 700, 710, 720, 730, 740, 750, 760, 770, 780, 790, 800, 810, 820, 830, 840, 850, 860, 870, 880, 890, 900, 910, 920, 930, 940, 950, 960, 970, 980, 990, 111), 999))
    }

}
