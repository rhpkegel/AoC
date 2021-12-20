package AoC2021

import utils.Point
import utils.readInputAsString
import utils.stringify
import utils.toBoard
import kotlin.math.sqrt

typealias BinaryMatrix = Map<Point, Char>
typealias Algorithm = String

private var testInput = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##\n" +
        "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###\n" +
        ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.\n" +
        ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....\n" +
        ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..\n" +
        "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....\n" +
        "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#\n" +
        "\n" +
        "#..#.\n" +
        "#....\n" +
        "##..#\n" +
        "..#..\n" +
        "..###"

private fun String.parse(): Pair<BinaryMatrix, Algorithm> {
    var (algorithm, input) = this.split("\n\n", "\r\n\r\n")
    algorithm = algorithm.replace("\n", "")
    val binaryinput = input.binaryMatrix()
    return binaryinput to algorithm
}

private fun String.binaryMatrix(): BinaryMatrix {
    val lines = this.lines()
    val result = HashMap<Point, Char>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            val toPut = if (char == '#') '1' else '0'
            val point = (x to y)
            result[point] = toPut
        }
    }
    return result
}

private fun BinaryMatrix.enhance(algorithm: String, step: Int): BinaryMatrix {
    val newMatrix = this.toMutableMap()
    this.forEach {
        val algorithmPos = it.key.getAdjacencyString(this, step).toInt(2)
        val newValue = if (algorithm[algorithmPos] == '#') '1' else '0'
        newMatrix[it.key] = newValue
    }
    return newMatrix
}

private fun BinaryMatrix.enlargeMatrix(): BinaryMatrix {
    val enlargedMatrix = HashMap<Point, Char>()
    val newMaxIndex = sqrt(this.size.toDouble()).toInt() +1
    this.forEach {
        val oldPoint = it.key
        val newPoint = oldPoint.first + 1 to oldPoint.second + 1
        enlargedMatrix[newPoint] = it.value
    }
    (0..newMaxIndex).forEach {
        enlargedMatrix[0 to it] = '0'
        enlargedMatrix[newMaxIndex to it] = '0'
        enlargedMatrix[it to 0] = '0'
        enlargedMatrix[it to newMaxIndex] = '0'
    }
    return enlargedMatrix
}

private fun Point.getAdjacencyString(matrix: BinaryMatrix, step: Int): String {
    val resultString = (-1..1).map { yDelta ->
        (-1..1).map { xDelta ->
            val pointToCheck = this.first + xDelta to this.second + yDelta
            val result = matrix[pointToCheck]
            result ?: if (step % 2 == 1) '1' else '0'
        }
    }.flatten()
    return resultString.joinToString("")
}

private fun String.solve20a(): Int {
    val (binaryInput, algorithm) = this.parse()

    var newMatrix = binaryInput
    var counter = 0
    repeat(4){ newMatrix = newMatrix.enlargeMatrix() }
    repeat(2) {newMatrix = newMatrix.enhance(algorithm, counter); counter+=1}
    return newMatrix.filter{it.value=='1'}.size
}

private fun BinaryMatrix.stringifyBinaryMatrix(): String =
    this.toList().map { it.first to it.second.digitToInt() }.toBoard().stringify()

private fun String.solve20b(): Int {
    val (binaryInput, algorithm) = this.parse()

    var newMatrix = binaryInput
    var counter = 0
    repeat(52) { newMatrix = newMatrix.enlargeMatrix() }
    repeat(50) { newMatrix = newMatrix.enhance(algorithm, counter); counter += 1 }
    return newMatrix.filter { it.value == '1' }.size
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_20.txt")
    println("Day 20a answer: ${input.solve20a()}")
    println("Day 20b answer: ${input.solve20b()}")
}
