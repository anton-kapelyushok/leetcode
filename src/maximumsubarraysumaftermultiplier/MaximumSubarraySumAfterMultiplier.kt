package maximumsubarraysumaftermultiplier

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    // choose one subarray
    // perform one of the operations:
    // * multiply every number by k
    // * divide every number by k:
    //     positive - use floor
    //     negative - use ceiling
    // @return the maximum possible sum of some other subarray
    fun maxSubarraySum(nums: IntArray /* 10^5 */, k: Int /* 10^5 */): Long {
        class Ns(nums: IntArray) {


            val dpn = Array<Long?>(nums.size) { null }
            fun n(i: Int): Long {
                if (i == nums.size) {
                    return 0
                }

                if (dpn[i] != null) {
                    return dpn[i]!!
                }

                val result = maxOf(
                    nums[i].toLong(),
                    nums[i] + n(i + 1),
                )

                dpn[i] = result

                return result
            }

            val dpd = Array<Long?>(nums.size) { null }
            fun d(i: Int): Long {
                if (i == nums.size) {
                    return 0
                }

                if (dpd[i] != null) {
                    return dpd[i]!!
                }

                val result = maxOf(
                    nums[i].toLong() / k,
                    nums[i] / k + d(i + 1),
                    n(i),
                )

                dpd[i] = result

                return result
            }

            val dpm = Array<Long?>(nums.size) { null }
            fun m(i: Int): Long {
                if (i == nums.size) {
                    return 0
                }

                if (dpm[i] != null) {
                    return dpm[i]!!
                }

                val result = maxOf(
                    nums[i].toLong() * k,
                    nums[i].toLong() * k + m(i + 1),
                    n(i),
                )

                dpm[i] = result

                return result
            }

            // normal then something
            val dpnn = Array<Long?>(nums.size) { null }
            fun nn(i: Int): Long {
                if (i == nums.size) {
                    return 0
                }


                if (dpnn[i] != null) {
                    return dpnn[i]!!
                }

                val result = maxOf(
                    nums[i].toLong(), // take this one
                    nums[i].toLong() + nn(i + 1),
                    d(i),
                    m(i),
                )

                dpnn[i] = result
                return result
            }
        }


        val ns = Ns(nums)
        val res = nums.indices.maxOf {
            val nn = ns.nn(it)
            nn
        }


        return res
    }
}


class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(14, s.maxSubarraySum(intArrayOf(1, -2, 3, 4, -5), 2))
    }

    @Test
    fun divide() {
        println(5 / 2) // 2 // floor
        println((-5) / 2) // -2 // ceiling
    }
}
