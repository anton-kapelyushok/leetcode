// https://leetcode.com/problems/add-two-numbers/

package addtwonumbers

import org.junit.jupiter.api.Test
import utils.ListNode
import utils.value

class Solution {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var l = l1
        var r = l2

        val result = ListNode(0)
        var cur = result
        var shift = 0
        while (l != null || r != null || shift != 0) {
            val toAdd = (l?.value ?: 0) + (r?.value ?: 0) + shift
            shift = toAdd / 10
            cur.next = ListNode(toAdd % 10)
            cur = cur.next!!
            l = l?.next
            r = r?.next
        }
        return result.next
    }

    fun toInt(node: ListNode?): Int {
        var result = 0
        var cur = node
        var dec = 1
        while (cur != null) {
            result += cur.value * dec
            dec *= 10
            cur = cur.next
        }
        return result
    }

    fun toList(number: Int): ListNode? {
        var cur = number / 10
        val result: ListNode? = ListNode(number % 10)
        var resultCur = result
        while (cur != 0) {
            resultCur!!.next = ListNode(cur % 10)
            cur /= 10
            resultCur = resultCur.next
        }
        return result
    }
}

class SolutionTest {
    val s = Solution()

    @Test
    fun test1() {
        assert(s.toInt(s.toList(123)) == 123)
    }

    @Test
    fun test2() {
        assert(s.toList(342).toString() == "2 -> 4 -> 3 -> null")
    }

    @Test
    fun test3() {
        assert(s.toInt(s.addTwoNumbers(s.toList(342), s.toList(465))) == 807)
    }

    @Test
    fun test4() {
        println(s.addTwoNumbers(s.toList(9), s.toList(999999991)))
    }
}
