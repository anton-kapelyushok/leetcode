package findxvalueofarrayii

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Solution {
    class SNode(
        val l: Int,
        val r: Int,

        val p: Int,
        // #{ subarrays starting at l producing x }
        val n: IntArray,

        val left: SNode? = null,
        val right: SNode? = null,
    ) {
        val m = (l + r) / 2

        override fun toString(): String {
            return "SNode(l=$l, r=$r, p=$p, n=${n.contentToString()})"
        }
    }

    fun merge(k: Int, leftP: Int, leftN: IntArray, rightP: Int, rightN: IntArray): Pair<Int, IntArray> {
        val p = (leftP * rightP) % k

        val n = IntArray(k)
        for (x in 0 until k) {
            // n = count of arrays on the range l..r starting at l with product x
            // all arrays from the left tree are still valid
            n[x] += leftN[x]

            // right calculations must be multiplied by product of the left array
            n[(x * leftP) % k] += rightN[x]
        }

        return p to n
    }

    fun query(node: SNode, k: Int, start: Int): Pair<Int, IntArray> {
        require(start <= node.r)
        if (start <= node.l) return node.p to node.n
        @Suppress("KotlinConstantConditions")
        if (node.l == node.r) return node.p to node.n

        requireNotNull(node.left)
        requireNotNull(node.right)
        if (start > node.m) {
            return query(node.right, k, start)
        }

        val (leftP, leftN) = query(node.left, k, start)
        val (rightP, rightN) = query(node.right, k, start)
        return merge(k, leftP, leftN, rightP, rightN)
    }

    fun constructTree(nums: IntArray, k: Int, l: Int, r: Int): SNode {
        if (l > r) {
            error("l cannot be > r")
        }
        if (l == r) {
            return SNode(
                l = l,
                r = r,
                p = nums[l] % k,
                n = IntArray(k).apply { this[nums[l] % k] = 1 }
            )
        }

        val m = (l + r) / 2
        val left = constructTree(nums, k, l, m)
        val right = constructTree(nums, k, m + 1, r)

        val (p, n) = merge(k, left.p, left.n, right.p, right.n)
        return SNode(l, r, p, n, left, right)
    }

    fun update(node: SNode, k: Int, idx: Int, value: Int): SNode {
        if (idx !in node.l..node.r) return node

        if (node.l == node.r) {
            return SNode(
                l = node.l,
                r = node.r,
                p = value % k,
                n = IntArray(k).apply { this[value % k] = 1 }
            )
        }
        requireNotNull(node.left)
        requireNotNull(node.right)

        val left = update(node.left, k, idx, value)
        val right = update(node.right, k, idx, value)

        val (p, n) = merge(k, left.p, left.n, right.p, right.n)
        return SNode(node.l, node.r, p, n, left, right)
    }

    // #{ arrays starting at maxOf(node.l, start) producing x }
    fun queryArrayCount(node: SNode, k: Int, start: Int, x: Int): Int {
        if (node.r < start) return 0
        val (_, n) = query(node, k, start)
        return n[x]
    }

    fun resultArray(nums: IntArray, k: Int, queries: Array<IntArray>): IntArray {
        var tree = constructTree(nums, k, 0, nums.lastIndex)
        val result = mutableListOf<Int>()
        for ((idx, value, start, x) in queries) {
            tree = update(tree, k, idx, value)
            result += queryArrayCount(tree, k, start, x)
        }
        return result.toIntArray()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test2() {
        // leetcode test case 1

        val tree1 = s.constructTree(
            intArrayOf(1, 2, 2, 4, 5),
            3,
            0, 4
        )
        assertEquals(2, s.queryArrayCount(tree1, 3, 0, 2))
        val tree2 = s.constructTree(
            intArrayOf(1, 2, 2, 3, 5),
            3,
            0, 4
        )
        assertEquals(2, s.queryArrayCount(tree2, 3, 3, 0))
        val tree3 = s.constructTree(
            intArrayOf(1, 2, 2, 3, 5),
            3,
            0, 4
        )
        assertEquals(2, s.queryArrayCount(tree3, 3, 0, 1))
    }

    @Test
    fun test2_1() {
        // leetcode test case 1
        val tree0 = s.constructTree(
            intArrayOf(1, 2, 3, 4, 5),
            3,
            0, 4
        )
        val tree1 = s.update(tree0, 3, 2, 2)
        assertEquals(2, s.queryArrayCount(tree1, 3, 0, 2))

        val tree2 = s.update(tree1, 3, 3, 3)
        assertEquals(2, s.queryArrayCount(tree2, 3, 3, 0))

        val tree3 = s.update(tree2, 3, 0, 1)
        assertEquals(2, s.queryArrayCount(tree3, 3, 0, 1))
    }

    @Test
    fun test3() {
        // leetcode testcase 2
        val tree1 = s.constructTree(
            intArrayOf(2, 2, 4, 8, 16, 32),
            4,
            0, 5
        )
        assertEquals(1, s.queryArrayCount(tree1, 4, 0, 2))

        val tree2 = s.constructTree(
            intArrayOf(2, 2, 4, 8, 16, 32),
            4,
            0, 5
        )
        assertEquals(0, s.queryArrayCount(tree1, 4, 0, 1))
    }

    @Test
    fun test4() {
        // leetcode 98
//        val tree0 = s.constructTree(intArrayOf(5, 7, 60), 2, 0, 2)
//        assertEquals(1, s.queryArrayCount(tree0, 2, 1, 1))

        val tree1 = s.constructTree(intArrayOf(53, 13, 9, 5, 7, 60), 2, 0, 5)
        assertEquals(1, s.queryArrayCount(tree1, 2, 4, 1))

        if (true) return
        var tree = s.constructTree(intArrayOf(1, 2, 9, 5, 7, 1), 2, 0, 5)
        tree = s.update(tree, 2, 5, 60)
        tree = s.update(tree, 2, 1, 13)
        tree = s.update(tree, 2, 0, 53)

        assertEquals(1, s.queryArrayCount(tree, 2, 4, 1))
    }
}
