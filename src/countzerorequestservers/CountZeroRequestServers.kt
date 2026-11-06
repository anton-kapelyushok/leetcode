package countzerorequestservers

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    fun countServers(
        n: Int,  // 10^5 total number of servers
        logs: Array<IntArray>, // 10^5 [serverId, time][]
        x: Int,
        queries: IntArray, // 10^5 # of servers that did not receive requests during [queries[i] - x, queries[i]]
    ): IntArray {
        val result = IntArray(queries.size)

        logs.sortBy { it[1] }
        val qIndices = queries.indices.sortedBy { queries[it] }

        var serversServed = 0
        val counts = mutableMapOf<Int, Int>()

        var logL = 0
        var logR = 0

        for (qIdx in qIndices) {
            val q = queries[qIdx]
            // EXPAND
            while (logR in logs.indices && logs[logR][1] <= q) {
                val (serverId) = logs[logR]
                counts[serverId] = (counts[serverId] ?: 0) + 1
                if (counts[serverId] == 1) {
                    serversServed++
                }
                logR++
            }

            // SHRINK
            while (logL in logs.indices && logs[logL][1] < q - x) {
                val (serverId) = logs[logL]
                counts[serverId] = counts[serverId]!! - 1
                if (counts[serverId] == 0) {
                    serversServed--
                }
                logL++
            }

            // RECORD
            result[qIdx] = n - serversServed
        }

        return result
    }
}

class SolutionTest {

    private val s = Solution()
    

    @Test
    fun test2() {
        assertEquals(
            listOf(1, 2), s.countServers(
                n = 3,
                logs = arrayOf(
                    intArrayOf(1, 3),
                    intArrayOf(2, 6),
                    intArrayOf(1, 5),
                ),
                x = 5,
                queries = intArrayOf(10, 11)
            ).toList()
        )
    }

    @Test
    fun test3() {
        assertEquals(
            listOf(5, 6, 5, 5, 6, 5, 6, 5, 6), s.countServers(
                n = 6,
                logs = arrayOf(
                    intArrayOf(1, 21)
                ),
                x = 10,
                queries = intArrayOf(24, 35, 28, 26, 20, 25, 16, 31, 12)
            ).toList()
        )
    }
}
