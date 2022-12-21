package AoC2021

import utils.readInputAsString

private var testInput = """[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]"""

private fun Char.getOpposite(): Char? {
    return when (this) {
        '>' -> '<'
        ')' -> '('
        '}' -> '{'
        ']' -> '['
        else -> null
    }
}

private fun Char.getInvalidValue(): Int {
    return when (this) {
        '>' -> 25137
        ')' -> 3
        '}' -> 1197
        ']' -> 57
        else -> 0
    }
}

private fun Char.getAutocompleteValue(): Long {
    return when (this) {
        '<' -> 4
        '(' -> 1
        '{' -> 3
        '[' -> 2
        else -> 0
    }
}

private fun String.getInvalidCharacter(): Char? {
    var scopesToClose = ""
    this.forEach {
        val opposite = it.getOpposite()
        if (opposite != null && scopesToClose.last() == opposite) {
            scopesToClose = scopesToClose.dropLast(1)
        } else if (opposite != null && scopesToClose.last() != opposite) {
            return it
        } else {
            scopesToClose = scopesToClose.plus(it)
        }
    }
    return null
}

private fun String.getAutoCompleteSequence(): String {
    var changed = true
    var result = this
    while (changed) {
        val newResult = result.replace("""<>|\[]|\(\)|\{}""".toRegex(), "")
        if (result == newResult) {
            changed = false
        } else {
            result = newResult
        }
    }
    return result
}

private fun String.solve10a(): Int =
    this.lines()
        .mapNotNull { it.getInvalidCharacter() }
        .sumOf { it.getInvalidValue() }

private fun String.solve10b(): Long {
    val incompleteLines = this.lines()
        .filter { it.getInvalidCharacter() == null }
    val values = incompleteLines
        .map { line ->
            line.getAutoCompleteSequence()
                .map { it.getAutocompleteValue() }
                .reversed()
                .let {
                    if (it.isNotEmpty()) it.reduce { acc, i -> acc * 5 + i }
                    else 0
                }
        }
    val sortedValues = values.sorted()
    return sortedValues[sortedValues.size / 2 + 1]
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}/input_day_10.txt")
    println("Day 10a answer: ${input.solve10a()}")
    println("Day 10b answer: ${input.solve10b()}")
}
