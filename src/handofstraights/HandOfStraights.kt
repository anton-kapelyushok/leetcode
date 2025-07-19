package problems

object HandOfStraights {

    class Solution {
        fun isNStraightHand(hand: IntArray, groupSize: Int): Boolean {
            if (hand.size % groupSize != 0) return false
            if (groupSize == 1) return true
            hand.sort()
            // endValue -> startIdx
            val sequencesByEnd = mutableMapOf<Int, MutableSet<Int>>()
            for (i in hand.indices) {
                val sequences = sequencesByEnd[hand[i] - 1]
                if (sequences == null) {
                    sequencesByEnd[hand[i]] = (sequencesByEnd[hand[i]] ?: mutableSetOf()).apply { add(i) }
                } else {
                    val f = sequences.first()
                    sequences -= f
                    if (sequences.isEmpty()) {
                        sequencesByEnd.remove(hand[i] - 1)
                    }
                    if (hand[i] - hand[f] + 1 != groupSize) {
                        sequencesByEnd[hand[i]] = (sequencesByEnd[hand[i]] ?: mutableSetOf()).apply { add(f) }
                    }
                }
            }

            return sequencesByEnd.isEmpty()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(isNStraightHand(intArrayOf(1, 2, 3, 6, 2, 3, 4, 7, 8), 3)) // true
            println(isNStraightHand(intArrayOf(1, 2, 3, 6, 2, 3, 4, 7, 9), 3)) // false
            println(isNStraightHand(intArrayOf(1, 2, 3, 4, 5), 4)) // false
        }
    }
}