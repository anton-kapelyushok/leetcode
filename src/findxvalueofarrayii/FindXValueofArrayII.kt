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

        val p = (left.p * right.p) % k
        val n = IntArray(k)
        for (x in 0 until k) {
            // n = count of arrays on the range l..r starting at l with product x
            // all arrays from the left tree are still valid
            n[x] += left.n[x]

            // right calculations must be multiplied by product of the left array
            n[(x * left.p) % k] += right.n[x]
        }

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
        val m = node.m

        val left = update(node.left, k, idx, value)
        val right = update(node.right, k, idx, value)

        val p = (left.p * right.p) % k
        val n = IntArray(k)
        for (x in 0 until k) {
            // n = count of arrays on the range l..r starting at l with product x
            // all arrays from the left tree are still valid
            n[x] += left.n[x]

            // right calculations must be multiplied by product of the left array
            n[(x * left.p) % k] += right.n[x]
        }

        return SNode(node.l, node.r, p, n, left, right)
    }

    // #{ arrays starting at maxOf(node.l, start) producing x }
    fun queryArrayCount(node: SNode, k: Int, start: Int, x: Int): Int {
        // suppose we are at [abcde], left is [abc], right is [de]

        // if right is to the right of us, we don't have any arrays making x
        if (node.r < start) return 0

        // if start is to the left of us, the answer to this query is in this tree:
        if (start <= node.l) return node.n[x]

        // node.l < start < node.r
        requireNotNull(node.left)
        requireNotNull(node.right)

        // if start is in the right tree, the answer is in the right tree
        if (start > node.m) return queryArrayCount(node.right, k, start, x)

        // start is in the left tree
        var result = 0

        // take all arrays from the left
        result += queryArrayCount(node.left, k, start, x)

        // find product maxOf(left, start)..right !TODO
        val p = product(node.left, k, start)
        for (xr in 0 until k) {
            if ((xr * p) % k != x) continue
            result += queryArrayCount(node.right, k, start, xr)
        }

        return result
    }

    fun product(node: SNode, k: Int, start: Int): Int {
        require(start <= node.r)
        if (start <= node.l) return node.p
        @Suppress("KotlinConstantConditions")
        if (node.l == node.r) return node.p

        requireNotNull(node.left)
        requireNotNull(node.right)

        val m = node.m
        if (start > node.m) return product(node.right, k, start)
        return (product(node.left, k, start) * node.right.p) % k
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
