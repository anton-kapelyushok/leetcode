package serializeanddeserializebinarytree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Codec() {
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        if (root == null) {
            return ""
        }
        return "${root.`val`}(${serialize(root.left)})(${serialize(root.right)})"
    }

    // Decodes your encoded data to tree.
    // value,(),()
    fun deserialize(data: String): TreeNode? {
        class Ns(val s: String) {
            var i = 0

            fun readTree(): TreeNode? {
                if (i !in s.indices || s[i] == ')') return null
                val v = readVal()
                require(s[i] == '(')
                i++
                val left = readTree()
                require(s[i] == ')')
                i++
                require(s[i] == '(')
                i++
                val right = readTree()
                require(s[i] == ')')
                i++
                return TreeNode(v).apply {
                    this.left = left
                    this.right = right
                }
            }

            fun readVal(): Int {
                val sb = StringBuilder()
                while (s[i] != '(') {
                    sb.append(s[i])
                    i++
                }
                return sb.toString().toInt()
            }
        }
        return Ns(data).readTree()
    }

}

class SolutionTest {

    private val s = Codec()

    @Test
    fun test1() {
        assertEquals(
            "1(2()())(3(4()())(5()()))", s.serialize(
                TreeNode(1).apply {
                    left = TreeNode(2)
                    right = TreeNode(3).apply {
                        left = TreeNode(4)
                        right = TreeNode(5)
                    }
                })
        )
        s.deserialize("1(2()())(3(4()())(5()()))")
    }
}
