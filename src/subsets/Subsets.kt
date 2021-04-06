package subsets;

import java.util.*

class Solution {
    fun subsets(nums: IntArray): List<List<Int>> {
        val counts = IntArray(21)
        nums.forEach { n -> counts[n + 10]++ }

        return emit(0, counts)
    }

    fun emit(start: Int, counts: IntArray): List<List<Int>> {
        if (start == counts.size) return listOf(listOf())

        val next = emit(start + 1, counts)
        return (0..Math.min(counts[start], 1)).flatMap { i ->
            val left = (0 until i).map { start - 10 }
            next.map { right -> left + right }
        }
    }
}

class ActualBtSolution {
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        backTrack(result, nums, 0, mutableListOf())

        return result
    }

    fun backTrack(result: MutableList<List<Int>>, nums: IntArray, start: Int, current: MutableList<Int>) {
        if (start == nums.size) {
            result.add(current.toList())
            return
        }
        // 0 of current
        backTrack(result, nums, start + 1, current)

        // 1 of current
        current.add(nums[start])
        backTrack(result, nums, start + 1, current)
        current.removeAt(current.size - 1)
    }
}

class BtNoRecursionSolution {
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        val starts = LinkedList<Int>()
        val states = LinkedList<Int>()
        val current = mutableListOf<Int>()
        starts.push(0)
        states.push(0)
        while (starts.isNotEmpty()) {
            val start = starts.peek()
            val state = states.peek()

            if (start == nums.size) {
                result.add(current.toList())
                starts.poll()
                states.poll()
                continue
            }

            if (state == 0) {
                states.poll()
                states.push(1)
                states.push(0)
                starts.push(start + 1)
            } else if (state == 1) {
                states.poll()
                states.push(2)
                states.push(0)
                starts.push(start + 1)
                current.add(nums[start])
            } else {
                states.poll()
                starts.poll()
                current.removeAt(current.size - 1)
            }
        }

        return result
    }
}