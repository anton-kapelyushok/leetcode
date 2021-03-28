package utils

data class ListNode(var `val`: Int) {
    var next: ListNode? = null

    override fun toString(): String {
        return "$`val` -> $next"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }
}

val ListNode.value: Int get() = this.`val`

fun ListNode?.toList(): List<Int> {
    if (this == null) return listOf()
    return listOf(value) + next.toList()
}

fun nodeListOf(vararg ints: Int): ListNode? {
    if (ints.isEmpty()) return null

    val head = ListNode(ints[0])
    var cur = head
    for (i in 1 until ints.size) {
        cur.next = ListNode(ints[i])
        cur = cur.next!!
    }

    return head
}
