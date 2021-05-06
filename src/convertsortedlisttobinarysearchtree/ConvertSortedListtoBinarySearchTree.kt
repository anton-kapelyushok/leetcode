package convertsortedlisttobinarysearchtree

import utils.ListNode
import utils.TreeNode

class Solution {
    fun sortedListToBST(head: ListNode?): TreeNode? {
        if (head == null) return null
        var cur: ListNode? = head
        val arr = mutableListOf<Int>()

        while (cur != null) {
            arr.add(cur.`val`)
            cur = cur.next
        }

        arr.sort()

        return tree(arr, 0, arr.size - 1)
    }

    fun tree(arr: List<Int>, startIdx: Int, endIdx: Int): TreeNode? {
        if (startIdx > endIdx) return null

        val mid = (startIdx + endIdx) / 2

        val node = TreeNode(arr[mid])
        node.left = tree(arr, startIdx, mid - 1)
        node.right = tree(arr, mid + 1, endIdx)

        return node
    }
}


