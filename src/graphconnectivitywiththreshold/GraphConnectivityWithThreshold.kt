package graphconnectivitywiththreshold

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun areConnected(n: Int, threshold: Int, queries: Array<IntArray>): List<Boolean> {
        val divisors = Array(n + 1) { mutableListOf<Int>() }
        for (i in 1..n) {
            for (j in i..n step i) {
                divisors[j].add(i)
            }
        }

        val reverseDivisors = Array(n + 1) { mutableListOf<Int>() }
        divisors.forEachIndexed { i, divs ->
            if (i == 0) return@forEachIndexed

            divs.forEach { d -> reverseDivisors[d].add(i) }
        }

        var lastSegment = 1
        val segments = IntArray(n + 1)
        val q = LinkedList<QEntry>()

        for (d in threshold + 1..n) {
            q.offer(QEntry(d, 0))
        }

        // nlog(n) until here

        while (q.isNotEmpty()) {
            Thread.sleep(1)
            val (d, s) = q.pop()

            if (s != 0 && segments[d] != 0 && s != segments[d]) {
                error("unexpected")
            }
            if (segments[d] != 0) continue
            val _s = if (s == 0) lastSegment++ else s
            segments[d] = _s

            for (num in reverseDivisors[d]) {
                for (div in divisors[num].filter { it > threshold }) {
                    q.push(QEntry(div, _s))
                }
                divisors[num].clear()
            }

            reverseDivisors[d].clear()
        }

        return queries.map { (l, r) -> segments[l] != 0 && (segments[l] == segments[r]) }
    }

    data class QEntry(
            val d: Int,
            val s: Int
    )
}

