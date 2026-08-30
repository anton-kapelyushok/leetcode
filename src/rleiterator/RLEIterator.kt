package rleiterator

import org.junit.jupiter.api.Test
import java.util.*

class RLEIterator(encoding: IntArray) {
    data class Node(val times: Int, val element: Int)

    val nodes: LinkedList<Node> = LinkedList()

    init {
        require(encoding.size % 2 == 0)
        encoding.toList().chunked(2).forEach { (times, element) -> nodes += Node(times, element) }
    }

    fun next(n: Int): Int {
        var n = n
        while (nodes.isNotEmpty()) {
            val (times, element) = nodes.pollFirst()
            if (n > times) {
                n -= times
            } else {
                val newTimes = times - n
                nodes.addFirst(Node(newTimes, element))
                return element
            }
        }
        return -1

    }

}


class SolutionTest {


    @Test
    fun test1() {
    }

}
