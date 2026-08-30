package slidingpuzzle

import org.junit.jupiter.api.Test
import java.util.*

class Solution {
    fun String.toBoardString(): String {
        return "${take(3)}\n${drop(3)}"
    }

    fun String.slideLeft(): String {
        val chars = toCharArray()
        if (chars[0] == '0') {
            chars[0] = chars[1]
            chars[1] = '0'
        } else if (chars[1] == '0') {
            chars[1] = chars[2]
            chars[2] = '0'
        } else if (chars[3] == '0') {
            chars[3] = chars[4]
            chars[4] = '0'
        } else if (chars[4] == '0') {
            chars[4] = chars[5]
            chars[5] = '0'
        }
        return String(chars)
    }

    fun String.slideRight(): String {
        val chars = toCharArray()
        if (chars[2] == '0') {
            chars[2] = chars[1]
            chars[1] = '0'
        } else if (chars[1] == '0') {
            chars[1] = chars[0]
            chars[0] = '0'
        } else if (chars[5] == '0') {
            chars[5] = chars[4]
            chars[4] = '0'
        } else if (chars[4] == '0') {
            chars[4] = chars[3]
            chars[3] = '0'
        }
        return String(chars)
    }

    fun String.slideUp(): String {
        val chars = toCharArray()
        if (chars[0] == '0') {
            chars[0] = chars[3]
            chars[3] = '0'
        } else if (chars[1] == '0') {
            chars[1] = chars[4]
            chars[4] = '0'
        } else if (chars[2] == '0') {
            chars[2] = chars[5]
            chars[5] = '0'
        }
        return String(chars)
    }

    fun String.slideDown(): String {
        val chars = toCharArray()
        if (chars[3] == '0') {
            chars[3] = chars[0]
            chars[0] = '0'
        } else if (chars[4] == '0') {
            chars[4] = chars[1]
            chars[1] = '0'
        } else if (chars[5] == '0') {
            chars[5] = chars[2]
            chars[2] = '0'
        }
        return String(chars)
    }


    fun slidingPuzzle(board: Array<IntArray>): Int {
        val visited = mutableSetOf<String>()
        val b = board.joinToString("") { it.joinToString("") }
        var q = LinkedList<String>()
        q.add(b)
        var steps = 0
        while (q.isNotEmpty()) {
            val nextQ = LinkedList<String>()
            for (bb in q) {
                if (bb in visited) continue
                visited += bb
                if (bb == "123450") return steps
                nextQ += bb.slideLeft()
                nextQ += bb.slideRight()
                nextQ += bb.slideUp()
                nextQ += bb.slideDown()
            }
            q = nextQ
            steps++
        }

        return -1
    }
}

