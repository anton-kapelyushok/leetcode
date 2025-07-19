package problems

object DesignBrowserHistory {
    class BrowserHistory(homepage: String) {
        private class Node(
            val value: String,
            var prev: Node? = null,
            var next: Node? = null,
        )

        private var current: Node = Node(homepage)

        fun visit(url: String) {
            current.next = Node(url, prev = current)
            current = current.next!!
        }

        fun back(steps: Int): String {
            for (i in 1..steps) {
                if (current.prev == null) return current.value
                current = current.prev!!
            }
            return current.value
        }

        fun forward(steps: Int): String {
            for (i in 1..steps) {
                if (current.next == null) return current.value
                current = current.next!!
            }
            return current.value
        }
    }
}