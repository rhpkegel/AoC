package AoC2021

import utils.Line
import utils.Point
import utils.readInputAsListOfStrings

private var test_input =
    """0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"""

private fun List<String>.toLineList(): List<Line> {
    return this.map {
        val (startLine, endLine) = it.split(" -> ")
        Pair(startLine.toCoordinate(), endLine.toCoordinate())
    }
}

private fun String.toCoordinate(): Point {
    val (x, y) = this.split(",")
    return Pair(x.toInt(), y.toInt())
}

private fun Line.lineToPoints(considerDiagonals: Boolean = false): List<Point> {
    var result = emptyList<Pair<Int, Int>>()
    val (x1, y1) = this.first
    val (x2, y2) = this.second
    if (x1 == x2) {
        x1.let { x -> (minOf(y1, y2)..maxOf(y1, y2)).forEach { y -> result = result.plus(Pair(x, y)) } }
    } else if (y1 == y2) {
        y1.let { y -> (minOf(x1, x2)..maxOf(x1, x2)).forEach { x -> result = result.plus(Pair(x, y)) } }
    } else if (considerDiagonals) {
        var xDir = (minOf(x1, x2)..maxOf(x1, x2)).toList()
        var yDir = (minOf(y1, y2)..maxOf(y1, y2)).toList()
        if (x1 < x2) xDir = xDir.reversed()
        if (y1 < y2) yDir = yDir.reversed()
        result = result.plus(xDir.zip(yDir))
    }
    return result
}

private fun List<Line>.toCollisionCount(considerDiagonals: Boolean): HashMap<Point, Int> {
    val result: HashMap<Point, Int> = HashMap()
    this.forEach { line ->
        line.lineToPoints(considerDiagonals).forEach {
            val current = result[it]
            if (current == null) result[it] = 1 else result[it] = current + 1
        }
    }
    return result
}

private fun List<String>.solve5a(): Int =
    this.toLineList().toCollisionCount(false).filter { (_, value) -> value > 1 }.size

private fun List<String>.solve5b(): Int =
    this.toLineList().toCollisionCount(true).filter { (_, value) -> value > 1 }.size

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_5b.txt")
    println("Day 5a solution: ${input.solve5a()}")
    println("Day 5b solution: ${input.solve5b()}")
}
