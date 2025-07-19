package problems

object LexicographicallySmallestEquivalentString {
    class Solution {
        class Dsu {
            val parents = IntArray(26) { it }

            fun parentOf(i: Int): Int {
                if (parents[i] == i) return i
                parents[i] = parentOf(parents[i])
                return parents[i]
            }

            fun union(l: Int, r: Int) {
                val lParent = parentOf(l)
                val rParent = parentOf(r)
                parents[lParent] = minOf(lParent, rParent)
                parents[rParent] = minOf(lParent, rParent)
            }
        }

        fun smallestEquivalentString(s1: String, s2: String, baseStr: String): String {
            val dsu = Dsu()
            s1.zip(s2).forEach { (l, r) -> dsu.union(l - 'a', r - 'a') }
            return String(baseStr.map { (dsu.parentOf(it - 'a') + 'a'.toInt()) }.map { it.toChar() }.toCharArray())
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

    }
}