package problems

import java.util.LinkedList

object ArrayNesting {
    class Solution1 {
        fun arrayNesting(nums: IntArray): Int {
            val cache = IntArray(nums.size) { -1 }
            fun f(i: Int, start: Int, l: Int): Int {
                if (cache[i] != -1) return cache[i]
                if (i == start) return l
                val s = if (start == -1) i else start
                cache[i] = f(nums[i], s, l + 1)
                return cache[i]
            }

            return nums.indices.maxOfOrNull {
                f(it, -1, 0)
            } ?: 0
        }
    }

    class Solution2 {
        fun arrayNesting(nums: IntArray): Int {
            val cache = IntArray(nums.size) { -1 }


            val inQ = mutableSetOf<Int>()
            val q = LinkedList<Int>()

            for (i in nums.indices) {
                var cur = i
                while (cur !in inQ) {
                    q.addFirst(cur)
                    inQ += cur
                    cur = nums[cur]
                }
            }

            var mr = 0
            for (i in q) {
                if (cache[i] != -1) continue
                var r = 1
                var cur = nums[i]
                while (cur != i) {
                    if (cache[cur] != -1) {
                        r += cache[cur]
                        break
                    }
                    r += 1
                    cache[cur] = r
                    cur = nums[cur]
                }
                cache[i] = r
                mr = maxOf(r, mr)
            }
            return mr
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution2()) {
            println(arrayNesting(intArrayOf())) // 0
            println(arrayNesting(intArrayOf(0))) // 1
            println(arrayNesting(intArrayOf(5, 4, 0, 3, 1, 6, 2))) // 4
            println(arrayNesting(intArrayOf(0, 1, 2))) // 1
            println(arrayNesting(((1..10).toList() + 0).toIntArray())) // 11
            println(arrayNesting(((1..6411).toList() + 0).toIntArray())) // 6412
            println(arrayNesting(((1..99999).toList() + 0).toIntArray())) // 100000
        }
    }
}

