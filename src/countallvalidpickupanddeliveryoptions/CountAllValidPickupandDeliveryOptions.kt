package countallvalidpickupanddeliveryoptions;

class Solution {
    val mod = 1_000_000_000 + 7
    fun countOrders(n: Int): Int {
        if (n == 1) return 1
        val n1 = (n - 1) * 2 + 1
        val a = (n1.toLong()) * (n1 + 1) / 2 % mod
        return (countOrders(n - 1) * a % mod).toInt()
    }
}
