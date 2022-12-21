package AoC2021

import utils.readInputAsString
import kotlin.math.abs
import kotlin.math.max

private var testInput = "target area: x=20..30, y=-10..-5\n"

private fun solve17a(yRange: IntRange): Int {
    return (1 until abs(yRange.first)).sum()
}

private fun fire(xrange: IntRange, yrange: IntRange, xVelocity:Int, yVelocity:Int): Boolean{
    var xVelo = xVelocity
    var yVelo = yVelocity
    var xPos = 0
    var yPos = 0
    do {
        xPos += xVelo
        yPos += yVelo
        xVelo = max(xVelo-1, 0)
        yVelo -= 1
    } while(!(xrange.contains(xPos) && yrange.contains(yPos)) && xPos <= xrange.last && yPos >= yrange.first)
    return (xrange.contains(xPos) && yrange.contains(yPos))
}

private fun solve17b(xrange: IntRange, yrange: IntRange): Int {
    val maxY = abs(yrange.first)-1
    return (yrange.first .. maxY).map { y->
        (1..xrange.last).map { x->
            x to y
        }.filter { (xVelo, yVelo) -> fire(xrange, yrange, xVelo, yVelo) }
    }.flatten().count()
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_17.txt")
//    val input = testInput
    val (xMin, xMax) = "x=(\\d+)..(\\d+)".toRegex().find(input)!!.destructured
    val (yMin, yMax) = "y=(-\\d+)..(-\\d+)".toRegex().find(input)!!.destructured
    val xRange = xMin.toInt() .. xMax.toInt()
    val yRange = yMin.toInt() .. yMax.toInt()
    println("Day 17a answer: ${solve17a(yRange)}")
    println("Day 17b answer: ${solve17b(xRange, yRange)}")
}
