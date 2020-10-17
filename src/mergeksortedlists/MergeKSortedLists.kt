package mergeksortedlists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.ListNode
import utils.nodeListOf
import utils.value

class Solution {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        val head = ListNode(0)
        var cur = head
        while (true) {
            var min = Int.MAX_VALUE
            var nextListIndex = - 1

            for (i in lists.indices) {
                if (lists[i] == null) continue
                if (lists[i]!!.`val` < min) {
                    min = lists[i]!!.`val`
                    nextListIndex = i
                }
            }

            if (nextListIndex == -1) break
            cur.next = ListNode(lists[nextListIndex]!!.`val`)
            cur = cur.next!!
            lists[nextListIndex] = lists[nextListIndex]!!.next
        }

        return head.next
    }
}

class SolutionTest {
    val s = Solution()

    @Test
    fun test1() {
        val lists = arrayOf(nodeListOf(1, 4, 5), nodeListOf(1, 3, 4), nodeListOf(2, 6))
        val expected = nodeListOf(1, 1, 2, 3, 4, 4, 5, 6)
        assertEquals(expected, s.mergeKLists(lists))
    }

    @Test
    fun test2() {
        val expected = nodeListOf()
        assertEquals(expected, s.mergeKLists(arrayOf()))
    }

    @Test
    fun test3() {
        val expected = nodeListOf()
        assertEquals(expected, s.mergeKLists(arrayOf(nodeListOf())))
    }
}
