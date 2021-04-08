package prisoncellsafterndays;

class Solution {

    fun prisonAfterNDays(_cells: IntArray, n: Int): IntArray {
        var cells = _cells

        val solutions = mutableListOf<String>()

        while (true) {
            val newCells = IntArray(cells.size)
            for (j in 1..cells.size - 2) {
                val leftOccupied = j - 1 in cells.indices && cells[j - 1] == 1
                val rightOccupied = j + 1 in cells.indices && cells[j + 1] == 1
                newCells[j] = if (leftOccupied == rightOccupied) 1 else 0
            }

            cells = newCells
            val s = newCells.joinToString("") { if (it == 1) "1" else "0" }
            if (solutions.isEmpty()) solutions.add(s)
            else if (solutions[0] != s) solutions.add(s)
            else break
        }

        val index = (n - 1) % solutions.size

        return solutions[index].map { it - '0' }.toIntArray()
    }
}