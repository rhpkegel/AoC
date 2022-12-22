package AoC2022

import utils.readInputAsListOfStrings

private var testInput = ("vJrwpWtwJgWrhcsFMMfFFhFp\n" +
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
        "PmmdzqPrVvPwwTWBwg\n" +
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
        "ttgJtRGJQctTZtZT\n" +
        "CrZsJsPPZsGzwwsLwLmpwMDw").split('\n')

private fun Char.itemPriority(): Int {
    val result = if (this.isLowerCase()) this.code - 96 else this.code - 64 + 26
    return result
}

private fun List<String>.solve3a(): Int {
    val asCompartments = this.map { it.chunked(it.length / 2) }
    val intersectingCharacters = asCompartments.map { it[0].toSet().intersect(it[1].toSet()).first() }
    return intersectingCharacters.map {it.itemPriority()}.sum()
}

private fun List<String>.solve3b(): Int {
    val groupsOfElves = this.chunked(3);
    val authenticityBadges = groupsOfElves.map{it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet())}
    if (authenticityBadges.any{it.size != 1}){
        throw Error("failure to find authenticity badges in some sets.")
    }
    return authenticityBadges.map{it.first().itemPriority()}.sum()
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_3.txt")
    println("Day 3a answer: ${input.solve3a()}")
    println("Day 3b answer: ${input.solve3b()}")
}
