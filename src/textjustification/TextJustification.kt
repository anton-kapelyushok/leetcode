package textjustification

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Solution {
    fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
        val wordList = LinkedList(words.toList())
        val result = mutableListOf<String>()
        while (wordList.isNotEmpty()) {
            result += produceLine(wordList, maxWidth)
        }
        return result
    }

    fun produceLine(words: LinkedList<String>, maxWidth: Int): String {
        if (words.isEmpty()) error("expected non empty words")

        val thisWords = mutableListOf<String>()
        thisWords += words.pollFirst()
        var width = thisWords[0].length

        var isEmpty = false
        while (true) {
            if (words.isEmpty()) {
                isEmpty = true
                break
            }

            val newWidth = width + words.peekFirst().length + 1
            if (newWidth > maxWidth) {
                break
            }

            width = newWidth
            thisWords += words.pollFirst()
        }

        if (isEmpty) {
            val sb = StringBuilder(maxWidth)
            for ((idx, w) in thisWords.withIndex()) {
                sb.append(w)
                if (idx == thisWords.lastIndex) {
                    while (sb.length != maxWidth) {
                        sb.append(' ')
                    }
                } else {
                    sb.append(' ')
                }
            }
            return sb.toString()
        }

        if (thisWords.size == 1) {
            val sb = StringBuilder(maxWidth)
            sb.append(thisWords[0])
            while (sb.length != maxWidth) {
                sb.append(' ')
            }
            return sb.toString()
        }

        val sb = StringBuilder(maxWidth)

        val wordsWithoutSpacesLength = thisWords.sumOf { it.length }
        val totalGap = maxWidth - wordsWithoutSpacesLength
        val nGaps = thisWords.size - 1

        val oneGapLength = totalGap / nGaps
        var extraGaps = totalGap % nGaps

        for ((idx, w) in thisWords.withIndex()) {
            sb.append(w)

            if (idx != thisWords.lastIndex) {
                repeat(oneGapLength) {
                    sb.append(' ')
                }
                if (extraGaps > 0) {
                    extraGaps--
                    sb.append(' ')
                }
            }
        }

        return sb.toString()
    }
}

class SolutionTest {

    private val s = Solution()

    @Test
    fun test1() {
        assertEquals(
            listOf(
                "This    is    an",
                "example  of text",
                "justification.  "
            ), s.fullJustify(arrayOf("This", "is", "an", "example", "of", "text", "justification."), 16)
        )
    }

    @Test
    fun test2() {
        assertEquals(
            listOf(
                "Science  is  what we",
                "understand      well",
                "enough to explain to",
                "a  computer.  Art is",
                "everything  else  we",
                "do                  "
            ), s.fullJustify(arrayOf("Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"), 20)
        )
    }

    @Test
    fun test3() {
        assertEquals(
            listOf(
                "What   must   be",
                "acknowledgment  ",
                "shall be        "
            ), s.fullJustify(arrayOf("What","must","be","acknowledgment","shall","be"), 16)
        )
    }

}
