package problems

import java.util.TreeSet
import kotlin.random.Random

object DistinctSubsequencesII {
    class Solution {

        private val mod = 1_000_000_000 + 7

        fun distinctSubseqII(s: String): Int {
            val charLookup = constructMap(s)

            var result = 0L
            val dp = IntArray(s.length + 1) { -1 }
            result += f(charLookup, dp, s, -1, 0) + mod - 1
            result %= mod
            return result.toInt()
        }

        fun f(charLookup: Array<TreeSet<Int>>, dp: IntArray, s: String, i: Int, n: Int): Int {
            if (dp[i + 1] != -1) return dp[i + 1]
            var result = 1L
            for (c in 'a'..'z') {
                val j = nextIndex(charLookup, c, i)
                if (j < 0) continue
                result += f(charLookup, dp, s, j, n - 1)
                result %= mod
            }
            dp[i + 1] = result.toInt()
            return result.toInt()
        }

        fun constructMap(s: String): Array<TreeSet<Int>> {
            val result = Array(26) { TreeSet<Int>() }
            for (i in s.indices) {
                result[s[i] - 'a'] += i
            }
            return result
        }

        // index of c in string higher than i
        fun nextIndex(charLookup: Array<TreeSet<Int>>, c: Char, i: Int): Int {
            return charLookup[c - 'a'].higher(i) ?: -1
        }
    }

    class Solution2 { // n^2

        private val mod = 1_000_000_000 + 7

        fun distinctSubseqII(s: String): Int {
            val charLookup = constructMap(s)

            var result = 0L
            val dp = Array(s.length) { IntArray(s.length + 1) { -1 } }
            for (n in 2..s.length + 1) {
                result += f(charLookup, dp, s, -1, n)
                result %= mod
            }

            return result.toInt()
        }

        // distinct subsequences of length n starting at i
        fun f(charLookup: Array<TreeSet<Int>>, dp: Array<IntArray>, s: String, i: Int, n: Int): Int {
            if (n == 1 && i in s.indices) {
                return 1
            }
            if (n > 0 && i >= s.length) {
                return 0
            }
            if (s.length - i < n) {
                return 0
            }
            if (dp[n - 2][i + 1] != -1) {
                return dp[n - 2][i + 1]
            }
            var result = 0L
            for (c in 'a'..'z') {
                val j = nextIndex(charLookup, c, i)
                if (j < 0) continue
                result += f(charLookup, dp, s, j, n - 1)
                result %= mod
            }
            dp[n - 2][i + 1] = result.toInt()
            return result.toInt()
        }

        fun constructMap(s: String): Array<TreeSet<Int>> {
            val result = Array(26) { TreeSet<Int>() }
            for (i in s.indices) {
                result[s[i] - 'a'] += i
            }
            return result
        }

