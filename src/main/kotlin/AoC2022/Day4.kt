package AoC2022

import utils.readInputAsListOfStrings

private var testInput = ("2-4,6-8\n" +
        "2-3,4-5\n" +
        "5-7,7-9\n" +
        "2-8,3-7\n" +
        "6-6,4-6\n" +
        "2-6,4-8").split('\n')

private fun String.hasCompleteOverlap(): Boolean {
    val elfList = this.split(',').map{
        val rangeBounds = it.split('-').map{it.toInt()}
        IntRange(rangeBounds[0], rangeBounds[1])
    }
    val intersectionList = elfList[0].intersect(elfList[1])
    return intersectionList.size == elfList[0].count() || intersectionList.size == elfList[1].count();
}

private fun String.hasPartialOverlap(): Boolean {
    val elfList = this.split(',').map{
        val rangeBounds = it.split('-').map{it.toInt()}
        IntRange(rangeBounds[0], rangeBounds[1])
    }
    val intersectionList = elfList[0].intersect(elfList[1])
    return intersectionList.size > 0;
}
private fun List<String>.solve4a(): Int {
    val overlapList = this.map{it.hasCompleteOverlap()}
    return overlapList.filter { it }.size
}

private fun List<String>.solve4b(): Int {
    val overlapList = this.map{it.hasPartialOverlap()}
    return overlapList.filter { it }.size
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_4.txt")
    println("Day 4a answer: ${input.solve4a()}")
    println("Day 4b answer: ${input.solve4b()}")
}
