package tictactoe

import org.junit.jupiter.api.Test


class Solution {
    class TicTacToe(val n: Int) {
        inner class State(var xs: Int = 0, var ys: Int = 0) {
            fun move(player: Int) {
                if (player == 1) xs++
                else ys++
            }

            fun isFull(player: Int): Boolean {
                return if (player == 1) xs == n
                else ys == n
            }
        }

        val rows = List(n) { State() }
        val columns = List(n) { State() }
        val mainDiag = State()
        val secondaryDiag = State()

        fun move(x: Int, y: Int, player: Int): Int {
            rows[x].move(player)
            if (rows[x].isFull(player)) return player
            columns[y].move(player)
            if (columns[y].isFull(player)) return player

            if (x == y) {
                mainDiag.move(player)
                if (mainDiag.isFull(player)) return player
            }

            if (x == n - y - 1) {
                secondaryDiag.move(player)
                if (secondaryDiag.isFull(player)) return player
            }

            return 0
        }
    }
}

class SolutionTest {
    @Test
    fun test1() {

    }
}