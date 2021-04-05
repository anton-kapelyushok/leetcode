package flattennestedlistiterator;

interface NestedInteger {
    fun isInteger(): Boolean
    fun getInteger(): Int?
    fun getList(): List<NestedInteger>?
}

class Solution {
    class NestedIterator(val nestedList: List<NestedInteger>) {
        var nestedIntegers = mutableListOf<List<NestedInteger>>()
        var indices = mutableListOf<Int>()

        var currentValue: Int? = null

        init {
            if (nestedList.isNotEmpty()) {
                nestedIntegers.add(nestedList)
                indices.add(-1)
                move()
            }
        }

        fun next(): Int {
            return currentValue!!.also { move() }
        }

        fun hasNext(): Boolean {
            return currentValue != null
        }

        private fun move() {
            currentValue = null

            while (nestedIntegers.isNotEmpty()) {
                indices[indices.size - 1] += 1

                if (indices.last() in nestedIntegers.last().indices) {
                    val nestedInteger = nestedIntegers.last()[indices.last()]
                    if (nestedInteger.isInteger()) {
                        currentValue = nestedInteger.getInteger()!!
                        return
                    } else {
                        indices.add(-1)
                        nestedIntegers.add(nestedInteger.getList()!!)
                    }
                } else {
                    indices.removeAt(indices.size - 1)
                    nestedIntegers.removeAt(nestedIntegers.size - 1)
                }
            }

        }
    }
}
