package problems

import java.util.*

object OnlineStockSpan {

    class StockSpanner() {

        data class DayAndPrice(val day: Int, val price: Int)

        var day = 0
        val stack = LinkedList<DayAndPrice>()

        fun next(price: Int): Int {
            day++

            while (stack.peek() != null && stack.peek().price <= price) {
                stack.pop()
            }
            val prevDay = stack.peek()?.day ?: 0
            stack.push(DayAndPrice(day, price))

            return day - prevDay

        }

    }


    @JvmStatic
    fun main(args: Array<String>) {
        run {
            val sp = StockSpanner()
            println(sp.next(100))
            println(sp.next(80))
            println(sp.next(60))
            println(sp.next(70))
            println(sp.next(60))
            println(sp.next(75))
            println(sp.next(85))
        }
    }
}