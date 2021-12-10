package AoC2021

import utils.readInputAsListOfStrings

private var test_input = listOf(
    "forward 5",
    "down 5",
    "forward 8",
    "up 3",
    "down 8",
    "forward 2"
)

private fun List<String>.solve2A(): Int {
    val forward = this.filter { it.startsWith('f') }.sumOf { it.split(' ')[1].toInt() }
    val down = this.filter { !it.startsWith('f') }
        .sumOf { if (it.startsWith('d')) it.split(' ')[1].toInt() else ("-" + it.split(' ')[1]).toInt() }
    return forward * down
}

private fun List<String>.solve2B(): Int {
    var forward = 0
    var depth = 0
    var aim = 0
    this.forEach {
        val instructionValue = it.split(' ')[1].toInt()
        when (it) {
            "forward" -> {
                forward += instructionValue
                depth += instructionValue * aim
            }
            "up" -> aim -= instructionValue
            "down" -> aim += instructionValue
        }
    }
    return forward * depth
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_2.txt")
    println("day 2a solution: ${input.solve2A()}")
    println("day 2b solution: ${input.solve2B()}")
}
