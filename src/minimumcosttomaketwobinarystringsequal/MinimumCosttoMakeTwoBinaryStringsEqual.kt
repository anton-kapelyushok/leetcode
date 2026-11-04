package minimumcosttomaketwobinarystringsequal

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // s, t - binary strings (101010110110), both length n <= 10^5
    // flipCost - flip s[i] OR t[i]
    // swapCost - swap s[i] with s[j] OR swap t[i] with t[j] - reorder?
    // crossCost - swap s[i] with t[i]
    // @return minimum cost to make strings equal
    //
    // swap(s,0,1)
    // 01000 | 10000 | 10100 | 10010 | 10011
    // 10111 | 10111 | 10011 | 10011 | 10011


    // cross == 2 x flip
    // 10 | 00
    // 01 | 11
    fun minimumCost(
        s: String, t: String, flipCost: Int, swapCost: Int, crossCost: Int
    ): Long {

        // 101010110110
        // 000001000110

        // 1) do not count the equal digits - they are already in place
        // 2) resulting with two numbers - misplaced 0s, misplaced 1s


        var misplacedZeros = 0
        var misplacedOnes = 0
        s.zip(t).forEach { (l, r) ->
            if (l == r) return@forEach
            if (l == '0') misplacedZeros++
            else misplacedOnes++
        }

        if (misplacedZeros > misplacedOnes) {
            misplacedZeros = misplacedOnes.also { misplacedOnes = misplacedZeros }
        }


        // 1. Strategy
        //   cross until 0s == 1s,
        //   swap
        //   flip the last one
        // 2. Strategy
        //   flip everything

        // 10111
        // 01000

        fun crossThanSwapThanFlip(misplacedZeros: Int, misplacedOnes: Int): Long {
            /// 00011 1111 -> 00000 1111
            var cost = 0L
            var misplacedZeros = misplacedZeros
            var misplacedOnes = misplacedOnes

            val target = (misplacedZeros + misplacedOnes) / 2
            val crossed = target - misplacedZeros
            cost += 1L * crossed * crossCost
            misplacedZeros += crossed
            misplacedOnes -= crossed

            // because I am lost in off by ones
            val swapped = minOf(misplacedZeros, misplacedOnes)
            cost += 1L * swapped * swapCost

            misplacedZeros -= swapped
            misplacedOnes -= swapped

            require(misplacedZeros + misplacedOnes <= 1)

            cost += 1L * (misplacedZeros + misplacedOnes) * flipCost

            return cost
        }

        fun swapThanFlip(misplacedZeros: Int, misplacedOnes: Int): Long {
            var cost = 0L
            var misplacedZeros = misplacedZeros
            var misplacedOnes = misplacedOnes

            val swapped = minOf(misplacedZeros, misplacedOnes)
            cost += 1L * swapped * swapCost

            misplacedZeros -= swapped
            misplacedOnes -= swapped

            cost += 1L *(misplacedZeros + misplacedOnes) * flipCost

            return cost
        }

        fun flip(misplacedZeros: Int, misplacedOnes: Int): Long {
            return 1L * (misplacedZeros + misplacedOnes) * flipCost
        }

        return minOf(
            crossThanSwapThanFlip(misplacedZeros, misplacedOnes),
            swapThanFlip(misplacedZeros, misplacedOnes),
            flip(misplacedZeros, misplacedOnes),
        )
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            16, s.minimumCost(
                s = "01000", t = "10111", flipCost = 10, swapCost = 2, crossCost = 2
            )
        )
    }

    @Test
    fun test2() {
        assertEquals(
            6, s.minimumCost(
                s = "001", t = "110", flipCost = 2, swapCost = 100, crossCost = 100
            )
        )
    }

    @Test
    fun test3() {
        assertEquals(
            0, s.minimumCost(
                s = "1010", t = "1010", flipCost = 5, swapCost = 5, crossCost = 5
            )
        )
    }

    @Test
    fun test4() {
        assertEquals(
            214, s.minimumCost(
                s = "100101",
                t = "111110",
                flipCost = 66, swapCost = 82, crossCost = 237798794
            )
        )
    }
}
