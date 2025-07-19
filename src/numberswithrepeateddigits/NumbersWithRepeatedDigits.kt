package problems

object NumbersWithRepeatedDigits {
    class Solution {
        val cache = mutableMapOf<Triple<Int, Int, String>, R2>()

        fun numDupDigitsAtMostN(n: Int): Int {
            // 5678
            val ns = n.toString()
            var result = 0

            val k = ns[0] - '0'

            // 1..., 2..., 3..., 4...
            for (j in 1 until k) {
                result += f2(j, ns.length, "9".repeat(ns.length)).duplicates()
            }

            // x.., x., x
            for (i in 1 until ns.length) {
                for (j in 1..9) {
                    result += f2(j, ns.length - i, "9".repeat(ns.length - i)).duplicates()
                }
            }

            result += f2(k, ns.length, ns).duplicates()

            return result
        }

        // #numbers of length l starting with s not greater that n
        fun f(s: Int, l: Int, n: String): R {
            val (first, next, max) = d(n)
            if (s > first) error("s >= first (s = $s, l = $l, n = $n)")
            if (l == 1) {
                return R(
                    duplicated = 0,
                    single = mapOf(s to 1),
                    none = (0..9).filter { it != s }.associateWith { 1 },
                )
            }
            val second = n[1] - '0'
            var duplicated = 0
            val single = mutableMapOf<Int, Int>()
            val none = mutableMapOf<Int, Int>()
            for (d in 0..second) {
                val nr = f(d, l - 1, if (d == second) next else max)
                duplicated += nr.duplicated

                for (k in nr.single.keys) {
                    if (k != s) {
                        single[k] = (single[k] ?: 0) + (nr.single[k] ?: 0)
                    } else {
                        duplicated += (nr.single[k] ?: 0)
                    }
                }

                for (k in nr.none.keys) {
                    if (k != s) {
                        none[k] = (none[k] ?: 0) + (nr.none[k] ?: 0)
                    } else {
                        single[k] = (single[k] ?: 0) + (nr.none[k] ?: 0)
                    }
                }
            }

            return R(duplicated, single, none)
        }

        fun d(n: String): D {
            return D(
                n[0] - '0',
                n.drop(1),
                "9".repeat(n.length - 1)
            )
        }

        fun f2(s: Int, l: Int, n: String): R2 {
            val key = Triple(s, l, n)
            if (key in cache) {
//                println("$s $l $n: return from cache")
                return cache[key]!!
            }
//            println("$s $l $n: calculating")

            val (first, next, max) = d(n)
            if (s > first) error("s >= first (s = $s, l = $l, n = $n)")
            if (l == 1) {
                val result = R2(
                    counts = mapOf(
                        Mask(1 shl s, 0) to 1
                    ),
                )
                cache[key] = result
                return result
            }
            val second = n[1] - '0'
            val counts = mutableMapOf<Mask, Int>()

            for (d in 0..second) {
                val nr = f2(d, l - 1, if (d == second) next else max)
                for ((nm, c) in nr.counts) {
                    val single = nm.single
                    val dup = nm.dup
                    val shift = 1 shl s
                    val m = if (shift and dup != 0) {
                        Mask(single, dup)
                    } else if (single and shift != 0) {
                        Mask(single xor shift, dup xor shift)
                    } else {
                        Mask(single xor shift, dup)
                    }

                    counts[m] = (counts[m] ?: 0) + c
                }
            }
            val result = R2(counts)
            cache[key] = result
            return result
        }

        data class R2(
            // mask or mask (0, 1, 2)?
            // mask -> count
            val counts: Map<Mask, Int>
        ) {
            fun duplicates(): Int {
                return counts.entries.sumOf { (k, v) -> if (k.dup != 0) v else 0 }
            }

            override fun toString(): String {
                return "R2(${duplicates()}: $counts)"
            }
        }

