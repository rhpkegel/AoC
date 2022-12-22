package AoC2020

import utils.readInputAsListOfStrings
import utils.readInputAsString

private var test_input = listOf(
    "BFFFBBFRRR",
    "FFFBBBFRRR",
    "BBFFBBFRLL"
);

fun String.convertToSeat(): Pair<Int, Int> {
    var binaryString = this.replace("B", "1")
        .replace("F", "0")
        .replace("R", "1")
        .replace("L", "0")
    return binaryString.take(7).toInt(2) to binaryString.takeLast(3).toInt(2)
}

fun Pair<Int, Int>.seatId(): Int =
    first * 8 + second

fun List<String>.day_5_a_solution(): Int? = map { it.convertToSeat().seatId() }.maxOrNull()

fun List<String>.missingSeatIds(): List<Int> {
    val incompleteRows = map {it.convertToSeat()}.groupBy { it -> it.first }.filter { it -> it.value.size != 8 }
    val occupiedSeats = incompleteRows.flatMap { it.value }
    val seatRange = 0..7
    return incompleteRows.keys.flatMap { seatRow -> seatRange.map {Pair(seatRow, it)} }.filter { !occupiedSeats.contains(it) }.map{it.seatId()}
}

fun List<String>.day_5_b_solution(): List<Int> {
    val seatIds = map{it.convertToSeat().seatId()}
    val missingSeats = missingSeatIds()
    return missingSeatIds().filter { seatIds.contains(it-1) && seatIds.contains(it+1) }
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_5b.txt.txt")
    println(input.day_5_a_solution())
    println(input.day_5_b_solution())
}
