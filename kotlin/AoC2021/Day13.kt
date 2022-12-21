package AoC2021

import utils.Point
import utils.readInputAsString

typealias FoldInstruction = Pair<Char, Int>
typealias FoldPuzzle = Pair<Set<Point>, List<FoldInstruction>>

private var testInput = """6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"""

private fun String.parseInput(): Pair<Set<Point>, List<FoldInstruction>> {
    val (points, instructions) = this.split("\n\n", "\r\n\r\n")
    val pointList = points.lines().map {
        val split = it.split(',')
        split[0].toInt() to split[1].toInt()
    }.toSet()
    val instructionList = instructions.trim().lines().map {
        val split = it.split("=")
        split[0].last() to split[1].toInt()
    }
    return pointList to instructionList
}

private fun Set<Point>.stringifySparseMatrix(): String {
    val maxX = this.maxByOrNull { it.first }?.first ?: 0
    val maxY = this.maxByOrNull { it.second }?.second ?: 0
    val resultMatrix = (0..maxY).map { y ->
        (0..maxX).map { x ->
            if (this.contains(x to y)) "#" else "."
        }
    }
    return resultMatrix
        .map { line -> line
            .reduce { acc, s -> "$acc $s" } }
        .reduce { acc, s -> "${acc}\n$s" }
}

private fun FoldPuzzle.fold(): FoldPuzzle{
    var (points, foldInstructions) = this
    val foldDirection = foldInstructions[0].first
    val foldLine = foldInstructions[0].second
    val complementToFill = foldLine * 2
    val pointsGreaterThanFoldLine =
        points.filter { (foldDirection == 'x' && it.first > foldLine) || (foldDirection == 'y' && it.second > foldLine) }
            .toSet()
    val pointComplements = pointsGreaterThanFoldLine.map {
        if (foldDirection == 'x') complementToFill - it.first to it.second else it.first to complementToFill - it.second
    }
    points = points.minus(pointsGreaterThanFoldLine).plus(pointComplements)
    return points to foldInstructions.drop(1)
}

private fun String.solve13a(): Int = this.parseInput().fold().first.count()

private fun String.solve13b(): Set<Point> {
    var result = this.parseInput()
    do {
        result = result.fold()
    } while (result.second.isNotEmpty())
    return result.first
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_13.txt")
    println("Day 13a answer: ${input.solve13a()}")
    println("Day 13b answer:\n${input.solve13b().stringifySparseMatrix()}")
}
