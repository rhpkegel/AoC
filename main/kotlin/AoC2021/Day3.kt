package AoC2021

import utils.readInputAsListOfStrings

private var test_input = listOf(
    "00100",
    "11110",
    "10110",
    "10111",
    "10101",
    "01111",
    "00111",
    "11100",
    "10000",
    "11001",
    "00010",
    "01010"
)

private fun List<String>.getBitFrequencyCountList(): List<Int> {
    if (this.first().isEmpty()) return emptyList()
    val elem = this.map { it.first() }.count { it == '1' }
    val elemList = this.map { it.drop(1) }.getBitFrequencyCountList()
    return listOf(elem).plus(elemList)
}

private fun List<String>.getGammaAndEpsilon(): Pair<String, String> {
    val gamma = this.getBitFrequencyCountList().map { (it >= (this.size.toDouble().div(2))).compareTo(false) }.joinToString("")
    val epsilon = gamma.map {if (it=='1') '0' else '1'}.joinToString("")
    return Pair(gamma, epsilon)
}

private fun List<String>.getRating(positionToCheck: Int = 0, oxOrScrub: Char): List<String> {
    if (this.size <= 1) return this
    val filterbit = this.getGammaAndEpsilon().let {(g, e) -> if (oxOrScrub == 'o') g[positionToCheck] else e[positionToCheck]}
    val filteredList = this.filter { it[positionToCheck] == filterbit }
    return filteredList.getRating(positionToCheck + 1, oxOrScrub)
}

private fun List<String>.day_3_a_solution(): Int = this.getGammaAndEpsilon().let { (g, e) -> e.toInt(2) * g.toInt(2) }
private fun List<String>.day_3_b_solution(): Int = this.getRating(oxOrScrub = 'o').first().toInt(2) * this.getRating(oxOrScrub = 's').first().toInt(2)

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_3.txt")
    println("day 3a solution: ${input.day_3_a_solution()}")
    println("day 3b solution: ${input.day_3_b_solution()}")
}
