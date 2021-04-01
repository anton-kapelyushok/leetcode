package palindromelinkedlist;

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import utils.ListNode
import utils.nodeListOf

class Solution {
    fun isPalindrome(head: ListNode?): Boolean {
        val size = size(head)
        if (size <= 1) return true

        var tailHead = head!!
        var tailOffset = size / 2 + size % 2
        while (tailOffset > 0) {
            tailOffset--
            tailHead = tailHead.next!!
        }

        val reversedTailHead = reverse(tailHead)

        var countDown = size / 2
        var cur = head
        var tailCur: ListNode? = reversedTailHead
        while (countDown > 0) {
            if (cur!!.`val` != tailCur!!.`val`) return false
            tailCur = tailCur!!.next
            cur = cur!!.next
            countDown--
        }
        return true
    }

    fun size(head: ListNode?): Int {
        var r = 0
        var cur = head
        while (cur != null) {
            r++
            cur = cur.next
        }
        return r
    }

    fun reverse(head: ListNode): ListNode {
        var prev: ListNode? = null
        var cur: ListNode? = head

        while (cur != null) {
            val next = cur.next
            cur.next = prev
            prev = cur
            cur = next
        }

        return prev!!
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(true, s.isPalindrome(nodeListOf(1, 2, 2, 1)))
    }

    @Test
    fun test2() {
        assertEquals(false, s.isPalindrome(nodeListOf(1, 2)))
    }

}
