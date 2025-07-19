package problems

object CombinationSum2 {
    class Solution {
        fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
            val result = mutableSetOf<List<Int>>()
            candidates.sort()

            fun f(i: Int, accSum: Int, acc: MutableList<Int>) {
                if (accSum == target) {
                    result += acc.toList()
                }
                if (accSum > target) {
                    return
                }

                if (i == candidates.size) {
                    return
                }


                var prev = -1
                for (n in i + 1 until candidates.size) {
                    if (prev == candidates[n]) continue
                    prev = candidates[n]
                    acc += candidates[n]
                    f(n, accSum + candidates[n], acc)
                    acc.removeLast()
                }
            }

            f(-1, 0, mutableListOf())

            return result.distinct()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(combinationSum2(intArrayOf(10, 1, 2, 7, 6, 1, 5), 8))
            println(
                combinationSum2(
                    intArrayOf(
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1,
                        1
                    ), 30
                )
            )
        }

    }
}