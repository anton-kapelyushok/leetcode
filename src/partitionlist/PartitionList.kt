package partitionlist

import utils.ListNode

class Solution {
    fun partition(head: ListNode?, x: Int): ListNode? {
        if (head == null) return head

        var cur: ListNode? = head

        val leftHead = ListNode(0)
        val rightHead = ListNode(0)
        var leftCur = leftHead
        var rightCur = rightHead

        while (cur != null) {
            val t = cur
            cur = cur.next
            if (t.`val` < x) {
                leftCur.next = t
                leftCur = t
                leftCur.next = null
            } else {
                rightCur.next = t
                rightCur = t
                rightCur.next = null
            }
        }

        leftCur.next = rightHead.next
        return leftHead.next
    }
}
