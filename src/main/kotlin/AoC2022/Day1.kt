package AoC2022

import utils.readAsEmptyLineSeparatedStrings

private var testInput = ""
private typealias ElfInventory = List<Int>
private typealias ElfInventories = List<ElfInventory>

private fun String.toElfInventory(): ElfInventory {
    return this.split("\n", "\r\n").map{it.toInt()}
}

private fun List<String>.solve1a(): String {
    val elfInventories = this.map{it.toElfInventory()}
    val mostCalories = elfInventories.map{it.sum()}.max()
    return mostCalories.toString()
}

private fun List<String>.solve1b(): String{
    val elfInventories = this.map{it.toElfInventory()}
    val top3Calories = elfInventories.map{it.sum()}.sortedDescending().subList(0,3)
    return top3Calories.sum().toString()
}

fun main() {
    val input = readAsEmptyLineSeparatedStrings("${Constants.INPUT_PATH}input_day_1.txt");
    println("Day 1a answer: ${input.solve1a()}")
    println("Day 1b answer: ${input.solve1b()}")
}