class Solution2 {
    val data = mutableMapOf(
        "012345" to 15,
        "012453" to 3,
        "012534" to 15,
        "013254" to 15,
        "013425" to 3,
        "013542" to 17,
        "014235" to 17,
        "014352" to 15,
        "014523" to 13,
        "015243" to 17,
        "015324" to 13,
        "015432" to 7,
        "021354" to 19,
        "021435" to 15,
        "021543" to 13,
        "023145" to 3,
        "023451" to 11,
        "023514" to 11,
        "024153" to 9,
        "024315" to 15,
        "024531" to 15,
        "025134" to 9,
        "025341" to 15,
        "025413" to 17,
        "031245" to 15,
        "031452" to 11,
        "031524" to 19,
        "032154" to 13,
        "032415" to 17,
        "032541" to 19,
        "034125" to 13,
        "034251" to 15,
        "034512" to 13,
        "035142" to 7,
        "035214" to 9,
        "035421" to 11,
        "041253" to 9,
        "041325" to 13,
        "041532" to 9,
        "042135" to 17,
        "042351" to 15,
        "042513" to 7,
        "043152" to 17,
        "043215" to 7,
        "043521" to 11,
        "045123" to 19,
        "045231" to 11,
        "045312" to 11,
        "051234" to 17,
        "051342" to 13,
        "051423" to 11,
        "052143" to 5,
        "052314" to 13,
        "052431" to 15,
        "053124" to 11,
        "053241" to 15,
        "053412" to 17,
        "054132" to 11,
        "054213" to 11,
        "054321" to 15,
        "102345" to 14,
        "102453" to 2,
        "102534" to 14,
        "103254" to 14,
        "103425" to 2,
        "103542" to 16,
        "104235" to 16,
        "104352" to 14,
        "104523" to 12,
        "105243" to 16,
        "105324" to 12,
        "105432" to 6,
        "120345" to 13,
        "120453" to 1,
        "120534" to 13,
        "123045" to 2,
        "123405" to 1,
        "123450" to 0,
        "124053" to 10,
        "124503" to 11,
        "124530" to 12,
        "125034" to 10,
        "125304" to 11,
        "125340" to 12,
        "130254" to 15,
        "130425" to 3,
        "130542" to 17,
        "132054" to 14,
        "132504" to 15,
        "132540" to 16,
        "134025" to 14,
        "134205" to 15,
        "134250" to 16,
        "135042" to 6,
        "135402" to 5,
        "135420" to 4,
        "140235" to 17,
        "140352" to 15,
        "140523" to 13,
        "142035" to 16,
        "142305" to 15,
        "142350" to 16,
        "143052" to 16,
        "143502" to 15,
        "143520" to 14,
        "145023" to 18,
        "145203" to 17,
        "145230" to 18,
        "150243" to 15,
        "150324" to 13,
        "150432" to 5,
        "152043" to 4,
        "152403" to 3,
        "152430" to 4,
        "153024" to 12,
        "153204" to 13,
        "153240" to 14,
        "154032" to 12,
        "154302" to 13,
        "154320" to 14,
        "201354" to 18,
        "201435" to 16,
        "201543" to 12,
        "203145" to 4,
        "203451" to 12,
        "203514" to 12,
        "204153" to 8,
        "204315" to 16,
        "204531" to 14,
        "205134" to 8,
        "205341" to 14,
        "205413" to 16,
        "210354" to 19,
        "210435" to 17,
        "210543" to 13,
        "213054" to 14,
        "213504" to 13,
        "213540" to 14,
        "214035" to 18,
        "214305" to 17,
        "214350" to 18,
        "215043" to 18,
        "215403" to 17,
        "215430" to 18,
        "230145" to 5,
        "230451" to 13,
        "230514" to 13,
        "231045" to 16,
        "231405" to 15,
        "231450" to 14,
        "234051" to 16,
        "234501" to 15,
        "234510" to 14,
        "235014" to 8,
        "235104" to 7,
        "235140" to 6,
        "240153" to 7,
        "240315" to 15,
        "240531" to 13,
        "241053" to 10,
        "241503" to 11,
        "241530" to 12,
        "243015" to 6,
        "243105" to 5,
        "243150" to 6,
        "245031" to 12,
        "245301" to 13,
        "245310" to 14,
        "250134" to 9,
        "250341" to 15,
        "250413" to 15,
        "251034" to 18,
        "251304" to 17,
        "251340" to 16,
        "253041" to 14,
        "253401" to 13,
        "253410" to 14,
        "254013" to 10,
        "254103" to 9,
        "254130" to 10,
        "301245" to 14,
        "301452" to 12,
        "301524" to 18,
        "302154" to 12,
        "302415" to 16,
        "302541" to 18,
        "304125" to 12,
        "304251" to 14,
        "304512" to 14,
        "305142" to 8,
        "305214" to 10,
        "305421" to 12,
        "310245" to 13,
        "310452" to 13,
        "310524" to 17,
        "312045" to 16,
        "312405" to 15,
        "312450" to 14,
        "314052" to 16,
        "314502" to 15,
        "314520" to 16,
        "315024" to 12,
        "315204" to 11,
        "315240" to 12,
        "320154" to 13,
        "320415" to 15,
        "320541" to 19,
        "321054" to 20,
        "321504" to 19,
        "321540" to 20,
        "324015" to 14,
        "324105" to 13,
        "324150" to 14,
        "325041" to 14,
        "325401" to 13,
        "325410" to 14,
        "340125" to 11,
        "340251" to 15,
        "340512" to 15,
        "341025" to 14,
        "341205" to 15,
        "341250" to 16,
        "342051" to 16,
        "342501" to 17,
        "342510" to 16,
        "345012" to 10,
        "345102" to 9,
        "345120" to 10,
        "350142" to 9,
        "350214" to 11,
        "350421" to 13,
        "351042" to 14,
        "351402" to 13,
        "351420" to 14,
        "352014" to 12,
        "352104" to 11,
        "352140" to 10,
        "354021" to 14,
        "354201" to 13,
        "354210" to 12,
        "401253" to 8,
        "401325" to 12,
        "401532" to 8,
        "402135" to 18,
        "402351" to 14,
        "402513" to 6,
        "403152" to 18,
        "403215" to 6,
        "403521" to 10,
        "405123" to 20,
        "405231" to 10,
        "405312" to 10,
        "410253" to 7,
        "410325" to 11,
        "410532" to 7,
        "412053" to 4,
        "412503" to 5,
        "412530" to 6,
        "413025" to 4,
        "413205" to 5,
        "413250" to 6,
        "415032" to 8,
        "415302" to 9,
        "415320" to 10,
        "420135" to 19,
        "420351" to 15,
        "420513" to 7,
        "421035" to 14,
        "421305" to 13,
        "421350" to 14,
        "423051" to 10,
        "423501" to 9,
        "423510" to 8,
        "425013" to 18,
        "425103" to 19,
        "425130" to 20,
        "430152" to 19,
        "430215" to 7,
        "430521" to 11,
        "431052" to 10,
        "431502" to 9,
        "431520" to 10,
        "432015" to 18,
        "432105" to 19,
        "432150" to 20,
        "435021" to 10,
        "435201" to 9,
        "435210" to 8,
        "450123" to 21,
        "450231" to 11,
        "450312" to 11,
        "451023" to 10,
        "451203" to 9,
        "451230" to 10,
        "452031" to 14,
        "452301" to 13,
        "452310" to 12,
        "453012" to 18,
        "453102" to 19,
        "453120" to 20,
        "501234" to 16,
        "501342" to 12,
        "501423" to 12,
        "502143" to 6,
        "502314" to 14,
        "502431" to 16,
        "503124" to 10,
        "503241" to 14,
        "503412" to 16,
        "504132" to 10,
        "504213" to 12,
        "504321" to 14,
        "510234" to 15,
        "510342" to 13,
        "510423" to 13,
        "512034" to 16,
        "512304" to 15,
        "512340" to 14,
        "513042" to 16,
        "513402" to 15,
        "513420" to 14,
        "514023" to 14,
        "514203" to 13,
        "514230" to 14,
        "520143" to 7,
        "520314" to 15,
        "520431" to 15,
        "521043" to 14,
        "521403" to 13,
        "521430" to 14,
        "523014" to 10,
        "523104" to 9,
        "523140" to 8,
        "524031" to 16,
        "524301" to 15,
        "524310" to 16,
        "530124" to 11,
        "530241" to 15,
        "530412" to 17,
        "531024" to 18,
        "531204" to 17,
        "531240" to 16,
        "532041" to 18,
        "532401" to 17,
        "532410" to 18,
        "534012" to 12,
        "534102" to 11,
        "534120" to 12,
        "540132" to 9,
        "540213" to 13,
        "540321" to 13,
        "541032" to 10,
        "541302" to 11,
        "541320" to 12,
        "542013" to 8,
        "542103" to 7,
        "542130" to 8,
        "543021" to 12,
        "543201" to 13,
        "543210" to 14,
    )

    fun slidingPuzzle(board: Array<IntArray>): Int {
        val b = board.joinToString("") { it.joinToString("") }
        return data[b] ?: -1
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        val set = "012345".toSet()

        fun bt(set: Set<Char>, acc: String) {
            if (acc.length == 6) {
                val board = arrayOf(
                    acc.take(3).map { it.digitToInt() }.toIntArray(),
                    acc.drop(3).map { it.digitToInt() }.toIntArray()
                )
                val res = s.slidingPuzzle(board)
                if (res != -1) {
                    println("\"$acc\" to ${s.slidingPuzzle(board)},")
                }
                return
            }
            for (c in set) {
                val s = HashSet(set)
                s -= c
                bt(s, acc + c)
            }
        }

        bt(set, "")
    }

}
