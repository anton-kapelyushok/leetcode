package sudokusolver

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

typealias Sudoku = Array<IntArray>

class Solution {
    fun solveSudoku(input: Array<CharArray>): Unit {
        val intBoard = input.map { row -> row.map { cell -> if (cell == '.') 0 else cell - '0' }.toIntArray() }.toTypedArray()
        val solved = solve(intBoard)!!
        for (rowI in solved.indices) {
            for (colI in solved[rowI].indices) {
                input[rowI][colI] = (solved[rowI][colI] + '0'.toInt()).toChar()
            }
        }
    }

    fun solve(sudoku: Sudoku): Sudoku? {

        loop@ while (true) {
            val p = sudoku.map { row -> row.map { cell -> (0..9).map { true }.toTypedArray() }.toTypedArray() }.toTypedArray()
            for (rowI in sudoku.indices) {
                for (colI in sudoku[rowI].indices) {
                    val value = sudoku[rowI][colI]
                    if (value > 0) {
                        p[rowI][colI] = (0..9).map { false }.toTypedArray()

                        (0 until 9).forEach { i ->
                            p[rowI][i][value] = false
                            p[i][colI][value] = false
                            val colIndex = i / 3 + colI / 3 * 3
                            val rowIndex = i % 3 + rowI / 3 * 3
                            p[rowIndex][colIndex][value] = false
                        }

                        p[rowI][colI][value] = true
                    }
                }
            }

            var updated = false
            for (rowI in sudoku.indices) {
                for (colI in sudoku[rowI].indices) {
                    val value = sudoku[rowI][colI]
                    val possibleValues = (1..9).filter { p[rowI][colI][it] }
                    if (possibleValues.isEmpty()) {
                        return null
                    }

                    if (possibleValues.size == 1) {
                        if (value != possibleValues[0]) {
                            sudoku[rowI][colI] = possibleValues[0]
                            updated = true
                        }
                    }
                }
            }

            if (!updated) {
                for (rowI in sudoku.indices) {
                    for (colI in sudoku[rowI].indices) {
                        if (sudoku[rowI][colI] == 0) {
                            val possibleValues = (1..9).filter { p[rowI][colI][it] }
                            for (v in possibleValues) {
                                val guess = sudoku.map { row -> intArrayOf(*row) }.toTypedArray()
                                guess[rowI][colI] = v
                                println("Guess $rowI, $colI -> $v")
                                val solved = solve(guess)
                                if (solved != null) return solved
                            }
                            return null
                        }
                    }
                }
                return sudoku
            }
        }
    }
}

class SolutionTest {
    private val s = Solution()

    @Test
    fun test1() {
        val input = arrayOf(
                charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
                charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
                charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
                charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
                charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
                charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
                charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
                charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
                charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9'))

        s.solveSudoku(input)
        val actual = input

        val expected = arrayOf(
                charArrayOf('5', '3', '4', '6', '7', '8', '9', '1', '2'),
                charArrayOf('6', '7', '2', '1', '9', '5', '3', '4', '8'),
                charArrayOf('1', '9', '8', '3', '4', '2', '5', '6', '7'),
                charArrayOf('8', '5', '9', '7', '6', '1', '4', '2', '3'),
                charArrayOf('4', '2', '6', '8', '5', '3', '7', '9', '1'),
                charArrayOf('7', '1', '3', '9', '2', '4', '8', '5', '6'),
                charArrayOf('9', '6', '1', '5', '3', '7', '2', '8', '4'),
                charArrayOf('2', '8', '7', '4', '1', '9', '6', '3', '5'),
                charArrayOf('3', '4', '5', '2', '8', '6', '1', '7', '9'))

        Assertions.assertEquals(expected.asString(), actual.asString())
    }

    @Test
    fun test2() {
        val input = arrayOf(
                charArrayOf('.', '.', '.', '2', '.', '.', '.', '6', '3'),
                charArrayOf('3', '.', '.', '.', '.', '5', '4', '.', '1'),
                charArrayOf('.', '.', '1', '.', '.', '3', '9', '8', '.'),
                charArrayOf('.', '.', '.', '.', '.', '.', '.', '9', '.'),
                charArrayOf('.', '.', '.', '5', '3', '8', '.', '.', '.'),
                charArrayOf('.', '3', '.', '.', '.', '.', '.', '.', '.'),
                charArrayOf('.', '2', '6', '3', '.', '.', '5', '.', '.'),
                charArrayOf('5', '.', '3', '7', '.', '.', '.', '.', '8'),
                charArrayOf('4', '7', '.', '.', '.', '1', '.', '.', '.'))


        s.solveSudoku(input)
        val actual = input

        val expected = arrayOf(charArrayOf('8', '5', '4', '2', '1', '9', '7', '6', '3'),
                charArrayOf('3', '9', '7', '8', '6', '5', '4', '2', '1'),
                charArrayOf('2', '6', '1', '4', '7', '3', '9', '8', '5'),
                charArrayOf('7', '8', '5', '1', '2', '6', '3', '9', '4'),
                charArrayOf('6', '4', '9', '5', '3', '8', '1', '7', '2'),
                charArrayOf('1', '3', '2', '9', '4', '7', '8', '5', '6'),
                charArrayOf('9', '2', '6', '3', '8', '4', '5', '1', '7'),
                charArrayOf('5', '1', '3', '7', '9', '2', '6', '4', '8'),
                charArrayOf('4', '7', '8', '6', '5', '1', '2', '3', '9'))


        Assertions.assertEquals(expected.asString(), actual.asString())
    }

    private fun Array<CharArray>.asString(): String =
            this.joinToString("\n") { row -> row.joinToString(",") { it.toString() } }
}
