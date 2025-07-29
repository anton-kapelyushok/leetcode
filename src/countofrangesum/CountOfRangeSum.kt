package problems

import kotlin.random.Random
import kotlin.system.measureTimeMillis


object CountOfRangeSum {
    class Solution {

        class Node(
            var count: Int,
            var lower: Long,
            var mid: Long,
            var upper: Long,
            var left: Node? = null, // lower until mid
            var right: Node? = null // mid .. upper
        )

        fun newNode(min: Long, max: Long): Node = Node(
            count = 0,
            lower = min,
            mid = if (max - min == 1L) max else (min + max) / 2,
            upper = max
        )

        fun addNode(node: Node, idx: Int, value: Long) {
            node.count++
            if (node.lower == node.upper) {
                return
            }

            val goLeft = value in node.lower until node.mid

            val nextNode = when {
                node.upper - node.lower == 1L && value == node.lower -> {
                    node.left = node.left ?: newNode(node.lower, node.lower)
                    node.left!!
                }

                node.upper - node.lower == 1L && value == node.upper -> {
                    node.right = node.right ?: newNode(node.upper, node.upper)
                    node.right!!
                }

                goLeft -> {
                    node.left = node.left ?: newNode(node.lower, node.mid - 1)
                    node.left!!
                }

                !goLeft -> {
                    node.right = node.right ?: newNode(node.mid, node.upper)
                    node.right!!
                }

                else -> error("")
            }

            addNode(nextNode, idx, value)
        }

        fun removeNode(node: Node, idx: Int, value: Long) {
            node.count--
            if (node.lower == node.upper) return

            val goLeft = value in node.lower until node.mid
            val nextNode = if (goLeft) node.left!! else node.right!!
            removeNode(nextNode, idx, value)
        }

        fun countEqualOrLessThan(node: Node, value: Long): Int {
            if (node.upper <= value) return node.count
            if (node.lower > value) return 0
            var res = 0
            node.left?.let { res += countEqualOrLessThan(it, value) }
            node.right?.let { res += countEqualOrLessThan(it, value) }
            return res
        }

        fun countMoreThan(node: Node, value: Long): Int {
            val total = node.count
            return total - countEqualOrLessThan(node, value)
        }

        fun countEqualTo(node: Node, value: Long): Int {
            if (node.lower == node.upper) return node.count
            return if (value in node.lower until node.mid) {
                node.left?.let { countEqualTo(it, value) } ?: 0
            } else {
                node.right?.let { countEqualTo(it, value) } ?: 0
            }
        }

        fun countNotEqualTo(node: Node, value: Long): Int {
            val total = node.count
            return total - countEqualTo(node, value)
        }

        fun countEqualOrMoreThan(node: Node, value: Long): Int {
            if (value >= node.upper) return 0
            if (node.lower > value) return node.count
            var res = 0
            node.left?.let { res += countEqualOrMoreThan(it, value) }
            node.right?.let { res += countEqualOrMoreThan(it, value) }
            return res
        }

        fun countRangeSum(nums: IntArray, lower: Int, upper: Int): Int {
            val rpsum = LongArray(nums.size)
            rpsum[rpsum.lastIndex] = nums.last().toLong()
            for (i in rpsum.lastIndex - 1 downTo 0) {
                rpsum[i] = rpsum[i + 1] + nums[i]
            }

            fun rpsum(idx: Int): Long {
                if (idx == rpsum.size) return 0
                if (idx in rpsum.indices) return rpsum[idx]
                error("oob $idx")
            }

            val tree = newNode(minOf(rpsum.min(), 0), maxOf(0, rpsum.max()))
            for (idx in 0..rpsum.size) {
                addNode(tree, idx, rpsum(idx))
            }

            var res = 0
            for (idx in nums.indices) {
                val wholeSum = rpsum[idx]
                removeNode(tree, idx, wholeSum)

                // sum(idx, j) = psum[idx] - psum[j] >= lower
                // psum[j] <= psum[idx] - lower
                val countMoreThanLower = countEqualOrLessThan(tree, wholeSum - lower)

                // sum(idx, j) = psum[idx] - psum[j] > higher
                // psum[j] < psum[idx] - higher
                // psum[j] <= psum[idx] - higher - 1
                val countMoreThanHigher = countEqualOrLessThan(tree, wholeSum - upper - 1)

                res += countMoreThanLower - countMoreThanHigher
            }

            return res
        }
    }


    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(countRangeSum(intArrayOf(-3, 1, 2, -2), -3, -1))
            println(countRangeSum(intArrayOf(2, -2), -3, -1))
            println(countRangeSum(intArrayOf(-2, 5, -20), -5, 5))
            println(countRangeSum(intArrayOf(-2, 5, -1), -2, 2))
            println(countRangeSum(intArrayOf(1), 0, 0))
            println(measureTimeMillis {
                println(countRangeSum((0..1_00_000).map { Random.nextInt() }.toIntArray(), -5000, 5000))
            })
            println(measureTimeMillis {
                println(countRangeSum((0..1_00_000).map { -833068696 }.toIntArray(), -5000, 5000))
            })
        }
    }
}