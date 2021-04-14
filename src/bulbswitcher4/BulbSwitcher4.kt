package bulbswitcher4

class Solution {
    fun minFlips(target: String): Int {
        var inversed = false
        var count = 0
        for (i in target.indices) {
            val shouldSwitch = (target[i] == '1') xor inversed
            if (shouldSwitch) {
                count++
                inversed = !inversed
            }
        }
        return count
    }
}
