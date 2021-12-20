package AoC2021

import utils.Point
import utils.readInputAsString

typealias BinaryMatrix = Set<Point>
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
    algorithm = algorithm.trim()
    val binaryinput = input.binaryMatrix()
    return binaryinput to algorithm;
}

private fun String.binaryMatrix(): BinaryMatrix {
    val lines = this.lines();
    val result = HashSet<Point>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            val point = (x to y)
            if (char == '#') result.add(point)
        }
    }
    return result
}

private fun BinaryMatrix.enhance(algorithm: String): BinaryMatrix {
    val newMatrix = this.toMutableSet()
    val toCheck = this.flatMap { it.getAdjacentPoints() }
    toCheck.forEach {
        val isLit = algorithm[it.getAdjacencyString(this).toInt(2)] == '#'
        if (isLit) newMatrix.add(it) else newMatrix.remove(it)
    }
    return newMatrix
}

private fun Point.getAdjacentPoints(): Set<Point> {
    return (-1..1).map { yDelta ->
        (-1..1).map { xDelta ->
            this.first + xDelta to this.second + yDelta
        }
    }.flatten().toSet()
}

private fun Point.getAdjacencyString(matrix: Set<Point>): String {
    val resultString = (-1..1).map { yDelta ->
        (-1..1).map { xDelta ->
            val pointToCheck = this.first + xDelta to this.second + yDelta
            if (pointToCheck in matrix) '1' else '0'
        }
    }.flatten()
    return resultString.joinToString("")
}

private fun String.solve20a(): Int {
    val (binaryInput, algorithm) = this.parse()
    val newMatrix = binaryInput.enhance(algorithm)
    println(newMatrix.stringifyBinaryMatrix())
    return newMatrix.size
}

private fun Set<Point>.stringifyBinaryMatrix(): String {
    val maxX = this.maxByOrNull { it.first }!!.first
    val maxY = this.maxByOrNull { it.second }!!.second

    return (0..maxY).map { y ->
        (0..maxX).map { x ->
            if (x to y in this) '#' else '.'
        }.joinToString("").plus("")
    }.joinToString ("\n")
}

private fun String.solve20b() {}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_20.txt")
    println("Day 20a answer: ${testInput.solve20a()}")
    println("Day 20b answer: ${testInput.solve20b()}")
}
