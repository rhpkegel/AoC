package utils

typealias Point = Pair<Int, Int>
typealias ValuePoint = Pair<Pair<Int, Int>, Int>
typealias Line = Pair<Point, Point>
typealias Board = List<List<Int>>

fun Board.transpose(index: Int = 0): Board {
    var newRow = emptyList<Int>()
    this.forEach { newRow = newRow.plus(it[index]) }
    return if (index + 1 >= this.size) listOf(newRow) else listOf(newRow).plus(this.transpose(index + 1))
}

fun Board.getValueAtPoint(point: Point): Int? {
    return this.getOrNull(point.second)?.getOrNull(point.first)
}

fun Point.getAdjacentNumbers(board: Board, diagonals: Boolean = false): List<Pair<Point, Int?>> {
    val (x, y) = this
    var result = listOf(
        ((x + 1 to y) to board.getValueAtPoint(x + 1 to y)),
        ((x - 1 to y) to board.getValueAtPoint(x - 1 to y)),
        ((x to y - 1) to board.getValueAtPoint(x to y - 1)),
        ((x to y + 1) to board.getValueAtPoint(x to y + 1))
    )
    if (diagonals) result = result.plus(listOf(
        ((x + 1 to y +1 ) to board.getValueAtPoint(x + 1 to y + 1)),
        ((x + 1 to y -1 ) to board.getValueAtPoint(x + 1 to y - 1)),
        ((x - 1 to y - 1) to board.getValueAtPoint(x - 1 to y - 1)),
        ((x - 1 to y + 1) to board.getValueAtPoint(x - 1 to y + 1))
    ))
    return result
}

fun Board.toValueList(): List<ValuePoint> = this.mapIndexed outer@{ indexY, line -> return@outer line.mapIndexed inner@{ indexX, value -> return@inner (indexX to indexY) to value } }.flatten()
fun List<ValuePoint>.toBoard(): Board {
    return this.sortedBy { it.first.first }.groupBy { it.first.second }.toList().map{it -> it.second}.map {it.map{it.second}}
}

fun Board.stringify(): String =
    this.mapIndexed { _, line -> line.mapIndexed { _, value -> value.toString() }.reduce{acc, s -> "$acc $s" } }.reduce{ acc, s -> "${acc}\n$s" }

fun String.spaceSeparatedStringToIntBoard(): Board = this
    .trim()
    .lines()
    .map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }

fun String.toDigitBoard(): Board = this.lines().map { line -> line.map { it.digitToInt() } }
