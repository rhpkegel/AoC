package AoC2021

import utils.*
import utils.Board

private var testInput = """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"""

private fun Board.solve11a(): Int {
    var totalTicks = 0
    var result = this

    repeat(100) {
        val (newResult, newTicks) = result.dumboTick()
        result = newResult
        totalTicks += newTicks
    }

    return (totalTicks)
}

private fun Board.dumboTick(): Pair<List<List<Int>>, Int> {
    var ticksInDumboTick = 0

    var result = this.mapIndexed outer@{ y, line ->
        line.mapIndexed inner@{ x, value ->
            value + 1
        }
    }

    do {
        val oldResult = result
        val intResult = result.flashTick()
        result = intResult.first
        ticksInDumboTick += intResult.second
    } while (intResult.first != oldResult)

    return result to ticksInDumboTick
}

private fun Board.flashTick(): Pair<Board, Int> {
    val inter = this.toValueList().toMap().toMutableMap()
    var ticksInFlashTick = 0

    inter.forEach { toTickEntry ->
        if (toTickEntry.value > 9) {
            ticksInFlashTick += 1
            inter[toTickEntry.key] = 0
            val adjacentPoints =
                toTickEntry.key.getAdjacentNumbers(this, diagonals = true).filter { it.second != null }.map { it.first }
            adjacentPoints.forEach {
                if (inter[it] != 0) inter[it] = inter[it]!! + 1
            }
        }
    }

    return inter.toList().toBoard() to ticksInFlashTick
}

private fun Board.solve11b(): Int {
    var result = this
    var totalSteps = 0

    do {
        result = result.dumboTick().first
        totalSteps += 1
    } while (!result.all { line -> line.all { it == 0 } })

    return totalSteps

}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_11.txt").toDigitBoard()
    println("Day 11a answer: ${input.solve11a()}")
    println("Day 11b answer: ${input.solve11b()}")
}
