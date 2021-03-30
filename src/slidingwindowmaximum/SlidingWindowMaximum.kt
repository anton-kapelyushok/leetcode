package slidingwindowmaximum

class Solution {

    val nums = LinkedList<Int>()
    val maxs = LinkedList<Int>()


    fun poll() {
        var num = nums.pollFirst()

        if (!maxs.isEmpty() && maxs.peekFirst() == num) maxs.pollFirst()
    }

    fun offer(num: Int) {
        nums.offer(num)

        while (!maxs.isEmpty() && maxs.peekLast() < num) maxs.pollLast()

        maxs.offer(num)
    }

    fun currentMax(): Int = maxs.peekFirst()

    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        val result = IntArray(nums.size - k + 1)

        for (i in 0 until k) {
            offer(nums[i])
        }

        result[0] = currentMax()

        for (i in k until nums.size) {
            poll()
            offer(nums[i])

            result[i - k + 1] = currentMax()
        }

        return result
    }
}
