package AoC2020

import utils.readInputAsListOfStrings

data class PasswordLine(
    val min: Int,
    val max: Int,
    val char: Char,
    val password: String)

private var test_input = listOf(
    "1-3 a: abcde",
    "1-3 b: cdefg",
    "2-9 c: ccccccccc"
);

fun String.convertToRule(): PasswordLine = """(\d+)-(\d+) (.): (.+)"""
    .toRegex()
    .matchEntire(this)!!
    .destructured
    .let {(min,max,char,password) ->
        PasswordLine(min.toInt(),max.toInt(),char[0],password)}


fun PasswordLine.isCompliant_a(): Boolean = min <= password.count { it==char } && password.count { it == char } <= max


fun PasswordLine.isCompliant_b(): Boolean = (password[min-1]==char) xor (password[max-1]==char)


fun List<String>.day_2_a_solution(): Int{
    val results = map{ it.convertToRule().isCompliant_a()}
    return results.count {it}
}

fun List<String>.day_2_b_solution(): Int {
    val results = map { it.convertToRule().isCompliant_b() }
    return results.count { it }
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_2.txt")
    println(input.day_2_a_solution());
    println(input.day_2_b_solution());
}
