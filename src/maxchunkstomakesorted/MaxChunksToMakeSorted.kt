package problems

object MaxChunksToMakeSorted {
    class Solution {
        fun maxChunksToSorted(arr: IntArray): Int {
            var currentMax = -1

            var chunksCount = 0
            for (i in arr.indices) {
                currentMax = maxOf(currentMax, arr[i])
                if (currentMax == i) {
                    currentMax = -1
                    chunksCount++ // add previous chunk
                }
            }
            return chunksCount
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(maxChunksToSorted(intArrayOf(4, 3, 2, 1, 0)))
            println(maxChunksToSorted(intArrayOf(1, 0, 2, 3, 4)))
        }
    }
}