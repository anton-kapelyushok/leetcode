package problems

import java.util.*

object MinimumGeneticMutation {
    class Solution {
        fun minMutation(startGene: String, endGene: String, bank: Array<String>): Int {
            val chs = "ACGT"
            val e = mutableMapOf<String, MutableSet<String>>()

            val bankSet = bank.toSet() + startGene
            for (w in bankSet) {
                val chars = w.toCharArray()
                val next = mutableSetOf<String>()
                for (i in 0 until 8) {
                    val initial = chars[i]
                    for (m in chs) {
                        if (m == initial) continue
                        chars[i] = m
                        val g = String(chars)
                        if (g in bankSet) next += g
                    }
                    chars[i] = initial
                }
                e[w] = next
            }

            var q = LinkedList<String>()
            val visited = mutableSetOf<String>()
            var steps = 0
            q.offer(startGene)
            while (q.isNotEmpty()) {
                val nextQ = LinkedList<String>()
                while (q.isNotEmpty()) {
                    val top = q.pop()
                    if (top == endGene) return steps
                    if (top !in bankSet) continue
                    if (top in visited) continue
                    visited += top
                    for (next in e[top]!!) {
                        nextQ.offer(next)
                    }
                }
                steps++
                q = nextQ
            }
            return -1
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(minMutation("AACCGGTT", "AACCGGTA", arrayOf("AACCGGTA")))
            println(minMutation("AACCGGTT", "AAACGGTA", arrayOf("AACCGGTA", "AACCGCTA", "AAACGGTA")))
        }
    }
}