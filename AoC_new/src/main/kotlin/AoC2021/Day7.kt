package AoC2021

import utils.readInputAsString
import kotlin.math.absoluteValue

private var test_input = "16, 1, 2, 0, 4, 2, 7, 1, 2, 14".split(", ").map { it.toInt() }

private fun List<Int>.solve7a(): Pair<Int, Int>? {
    if(this.isEmpty()) return null
    val minRange = this.minOrNull()!!
    val maxRange = this.maxOrNull()!!
    return (minRange..maxRange).map { x -> Pair(x, (this.map { (it - x).absoluteValue }.sum())) }.minByOrNull { it.second }!!
}

private fun List<Int>.solve7b(): Pair<Int, Long>? {
    if (this.isEmpty()) return null
    val minRange = this.minOrNull()!!
    val maxRange = this.maxOrNull()!!
    val results =
        (minRange..maxRange)
            .map { x -> Pair(x, (this.map i@{
                val totalDistance = (it - x).absoluteValue
                val fuelCost = (totalDistance.toDouble()/2)*(totalDistance+1)
                return@i fuelCost.toLong()
            }.sum())) }
            .minByOrNull { it.second }!!
    return results
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_7.txt").split(",").map { it.trim().toInt() }
    println("Day 7a output solution: ${input.solve7a()}")
    println("Day 7b output solution: ${input.solve7b()}")
}
