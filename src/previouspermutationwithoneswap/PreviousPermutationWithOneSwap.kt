package previouspermutationwithoneswap

class Solution {
    fun prevPermOpt1(arr: IntArray): IntArray {
        var l = arr.size
        for (i in arr.size - 1 downTo 1) {
            if (arr[i - 1] > arr[i]) {
                l = i - 1
                break
            }
        }
        if (l == arr.size) return arr

        var r = l + 1
        var max = arr[r]
        var maxR = r++
        while (r < arr.size) {
            if (arr[r] > max && arr[r] < arr[l]) {
                max = arr[r]
                maxR = r
            }
            r++
        }

        arr[l] = arr[maxR].also { arr[maxR] = arr[l] }
        return arr
    }
}