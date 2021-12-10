package AoC2021

import utils.readInputAsListOfStrings

private var testInput = listOf(
    "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
    "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
    "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
    "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
    "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
    "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
    "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
    "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
    "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
    "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"
)

private var numberMap = hashMapOf(
    setOf(0, 1, 2, 4, 5, 6) to 0,
    setOf(2, 5) to 1,
    setOf(0, 2, 3, 4, 6) to 2,
    setOf(0, 2, 3, 5, 6) to 3,
    setOf(1, 2, 3, 5) to 4,
    setOf(0, 1, 3, 5, 6) to 5,
    setOf(0, 1, 3, 4, 5, 6) to 6,
    setOf(0, 2, 5) to 7,
    setOf(0, 1, 2, 3, 4, 5, 6) to 8,
    setOf(0, 1, 2, 3, 5, 6) to 9
)

private fun String.prepString(): Pair<List<String>, List<String>> {
    val (numbers, output) = this.split("|")
    val numberList = numbers.trim().split(" ").map { it.trim() }
    val outputList = output.trim().split(" ").map { it.trim() }
    return Pair(numberList, outputList)
}

private fun List<Pair<List<String>, List<String>>>.solve8a(): Int {
    return this.sumOf {
        it.second.filter {
            it.length in listOf(2, 3, 4, 7)
        }.count()
    }
}

private fun Pair<List<String>, List<String>>.unscramble(): HashMap<Int, Char> {
    val unscrambledMap = HashMap<Int, Char>()
    val scrambledList = this.first.plus(this.second)
    val one = scrambledList.firstOrNull { it.length == 2 }!!
    val four = scrambledList.firstOrNull { it.length == 4 }!!
    val seven = scrambledList.firstOrNull { it.length == 3 }!!
    val zeroSixNine = scrambledList.filter { it.length == 6 }
    val twoThreeFive = scrambledList.filter { it.length == 5 }

    unscrambledMap.put(0, seven.filter { !four.contains(it) && !one.contains(it) }.first())
    unscrambledMap.put(5, one.filter { c -> zeroSixNine.all { it.contains(c) } }.first())
    unscrambledMap.put(2, one.filter { it != unscrambledMap[5]!! }.first())

    val five = twoThreeFive.filter { !it.contains(unscrambledMap[2]!!) }.first()
    val threeFive = twoThreeFive.filter { nr -> !nr.all { five.contains(it) }}

    unscrambledMap.put(1, five.filter { c -> threeFive.none { it.contains(c) } }.first())
    unscrambledMap.put(3, four.filter { !unscrambledMap.values.contains(it)}.first())
    unscrambledMap.put(6, five.filter { !unscrambledMap.values.contains(it)}.first())
    unscrambledMap.put(4, "abcdefg".filter { !unscrambledMap.values.contains(it)}.first())
    return unscrambledMap
}

private fun Pair<List<String>, List<String>>.getUnscrambledDigits(): Int {
    val unscrambledMap = this.unscramble()
    val reversedMap = unscrambledMap.entries.associateBy({ it.value }) { it.key }
    val result = this.second.map {numberMap[it.map { reversedMap[it] }.toSet()]}
    var resultAsStringInt = ""
    result.forEach { resultAsStringInt = resultAsStringInt.plus(it)}
    return resultAsStringInt.toInt()
}

private fun List<Pair<List<String>, List<String>>>.solve8b(): Int {
    val result = this.sumOf{it.getUnscrambledDigits()}
    return result
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_8.txt")
    val prepped = input.map { it.prepString() }
    println("Day 8a answer: ${prepped.solve8a()}")
    println("Day 8b answer: ${prepped.solve8b()}")
}
