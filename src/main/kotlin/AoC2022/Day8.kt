package AoC2022

import utils.readInputAsString
import utils.takeWhileInclusive

private typealias Line = List<Int>
private typealias Point = Pair<Int, Int>
private typealias Board = List<Line>

private var testInput = ("30373\n" +
        "25512\n" +
        "65332\n" +
        "33549\n" +
        "35390")

private fun String.toBoard(): Board {
    val lines = this.split("\n", "\r\n");
    val board = lines.map { it.toList().map { it.digitToInt() } }
    return board
}

private fun Board.transpose(): Board {
    val transposedBoard = mutableListOf<Line>()
    (0 until this.size).forEach { index -> transposedBoard.add(this.map{
        it[index]
    }) }
    return transposedBoard
}

private fun Point.getNeighbourLists(board: Board): List<Line> {
    val westList = board[first].subList(0,second)
    val eastList = if (second < board.size) board[first].subList(second+1, board.size) else emptyList()
    val transposedBoard = board.transpose()
    val northList = transposedBoard[second].subList(0, first)
    val southList = if (first < board.size) transposedBoard[second].subList(first+1, board.size) else emptyList()
    return listOf(northList, eastList, southList, westList)
}

private fun Board.prettyPrint() {
    this.forEach{
        it.forEach{print("${it} ")}
        print("\n")
    }
}

private fun String.solve8a(): Int {
    val board = this.toBoard()
    var visibleTreeCounter = 0
    (0 until board.size).forEach{row->
        (0 until board.size).forEach{col ->
            val pointValue = board[row][col]
            val visible = Pair(row, col).getNeighbourLists(board).any{
                if (!it.isEmpty()) it.max() < pointValue else true
            }
            if (visible){
                visibleTreeCounter += 1
            }
        }
    }
    return visibleTreeCounter
}

private fun Point.getScenicScore(board: Board): Int {
    val pointValue = board[first][second]
    val neighbourLists = Pair(first, second).getNeighbourLists(board)
    val sightLineLists = listOf(neighbourLists[0].reversed(), neighbourLists[1], neighbourLists[2], neighbourLists[3].reversed())
    return sightLineLists.map{line -> line.takeWhileInclusive { it < pointValue }.toList().size}.reduce { acc, i -> acc*i }
}

private fun String.solve8b(): Int {
    val board = this.toBoard()
    var maxScenicScore = 0

    (0 until board.size).forEach{row->
        (0 until board.size).forEach{col ->
            val scenicScore = Point(row, col).getScenicScore(board)
            if(scenicScore > maxScenicScore) maxScenicScore = scenicScore
        }
    }
    return maxScenicScore
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_8.txt")
    println("Day 8a answer: ${input.solve8a()}")
    println("Day 8b answer: ${input.solve8b()}")
}
