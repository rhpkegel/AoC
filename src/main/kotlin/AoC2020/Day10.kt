package AoC2020

import utils.readInputAsListOfIntegers

private var test_input = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
private var test_input_2 = listOf(
    28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3
)

private fun List<Int>.solve10b(): Long {
    val sortedList = listOf(0).plus(this.sorted())
    var distanceList =
        sortedList.mapIndexed { index, i -> if (index != sortedList.size - 1) sortedList[index + 1] - i else 3 }
    var oneGroups = emptyList<Int>()
    while (distanceList.isNotEmpty()) {
        val consecutiveOnes = distanceList.takeWhile { it != 3 }.count()
        distanceList = distanceList.drop(consecutiveOnes).dropWhile { it == 3 }
        oneGroups = oneGroups.plus(consecutiveOnes)
    }
    return oneGroups.map { it.permutations() }.reduce { acc, l -> acc * l }
}

private fun Int.permutations(): Long =
    when (this) {
        1 -> 1
        2 -> 2
        3 -> 4
        4 -> 7
        else -> 0
    }

fun main() {
    val input = readInputAsListOfIntegers("${Constants.INPUT_PATH}input_day_10.txt")
    println("day 10b solution: ${input.solve10b()}")
}
