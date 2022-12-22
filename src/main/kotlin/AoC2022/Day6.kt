package AoC2022

import utils.readInputAsString

private var testInput = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"

private fun String.solve6a(): Int {
    val values = this.windowed(4,1)
    val firstDistinctWindow = values.indexOfFirst{ window -> window.toSet().size == 4}
    return firstDistinctWindow+4
}

private fun String.solve6b(): Int {
    val values = this.windowed(14,1)
    val firstDistinctWindow = values.indexOfFirst{ window -> window.toSet().size == 14}
    return firstDistinctWindow+14
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_6.txt")
    println("Day 6a answer: ${input.solve6a()}")
    println("Day 6b answer: ${input.solve6b()}")
}
