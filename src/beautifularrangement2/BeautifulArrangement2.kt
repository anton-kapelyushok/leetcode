package beautifularrangement2

class Solution {
    fun constructArray(n: Int, k: Int): IntArray {
        val result = IntArray(n) { it + 1 }

        var right = k + 1
        var minus = true

        for (i in k - 1 downTo 0) {
            if (minus) result[i] = right - result[i]
            else result[i] = right + result[i]
            right = result[i]
            minus = !minus
        }

        return result
    }
}