        data class Mask(
            var single: Int,
            var dup: Int
        ) {
            override fun toString(): String {
                val sb = StringBuilder()

                for (i in 0..9) {
                    val shift = 1 shl i
                    if (single and shift != 0) sb.append(1)
                    else if (dup and shift != 0) sb.append(2)
                    else sb.append(0)
                }

                return sb.toString()
            }
        }

        data class R(
            // #numbers with duplicated digits
            val duplicated: Int,
            // #numbers containing single <key>
            val single: Map<Int, Int>,
            // #numbers not containing <key>
            val none: Map<Int, Int>,
        ) {
        }

        data class D(
            val first: Int,
            val next: String,
            val nextMax: String,
        )
    }

    @JvmStatic
    fun main(args: Array<String>) {
        with(Solution()) {
            fun hasRepeatedDigits(n: Int): Boolean {
                return n.toString().groupingBy { it }.eachCount().any { (_, v) -> v > 1 }
            }

            fun hasRepeatedDigits2(n: Int, l: Int): Boolean {
                return n.toString().padStart(l, '0').groupingBy { it }.eachCount().any { (_, v) -> v > 1 }
            }

            fun check(n: Int) {
                val expected = (0..n).count { hasRepeatedDigits(it) }
                val actual = numDupDigitsAtMostN(n)
                println("numDupDigitsAtMostN(n): expected $expected actual $actual")
            }

            fun checkF(s: Int, l: Int, n: String) {
                var e = 1
                val ns = n.toInt()
                for (i in 1 until l) {
                    e *= 10
                }
                val expected = (s * e until (s + 1) * e).count { it <= ns && hasRepeatedDigits(it) }
                val actual = f2(s, l, n)
                println("f(n = $s, l = $l, s = $n): expected = $expected, actual = $actual")
            }

//            check(50)
//            check(101)
//            check(101)
//            check(199)
//            check(200)
//            check(999)
//            check(580)
//            check(488)
//            check(1000)
//            check(1001)
            check(7488)
            check(748888)
//            check(Int.MAX_VALUE)
//            println(numDupDigitsAtMostN(7488888))
//            println(numDupDigitsAtMostN(74888888))
            println(Int.MAX_VALUE)
            println(numDupDigitsAtMostN(Int.MAX_VALUE))

            checkF(0, 2, "01")
//            checkF(0, 3, "001")
//            checkF(1, 4, "1001")

//            checkF(5, 2, "55") /
//            checkF(2, 3, "282") // 0
//
//            println("0..9: " + (0..9).count { hasRepeatedDigits(it) } + "/" + (0..9).count())
//            println("10..99: " + (10..99).count { hasRepeatedDigits(it) } + "/" + (10..99).count())
//            println("0..99: " + (0..99).count { hasRepeatedDigits(it) } + "/" + (0..99).count())
//            println("100..999: " + (100..999).count { hasRepeatedDigits(it) } + "/" + (100..999).count())
//            println("0..999: " + (0..999).count { hasRepeatedDigits(it) } + "/" + (0..999).count())
//            println("0..999: " + (0..999).count { hasRepeatedDigits2(it, 3) } + "/" + (0..999).count())
////            (0..999).filter { hasRepeatedDigits(it) }.joinToString().let { println(it) }
////            (0..999).filter { hasRepeatedDigits2(it, 3) }.joinToString().let { println(it) }
//            println("1000..9999: " + (1000..9999).count { hasRepeatedDigits(it) } + "/" + (1000..9999).count())
//
//
////            println(f(1, 3))
////            (100..199).count { hasRepeatedDigits(it) }.let { println(it) }
//
////            println(numDupDigitsAtMostN(10)) // 0
//            println(numDupDigitsAtMostN(99)) // 0
//
//            (0..89).count { hasRepeatedDigits(it) }.let { println(it) }
//
//            println(numDupDigitsAtMostN(222))
//            (0..999).count { hasRepeatedDigits(it) }.let { println(it) }
        }
    }
}