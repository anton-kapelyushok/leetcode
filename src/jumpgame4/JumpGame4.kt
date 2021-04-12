package jumpgame4

import java.util.*

class Solution {
    fun minJumps(arr: IntArray): Int {
        val scores = IntArray(arr.size) { Int.MAX_VALUE }

        val indicesByValue = arr.indices.groupBy { arr[it] }.toMutableMap()

        val q = LinkedList<N>()
        q.offer(N(0, 1))

        while (q.isNotEmpty()) {
            val (index, score) = q.poll()

            if (scores[index] <= score) continue
            scores[index] = score

            if (index == arr.size - 1) return score - 1

            if (index + 1 in arr.indices) q.offer(N(index + 1, score + 1))
            if (index - 1 in arr.indices) q.offer(N(index - 1, score + 1))

            indicesByValue[arr[index]]?.forEach { q.offer(N(it, score + 1))}
            indicesByValue.remove(arr[index])
        }

        error("")
    }

    data class N(val index: Int, val score: Int)
}