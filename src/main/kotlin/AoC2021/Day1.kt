package AoC2021

import utils.readInputAsListOfIntegers

private var test_input = listOf(
199,
200,
208,
210,
200,
207,
240,
269,
260,
263
)

private fun List<Int>.solve1A(): Int = this.windowed(2, 1, false).count { (a,b) -> a < b }
private fun List<Int>.solve1B(): Int = this.windowed(3, 1, false).map{it.sum()}.solve1A()

fun main() {
    val input = readInputAsListOfIntegers("${Constants.INPUT_PATH}input_day_1.txt")
    println ("Day 1a solution: ${input.solve1A()}")
    println("Day 1b solution: ${input.solve1B()}")
}
