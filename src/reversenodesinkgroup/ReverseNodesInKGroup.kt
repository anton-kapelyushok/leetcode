package reversenodesinkgroup

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.ListNode
import utils.nodeListOf

class Solution {
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        var size = size(head)

        var newHead: ListNode? = null
        var curHead: ListNode? = head
        var prevHead: ListNode? = null

        while (k <= size && size != 0) {
            var prev: ListNode? = null
            var cur = curHead
            for (i in (1..k)) {
                val tmp = cur!!.next
                cur.next = prev

                prev = cur
                cur = tmp
            }

            if (newHead == null) newHead = prev
            if (prevHead != null) prevHead.next = prev
            prevHead = curHead
            curHead = cur
            size -= k
        }

        if (k > size || size == 0) {
            prevHead!!.next = curHead
        }

        return newHead ?: head
    }

    fun size(head: ListNode?): Int {
        var cur = head
        var result = 0
        while (cur != null) {
            cur = cur.next
            result++
        }
        return result
    }
}

class SolutionRecursive {
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        return reverseKGroup(head, k, size(head))
    }

    fun reverseKGroup(head: ListNode?, k: Int, size: Int): ListNode? {
        if (k > size || size == 0) return head

        var prev: ListNode? = null
        var cur = head

        for (i in (1..k)) {
            val tmp = cur!!.next
            cur.next = prev

            prev = cur
            cur = tmp
        }

        head!!.next = reverseKGroup(cur, k, size - k)

        return prev
    }

    fun size(head: ListNode?): Int {
        var cur = head
        var result = 0
        while (cur != null) {
            cur = cur.next
            result++
        }
        return result
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        Assertions.assertEquals(nodeListOf(2, 1, 4, 3, 5), s.reverseKGroup(nodeListOf(1, 2, 3, 4, 5), 2))
    }

    @Test
    fun test2() {
        Assertions.assertEquals(nodeListOf(3, 2, 1, 4, 5), s.reverseKGroup(nodeListOf(1, 2, 3, 4, 5), 3))
    }
}