        // index of c in string higher than i
        fun nextIndex(charLookup: Array<TreeSet<Int>>, c: Char, i: Int): Int {
            return charLookup[c - 'a'].higher(i) ?: -1
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        fun Solution2.checkF(s: String, i: Int, n: Int) {
            val dp = Array(s.length + 1) { IntArray(s.length + 1) { -1 } }
            val charLookup = constructMap(s)
            val actual = this.f(charLookup, dp, s, i, n)
            val generated = mutableSetOf<String>()
            val acc = StringBuilder()
            acc.append(s[i])
            fun f(ii: Int) {
                if (acc.length == n) {
                    generated += acc.toString()
                    return
                }
                if (ii == s.length) return
                acc.append(s[ii])
                f(ii + 1)
                acc.deleteCharAt(acc.lastIndex)

                f(ii + 1)
            }
            f(i + 1)
            val expected = generated.size
            println("f(s = $s, i = $i, n = $n): expected $expected, actual $actual")
        }

        fun naive(s: String): List<String> {
            val allGenerated = mutableListOf<String>()
            fun f(i: Int, acc: StringBuilder) {
                if (i == s.length) return
                f(i + 1, acc)// do nothing
                acc.append(s[i])
                allGenerated += acc.toString()
                f(i + 1, acc)
                acc.deleteCharAt(acc.lastIndex)
            }

            f(0, StringBuilder())

            return allGenerated
        }

        @Suppress("NAME_SHADOWING")
        fun Solution2.check(s: String, expected: Int? = null) {
            val expected = expected ?: naive(s).distinct().count()
            val actual = distinctSubseqII(s)
            val trimmed = if (s.length > 60) s.take(57) + "..." else s
            println("distinctSubseqII(s = $trimmed): expected $expected, actual $actual")
        }

        @Suppress("NAME_SHADOWING")
        fun Solution.check(s: String, expected: Int? = null) {
            val expected = expected ?: naive(s).distinct().count()
            val actual = distinctSubseqII(s)
            val trimmed = if (s.length > 60) s.take(57) + "..." else s
            println("distinctSubseqII(s = $trimmed): expected $expected, actual $actual")
        }

        println()

        with(Solution()) {
            check("abbaadsfa")
            check("aaaa")
            check("aaadsfa")
            check("abbaadsfa")
            check("a".repeat(2000), expected = 2000)
            check(
                "swaxupulikvbjtsqhogfspihyiivbazmoljbgacbeccawgvshiogebxnmqeuhwoqmdogbxogxoqpvebliugoqwwndtbiuzgxktuarozybnjzessyqtylplxtwadjwzlzgzjcuilfqlylbgebmflsptsvrifcngyaliaupopccfkhfqmgauckglfplfhfhezdephvdfwebwbvxrccbsmxkpmxkzxtderujbiadrpflphbtmqivpjfrqhhiuudwdeikjvpvvaffsgsqtxeizrupruuytkpivaeyazscbgjdtyhjapdzkxoixmxfnkaxbthqeqkficropkuocdlqhgsiyxbuhwlcqczeasajpmohahsfrgwtszazprntqzksvmcmcagywhvmquexldcjmnlglpxgqhdoxbhpemrmxvqdlnjqodjvungsapoqrigdqarwxtiizuqvjwohgfzbtxvbxjmyflzagvyloggeosatpvrremsjdofoanzsrlovylylfclgetbkwjzvduhbfnpqqqebgpuivvxovzszmztwqochilpqdfztyuayltngvjwjntfqjwxpkzzgurlzletdkofznpuenxlhenvqvrvntqdsblrfgqkhhyiyjlhoatfyrwjvzovpvagrxfboqoyjigcvqxddduhaqezlpxduczpusrcdkfvvwrzzxphadfpfkfgqctcnepggkxostbpricxvrzvzynzgqgpmlhxwbtnztutqswxyvukgrjzpzihsylxgkmfvhzyyjkxwsjwyxhstzopwmayvocmgwyqiomrctauriaurfsecdwcevejhcbijnwqhkgsffzpljwkwagubforlbzegrgfuddggredfpkqprhwrxwhvwkxdnrfhohpozoueazfhzxqhbltrmsleeqhzbdauxsoyglbpjxfsrwnenvlmovzovbfhjryazqzfoltcrhfavhupbofdfkxoqbstxhipkuwutgblaojhaewrxxjoudzmegdvylpzeuobztgmjuhokofinraofucufxuezfxigpbulowosoyznvrdppgjsiokltmfdxobsrgvcptqfkkhkkxbukuvgnqapktraahwedgdsewrnfzlvwinornynaxsmsnaeyfiwyecdrietsezhhotaygqkrwnxafbtckuxjiaxonxsypvovuqywqtbrxukmrbldilkdsdmvnmifsyoeqcdwapnewnbwymcmwwzhsgvzgpqeyhpvnwijzomeyfsxdyifcpghnzeywtpxswfcntdjklxdbwhkvemuszconthzrzdxlgnzfnclplzvhmboeamzkyptorvcsqssavpqgncrwurnpiyomovqfjoupphzlctnbkfejszjbelmehppdyskgsifrosgmvivwzsphwmxzssebqiuqslsntkmxpspbmxbxphxysqtgebszkwysgtpnwsfufxonhjusvdkhxxsjqutewoysfgcqvifdqfyghcswtomqldahbeytctceolztjguwwozbulrggdxipyweubmysyfzwcqbehctuuqebvgclbcpqkqjtiocrsvfckahdpflytfvqrwwhbavgmadnibsdmkexidduvckdneovolnecfwrwickpyjiiszlxcfcusarzjbwkqhsnxxqfucnaogrodnhjlkntqfcbfhpdgmtmtpdyoroybgzxzwenmzyeqqeohgkwwauwibnqiurfzxdzpmztsnuogbpuslwpiqlhwhdeqbpewpaukyandaqearvbsmlowcvcoxwrzyjxhnjsgnnrwghkfrerugqhjgkwqydllhmsxqyuzpkbkiefjkeszacxhoyinbiveqzupuasytyqgfueditgovmojsfghkeynshrfnvntzsknfwdjrtbpsfxobycsrmsxfvrjegeyoltgsjqfmaiklyivbwlaytoqaioynhvlmhdvvmynwsvgfiviebkpc",
                expected = 110750980
            )

            check(
                "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop",
                expected = 1000000006
            )
        }

        with(Solution2()) {
            checkF("abcde", 1, 2)
            checkF("abcde", 2, 2)
            checkF("abcde", 0, 3)
            checkF("aaaaa", 0, 3)
            checkF("aaaab", 0, 3)

            check("swaxupulikvbjtsqhogfspihyiivbazmoljbgacbeccawgvshiogebxnmqeuhwoqmdogbxogxoqpvebliu", expected = -1)
            check("aaa")
            check("aaaa")
            check("aaadsfa")
            check("abbaadsfa")
            check("abc")
            check("aba")
            check("a".repeat(2000), expected = 2000)
            check(
                "swaxupulikvbjtsqhogfspihyiivbazmoljbgacbeccawgvshiogebxnmqeuhwoqmdogbxogxoqpvebliugoqwwndtbiuzgxktuarozybnjzessyqtylplxtwadjwzlzgzjcuilfqlylbgebmflsptsvrifcngyaliaupopccfkhfqmgauckglfplfhfhezdephvdfwebwbvxrccbsmxkpmxkzxtderujbiadrpflphbtmqivpjfrqhhiuudwdeikjvpvvaffsgsqtxeizrupruuytkpivaeyazscbgjdtyhjapdzkxoixmxfnkaxbthqeqkficropkuocdlqhgsiyxbuhwlcqczeasajpmohahsfrgwtszazprntqzksvmcmcagywhvmquexldcjmnlglpxgqhdoxbhpemrmxvqdlnjqodjvungsapoqrigdqarwxtiizuqvjwohgfzbtxvbxjmyflzagvyloggeosatpvrremsjdofoanzsrlovylylfclgetbkwjzvduhbfnpqqqebgpuivvxovzszmztwqochilpqdfztyuayltngvjwjntfqjwxpkzzgurlzletdkofznpuenxlhenvqvrvntqdsblrfgqkhhyiyjlhoatfyrwjvzovpvagrxfboqoyjigcvqxddduhaqezlpxduczpusrcdkfvvwrzzxphadfpfkfgqctcnepggkxostbpricxvrzvzynzgqgpmlhxwbtnztutqswxyvukgrjzpzihsylxgkmfvhzyyjkxwsjwyxhstzopwmayvocmgwyqiomrctauriaurfsecdwcevejhcbijnwqhkgsffzpljwkwagubforlbzegrgfuddggredfpkqprhwrxwhvwkxdnrfhohpozoueazfhzxqhbltrmsleeqhzbdauxsoyglbpjxfsrwnenvlmovzovbfhjryazqzfoltcrhfavhupbofdfkxoqbstxhipkuwutgblaojhaewrxxjoudzmegdvylpzeuobztgmjuhokofinraofucufxuezfxigpbulowosoyznvrdppgjsiokltmfdxobsrgvcptqfkkhkkxbukuvgnqapktraahwedgdsewrnfzlvwinornynaxsmsnaeyfiwyecdrietsezhhotaygqkrwnxafbtckuxjiaxonxsypvovuqywqtbrxukmrbldilkdsdmvnmifsyoeqcdwapnewnbwymcmwwzhsgvzgpqeyhpvnwijzomeyfsxdyifcpghnzeywtpxswfcntdjklxdbwhkvemuszconthzrzdxlgnzfnclplzvhmboeamzkyptorvcsqssavpqgncrwurnpiyomovqfjoupphzlctnbkfejszjbelmehppdyskgsifrosgmvivwzsphwmxzssebqiuqslsntkmxpspbmxbxphxysqtgebszkwysgtpnwsfufxonhjusvdkhxxsjqutewoysfgcqvifdqfyghcswtomqldahbeytctceolztjguwwozbulrggdxipyweubmysyfzwcqbehctuuqebvgclbcpqkqjtiocrsvfckahdpflytfvqrwwhbavgmadnibsdmkexidduvckdneovolnecfwrwickpyjiiszlxcfcusarzjbwkqhsnxxqfucnaogrodnhjlkntqfcbfhpdgmtmtpdyoroybgzxzwenmzyeqqeohgkwwauwibnqiurfzxdzpmztsnuogbpuslwpiqlhwhdeqbpewpaukyandaqearvbsmlowcvcoxwrzyjxhnjsgnnrwghkfrerugqhjgkwqydllhmsxqyuzpkbkiefjkeszacxhoyinbiveqzupuasytyqgfueditgovmojsfghkeynshrfnvntzsknfwdjrtbpsfxobycsrmsxfvrjegeyoltgsjqfmaiklyivbwlaytoqaioynhvlmhdvvmynwsvgfiviebkpc",
                expected = 110750980
            )
            check(String((1..2000).map { 'a' + Random.Default.nextInt(26) }.toCharArray()), expected = -1)
        }
    }
}