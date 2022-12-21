package AoC2021

import AoC2020.Constants
import utils.*

private fun Board.getLowestPointList(): List<Point> {
    var result = listOf<Point>()
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, _ ->
            val point = x to y
            val pointValue = this.getValueAtPoint(point)!!
            val neighbourValues = point.getAdjacentNumbers(this).filter { it.second != null }
            if (neighbourValues.all { it.second!! > pointValue }) result = result.plus(point)
        }
    }
    return result
}

private fun Point.getBasinFromLowestPoint(board: Board): List<Point> {
    val notSeen = ArrayDeque(listOf(this))
    var seen = setOf<Point>()
    while (!notSeen.isEmpty()) {
        val point = notSeen.removeFirst()
        val pointValue = board.getValueAtPoint(point)!!
        seen = seen.plus(point)
        val higherValues =
            point.getAdjacentNumbers(board)
                .filter { it.second != null }
                .filter { it.second!! > pointValue && it.second!! != 9 && !seen.contains(it.first) }
                .map { it.first }
        notSeen.addAll(higherValues)
    }
    return seen.toList()
}

private fun Board.solve9a(): Int =
    this.getLowestPointList()
        .sumOf { this.getValueAtPoint(it)!! + 1 }

private fun Board.solve9b(): Int =
    this.getLowestPointList()
        .map { it.getBasinFromLowestPoint(this) }
        .sortedBy { it.size }
        .takeLast(3)
        .map { it.size }
        .reduce { acc, i -> acc * i }

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_9.txt").trim()
    val prepped = input.toDigitBoard()
    println("Day 9a answer: ${prepped.solve9a()}")
    println("Day 9b answer: ${prepped.solve9b()}")
}
