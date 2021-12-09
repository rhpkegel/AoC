package utils

typealias Point = Pair<Int, Int>
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

fun Point.getAdjacentNumbers(board: Board): List<Pair<Point, Int?>> {
    val (x, y) = this;
    return listOfNotNull(
        ((x + 1 to y) to board.getValueAtPoint(x + 1 to y)),
        ((x - 1 to y) to board.getValueAtPoint(x - 1 to y)),
        ((x to y - 1) to board.getValueAtPoint(x to y - 1)),
        ((x to y + 1) to board.getValueAtPoint(x to y + 1))
    )
}

fun String.spaceSeparatedStringToIntBoard(): Board = this
    .trim()
    .lines()
    .map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }

fun String.toDigitBoard(): Board = this.lines().map { line -> line.map { it.digitToInt() } }
