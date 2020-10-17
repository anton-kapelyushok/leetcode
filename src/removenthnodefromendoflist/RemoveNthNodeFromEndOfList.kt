package removenthnodefromendoflist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.ListNode
import utils.nodeListOf

class Solution {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var size = 0
        run {
            var cur = head
            while (cur != null) {
                size++
                cur = cur!!.next
            }
        }

        val indexToRemove = size - n
        var prev = head
        var cur = prev?.next

        if (indexToRemove == 0) {
            return head?.next
        }

        var i = 1
        while (cur != null) {
            if (i == indexToRemove) {
                prev!!.next = cur!!.next
                break
            }

            prev = cur
            cur = cur!!.next
            i++
        }

        return head
    }
}

class SolutionTest {
    val s = Solution()

    @Test
    fun test1() {
        assertEquals(nodeListOf(1, 2, 3, 5), s.removeNthFromEnd(nodeListOf(1, 2, 3, 4, 5), 2))
    }

    @Test
    fun test2() {
        assertEquals(nodeListOf(), s.removeNthFromEnd(nodeListOf(1), 1))
    }

    @Test
    fun test3() {
        assertEquals(nodeListOf(1), s.removeNthFromEnd(nodeListOf(1, 2), 1))
    }
}
