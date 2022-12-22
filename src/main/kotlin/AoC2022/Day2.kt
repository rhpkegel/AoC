package AoC2022

import utils.readInputAsListOfStrings

private val testInput: List<String> = ("A Y\n" +
        "B X\n" +
        "C Z").split('\n')

enum class RPS {ROCK, PAPER, SCISSORS}
typealias RPSRound = Pair<RPS, RPS>

private fun String.toRPS(): RPS {
    return when(this) {
        "A", "X" -> RPS.ROCK
        "B", "Y" -> RPS.PAPER
        "C", "Z" -> RPS.SCISSORS
        else -> throw Error("invalid input for RPS: ${this}")
    }
}

private fun String.toRPSRound(): RPSRound {
    val rpsList = this.split(' ').map { it.toRPS() }
    if (rpsList.size != 2) throw Error("encountered a malformed RPS round: ${this}")
    return Pair(rpsList[0], rpsList[1])
}
private fun RPSRound.toPoints(): Int {
    val choicePoints = when(this.second){
        RPS.ROCK -> 1
        RPS.PAPER -> 2
        RPS.SCISSORS -> 3
    }

    val player2HasWon = (this.equals(Pair(RPS.SCISSORS, RPS.ROCK)) || this.equals(Pair(RPS.PAPER, RPS.SCISSORS)) || this.equals(Pair(RPS.ROCK, RPS.PAPER)))
    val draw = this.first.equals(this.second)
    if (player2HasWon) return 6 + choicePoints
    if (draw) return 3 + choicePoints
    return 0 + choicePoints
}

private fun RPS.getLoss(): RPS {
    return when(this){
        RPS.ROCK -> RPS.SCISSORS
        RPS.PAPER -> RPS.ROCK
        RPS.SCISSORS -> RPS.PAPER
    }
}

private fun RPS.getWin(): RPS {
    return when(this){
        RPS.ROCK -> RPS.PAPER
        RPS.PAPER -> RPS.SCISSORS
        RPS.SCISSORS -> RPS.ROCK
    }
}
private fun RPSRound.toPointsB(): Int {
    val moveToPlay = when(this.second){
        RPS.ROCK -> this.first.getLoss()
        RPS.PAPER -> this.first
        RPS.SCISSORS -> this.first.getWin()
    }

    val choicePoints = when(moveToPlay){
        RPS.ROCK -> 1
        RPS.PAPER -> 2
        RPS.SCISSORS -> 3
    }

    if (this.second.equals(RPS.SCISSORS)) return 6 + choicePoints
    if (this.second.equals(RPS.PAPER)) return 3 + choicePoints
    return choicePoints
}
private fun List<String>.solve2a(): Int {
    return this.map{it.toRPSRound().toPoints()}.sum()
}
private fun List<String>.solve2b(): Int {
    return this.map{it.toRPSRound().toPointsB()}.sum()
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_2.txt")
//    println("Day 2a answer: ${input.solve2a()}")
    println("Day 2b answer: ${input.solve2b()}")
}
