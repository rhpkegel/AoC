package AoC2021

import utils.readAsEmptyLineSeparatedStrings
import utils.spaceSeparatedStringToIntBoard
import utils.transpose

typealias Board = List<List<Int>>

private var test_input =
"""7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24, 10, 16, 13, 6, 15, 25, 12, 22, 18, 20, 8, 19, 3, 26, 1

    22 13 17 11  0
    8  2 23  4 24
    21  9 14 16  7
    6 10  3 18  5
    1 12 20 15 19

    3 15  0  2 22
    9 18 13 17  5
    19  8  7 25 23
    20 11 10 24  4
    14 21 16 12  6

    14 21 17 24  4
    10 16 15  9 19
    18  8 23 26 20
    22 11 13  6  5
    2  0 12  3  7"""

private fun Board.bingo(numberSequence: List<Int>): Pair<Board, List<Int>>? {
    this.forEach { if (it.all{numberSequence.contains(it)}) return Pair(this, numberSequence) }
    this.transpose().forEach { if (it.all{numberSequence.contains(it)}) return Pair(this, numberSequence) }
    return null
}

private fun solve(numberSequence: List<Int>, boards: List<Board>, winningIsLosing: Boolean): Int {
    val boardWins = boards.map { board ->
        generateSequence(0){ it+1 }
            .take(numberSequence.size)
            .firstNotNullOfOrNull {
            board.bingo(
                numberSequence.subList(
                    0,
                    it
                )
            )
        }
    }
    val winningBoard = if (winningIsLosing) (boardWins.maxByOrNull { it!!.second.size }) else (boardWins.minByOrNull { it!!.second.size })
    val unmarkedNumberSum = winningBoard?.let{it.first.flatten().filter { !winningBoard.second.contains(it) }.sum()}
    val winningNumber = winningBoard?.second?.last()
    return if (unmarkedNumberSum != null && winningNumber != null) unmarkedNumberSum * winningNumber else 0
}

fun main() {
    val input = readAsEmptyLineSeparatedStrings("${Constants.INPUT_PATH}input_day_4.txt")
    val sequence = input.first().split(",").map { it.trim().toInt() }
    val boards: List<Board> = input.drop(1).map{it.spaceSeparatedStringToIntBoard()}
    println("4a solution: ${solve(sequence, boards, false)}")
    println("4b solution: ${solve(sequence, boards, true)}")
}
