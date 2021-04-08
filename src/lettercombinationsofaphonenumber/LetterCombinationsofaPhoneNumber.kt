class Solution {

    val dMap = listOf("", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz")

    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return listOf()

        val result = mutableListOf<String>()

        backtrack(0, digits, StringBuilder(), result)

        return result
    }

    fun backtrack(start: Int, digits: String, acc: StringBuilder, result: MutableList<String>) {
        if (start == digits.length) {
            result.add(acc.toString())
            return
        }

        val ds = dMap[digits[start] - '0']

        for (i in ds.indices) {
            acc.append(ds[i])
            backtrack(start + 1, digits, acc, result)
            acc.deleteCharAt(acc.length - 1)
        }
    }
}
