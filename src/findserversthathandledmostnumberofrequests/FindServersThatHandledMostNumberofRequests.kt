package findserversthathandledmostnumberofrequests

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

/*
    k servers, 0 until k, one request at the time
    all servers busy -> request dropped
    i request is assigned to (i % k) server, if busy, assigned to the next in ring (i + 1) % k
    @return server(s) with the most handled requests
 */

class Solution {
    fun TreeSet<Int>.chooseServer(k: Int, i: Int): Int? {
        val set = this
        return set.ceiling(i % k) ?: set.ceiling(0)
    }

    fun busiestServers(
        k: Int, // 10^5
        arrival: IntArray,
        load: IntArray
    ): List<Int> {
        if (k == 0) {
            return listOf()
        }

        val serverCounts = IntArray(k) { 0 }

        val freeServers = TreeSet<Int>()
        (0 until k).forEach { freeServers += it }

        data class ServerFreeEvent(val t: Int, val server: Int)

        val serverFrees = PriorityQueue<ServerFreeEvent>(compareBy { it.t })

        for (i in arrival.indices) {
            val arr = arrival[i]
            val l = load[i]

            println("i: $i, t: $arr")

            // drain free servers
            while (serverFrees.isNotEmpty() && serverFrees.peek().t <= arr) {
                val (_, s) = serverFrees.poll()
                freeServers += s
                println("server $s freed")
            }

            println("freeServers: ${freeServers.toList().sorted()}")

            // assign the job
            val s = freeServers.chooseServer(k, i)
            if (s == null) {
                println("all servers busy")
                continue
            }
            println("choosen: $s")

            serverCounts[s]++
            freeServers -= s
            serverFrees += ServerFreeEvent(arr + l, s)
            println("server $s busy until ${arr + l}")
            println("counts ${serverCounts.toList()}")
            println()
        }

        val maxHandled = serverCounts.max()
        return serverCounts.indices.filter { serverCounts[it] == maxHandled }
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            listOf(1), s.busiestServers(
                k = 3,
                arrival = intArrayOf(1, 2, 3, 4, 5),
                load = intArrayOf(5, 2, 3, 3, 3),
            )
        )
    }


    @Test
    fun test2() {
        assertEquals(
            listOf(1), s.busiestServers(
                k = 3,
                arrival = intArrayOf(1, 2, 3, 4, 8, 9, 10),
                load = intArrayOf(5, 2, 10, 3, 1, 2, 2),
            )
        )
    }
}