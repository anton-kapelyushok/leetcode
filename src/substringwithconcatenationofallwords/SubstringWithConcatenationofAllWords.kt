package substringwithconcatenationofallwords

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class Solution {
    fun findSubstring(s: String, words: Array<String>): List<Int> {
        val wordLen = words[0].length

        return (0 until wordLen)
                .flatMap { offset ->
                    findSubstringInternal(s.substring(offset), words).map { it + offset }
                }
                .sorted()
    }

    fun findSubstringInternal(s: String, words: Array<String>): List<Int> {
        val result = mutableListOf<Int>()
        val wordLen = words[0].length
        val missingWords = words.groupingBy { it }.eachCount()
        var currentWordsMap = missingWords.toMutableMap()
        var wordsLeft = words.size

        var index = 0
        while (index + wordLen <= s.length) {
            if (wordsLeft == 0) {
                val startOfFirstWord = index - (words.size * wordLen)
                val firstWord = s.substring(startOfFirstWord until startOfFirstWord + wordLen)
                currentWordsMap[firstWord] = currentWordsMap[firstWord]!! + 1
                wordsLeft += 1
            }

            val currentWord = s.substring(index until index + wordLen)
            if (currentWord !in currentWordsMap) {
                currentWordsMap = missingWords.toMutableMap()
                wordsLeft = words.size
            } else {
                if (currentWordsMap[currentWord]!! > 0) {
                    currentWordsMap[currentWord] = currentWordsMap[currentWord]!! - 1
                    wordsLeft -= 1

                } else {
                    var startOfFirstWord = index - ((words.size - wordsLeft) * wordLen)
                    while (startOfFirstWord < index) {
                        val firstWord = s.substring(startOfFirstWord until startOfFirstWord + wordLen)
                        if (firstWord == currentWord) break

                        currentWordsMap[firstWord] = currentWordsMap[firstWord]!! + 1

                        wordsLeft += 1
                        startOfFirstWord += wordLen
                    }
                }

                if (wordsLeft == 0) result.add(index - (words.size - 1) * wordLen)
            }

            index += wordLen
        }

        return result
    }
}


class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(listOf(0, 9), s.findSubstring("barfoothefoobarman", arrayOf("foo", "bar")))
    }

    @Test
    fun test2() {
        assertEquals(listOf<Int>(), s.findSubstring("wordgoodgoodgoodbestword", arrayOf("word", "good", "best", "word")))
    }

    @Test
    fun test3() {
        assertEquals(listOf(6, 9, 12), s.findSubstring("barfoofoobarthefoobarman", arrayOf("bar", "foo", "the")))
    }
}
