package problems

/*

expr = first (OPERAND tailPart)*

first = unary_minus_expr
      | number
      | parenthesis_expr

tailPart = number
         | parenthesis_expr

number = \d+
parenthesis_expr = LPAREN expr RPAREN
unary_minus_expr = MINUS second

 */

object BasicCalculator {
    class Solution {
        fun calculate(s: String): Int {
            val ss = s.replace(" ", "")
            val node = parse(ss)
            return evaluate(node)
        }

        fun evaluate(n: Node): Int {
            return when (n) {
                is ExprNode -> {
                    var res = 0
                    res += evaluate(n.first)
                    for ((op, c) in n.tail) {
                        when (op) {
                            '+' -> res += evaluate(c)
                            '-' -> res -= evaluate(c)
                            else -> error("Unknown operator $op")
                        }
                    }
                    res
                }

                is NumberNode -> n.v
                is UnaryMinusNode -> -evaluate(n.child)
            }
        }

        fun parse(s: String): Node {
            val res = expr(s, 0, s.length - 1)
            if (res.r != s.length - 1) error("failed to parse, parsed ${res.r}/${s.length - 1} symbols")

            return res.n
        }

        fun expr(s: String, l: Int, r: Int): ParseResult {
            val first = first(s, l, r)
            if (first.r == r) return first
            val tail = mutableListOf<Pair<Char, Node>>()
            fun operator(i: Int): Char? {
                val c = s[i]
                if (s[i] != '-' && s[i] != '+') return null
                return c
            }

            var i = first.r + 1
            while (i <= r && operator(i) != null) {
                val op = operator(i)!!
                val res = tailPart(s, i + 1, r)
                tail += op to res.n
                i = res.r + 1
            }

            return ParseResult(i - 1, ExprNode(first.n, tail))
        }

        fun first(s: String, l: Int, r: Int): ParseResult {
            val c = s[l]
            return when (c) {
                '-' -> unaryMinus(s, l, r)
                in '0'..'9' -> number(s, l, r)
                '(' -> parenthesisExpr(s, l, r)
                else -> error("Unexpected symbol `$c` at position $l")
            }
        }

        fun tailPart(s: String, l: Int, r: Int): ParseResult {
            val c = s[l]
            return when (c) {
                in '0'..'9' -> number(s, l, r)
                '(' -> parenthesisExpr(s, l, r)
//                '-' -> unaryMinus(s, l, r)
                else -> error("Unexpected symbol `$c` at position $l")
            }
        }

        fun number(s: String, l: Int, r: Int): ParseResult {
            var i = l
            var acc = 0
            while (i <= r && s[i] in '0'..'9') {
                acc *= 10
                acc += s[i].digitToInt()
                i++
            }
            return ParseResult(
                r = i - 1,
                n = NumberNode(acc)
            )
        }

        fun unaryMinus(s: String, l: Int, r: Int): ParseResult {
            if (s[l] != '-') error("Unexpected symbol `${s[l]}` at position $l, expected `(`")
            val res = when (s[l + 1]) {
                '(' -> parenthesisExpr(s, l + 1, r)
                in '0'..'9' -> number(s, l + 1, r)
                else -> error("Unexpected symbol `${s[l + 1]}` at position ${l + 1}, expected `(` or number")
            }
            return ParseResult(res.r, UnaryMinusNode(res.n))
        }

        fun parenthesisExpr(s: String, l: Int, r: Int): ParseResult {
            if (s[l] != '(') error("Unexpected symbol `${s[l]}` at position $l, expected `(`")
            val exprResult = expr(s, l + 1, r - 1)
            if (s[exprResult.r + 1] != ')') error("Unexpected symbol `${s[l]}` at position $l, expected `)`")
            return ParseResult(exprResult.r + 1, exprResult.n)
        }

        data class ParseResult(
            val r: Int,
            val n: Node,
        )

        sealed interface Node {}

        data class ExprNode(val first: Node, val tail: List<Pair<Char, Node>>) : Node
        data class UnaryMinusNode(val child: Node) : Node
        data class NumberNode(val v: Int) : Node
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            println(calculate("1 + 1"))
            println(calculate("1+1"))
//            println(calculate("1--1"))
            println(calculate("(1+(4+5+2)-3)+(6+8)"))
            println(calculate("-((1+(4+5+2)-3)+(6+8))"))

        }
    }
}