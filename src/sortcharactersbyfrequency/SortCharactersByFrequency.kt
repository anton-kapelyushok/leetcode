package problems

object SortCharactersByFrequency {
    class Solution {
        fun frequencySort(s: String): String {
            return s.toList()
                .groupBy { it }
                .mapValues { (_, vs) -> vs.joinToString("") }
                .entries
                .sortedBy { (_, v) -> -v.length }
                .joinToString("") { (_, v) -> v }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(frequencySort("tree"))
            println(frequencySort("cccaaa"))
            println(frequencySort("Aabb"))
        }
    }
}