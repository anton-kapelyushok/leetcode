package problems

object RectangleArea {

    class Solution {
        fun computeArea(ax1: Int, ay1: Int, ax2: Int, ay2: Int, bx1: Int, by1: Int, bx2: Int, by2: Int): Int {
            val lx1 = maxOf(ax1, bx1)
            val lx2 = minOf(ax2, bx2)
            val ly1 = maxOf(ay1, by1)
            val ly2 = minOf(ay2, by2)

            val la = if (lx1 >= lx2 || ly1 >= ly2) 0
            else (lx2 - lx1) * (ly2 - ly1)

            val aa = (ax2 - ax1) * (ay2 - ay1)
            val ba = (bx2 - bx1) * (by2 - by1)
            return aa + ba - la
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(computeArea(ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2))
            println(computeArea(ax1 = -2, ay1 = -2, ax2 = 2, ay2 = 2, bx1 = -2, by1 = -2, bx2 = 2, by2 = 2))
        }
    }
}