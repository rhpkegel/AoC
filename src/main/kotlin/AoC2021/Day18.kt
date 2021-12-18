package AoC2021

import utils.readInputAsString
import kotlin.math.ceil
import kotlin.math.floor

typealias DepthList = MutableList<Int>
typealias NumberList = MutableList<Int>
typealias SnailNumber = Pair<NumberList, DepthList>

private var testInput = """[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"""

private var magnitudeTestInput = "[[1,2],[[3,4],5]]"

private fun String.solve18a(): Int {
    return this.trim()
        .lines()
        .map{it.parseSnailNumber()}
        .reduce{acc, snailNumber -> acc.addAndProcess(snailNumber)}
        .magnitude()
}

private fun SnailNumber.addAndProcess(other: SnailNumber): SnailNumber{
    var number = this.add(other)
    do {
        while (number.second.any{it > 4}) {
            number = number.explode()
        }
        if (number.first.any { it > 9 }) {
            number = number.split()
        }
    } while (number.second.any{it > 4} || number.first.any { it > 9 })
    return number
}

private fun String.parseSnailNumber(): SnailNumber{
    val numberList = listOf<Int>().toMutableList()
    val depthList = listOf<Int>().toMutableList()
    var currentDepth = 0
    this.forEach {
        if (it == '[') currentDepth += 1
        if (it == ']') currentDepth -= 1
        if (it.isDigit()){
            numberList.add(it.digitToInt())
            depthList.add(currentDepth)
        }
    }
    return numberList to depthList
}

private fun SnailNumber.add(other: SnailNumber): SnailNumber{
    val newDepthsLeft = this.second.map{it + 1}
    val newDepthsRight = other.second.map{it + 1}
    return this.first.plus(other.first).toMutableList() to newDepthsLeft.plus(newDepthsRight).toMutableList()
}

private fun SnailNumber.explode(): SnailNumber {
    val toExplodeIndex = this.second.indexOfFirst { it>4 }
    if (toExplodeIndex == -1) return this

    //remove exploded elements and replace with 4
    this.second.removeAt(toExplodeIndex)
    this.second.removeAt(toExplodeIndex)
    this.second.add(toExplodeIndex, 4)

    //attempt to add left element
    if(toExplodeIndex > 0)
    this.first[toExplodeIndex-1] += this.first[toExplodeIndex]

    //attempt to add right element
    if (toExplodeIndex+1 < this.first.size-1)
    this.first[toExplodeIndex + 2] += this.first[toExplodeIndex+1]

    //remove elements from list and replace with 0
    this.first.removeAt(toExplodeIndex)
    this.first.removeAt(toExplodeIndex)
    this.first.add(toExplodeIndex, 0)
    return this.first.toMutableList() to this.second.toMutableList()
}

private fun SnailNumber.split(): SnailNumber {
    val toSplitIndex = this.first.indexOfFirst { it > 9 }
    if (toSplitIndex == -1) return this
    //add depth and second number at index
    this.second[toSplitIndex] += 1
    this.second.add(toSplitIndex, this.second[toSplitIndex])

    //remove element and add two new ones
    val oldNumber = this.first[toSplitIndex]
    this.first.removeAt(toSplitIndex)
    val leftNumber = ceil(oldNumber.toDouble() / 2).toInt()
    val rightNumber = floor(oldNumber.toDouble() / 2).toInt()
    this.first.add(toSplitIndex, leftNumber)
    this.first.add(toSplitIndex, rightNumber)

    return this.first.toMutableList() to this.second.toMutableList()
}

private fun SnailNumber.magnitude(): Int {
    var highestIndex = this.second.maxOrNull()!!
    while (this.second[highestIndex] > 0) {
        val firstHighest = this.second.indexOf(highestIndex)
        //getMagNumber
        val magNumber = 3 * this.first[firstHighest] + 2 * this.first[firstHighest + 1]

        //replace
        this.first.removeAt(firstHighest)
        this.first.removeAt(firstHighest)
        this.second.removeAt(firstHighest)
        this.second[firstHighest] -= 1
        this.first.add(firstHighest, magNumber)
        highestIndex = this.second.maxOrNull()!!
    }
    return this.first[0]
}

private fun String.solve18b(): Int{
    val numberList = this.trim()
        .lines()
        .map { it.parseSnailNumber() }

    val result = numberList.map { number ->
        numberList.minus(number).maxOf { other -> number.addAndProcess(other).magnitude() }
    }.maxOrNull()!!

    return result
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_18.txt")
    println("Day 18a answer: ${input.solve18a()}")
    println("Day 18b answer: ${input.solve18b()}")
}
