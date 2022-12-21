package AoC2020

import utils.readAsEmptyLineSeparatedStrings

private var test_input = listOf(
    """abc

a
b
c

ab
ac

a
a
a
a

b"""
);

fun String.getQuestionCount(): Int = replace("\\s".toRegex(), "").toSet().size

fun String.getQuestionCount_b(): Int {
    val individuals = split("\n", "\r\n").filter { !it.isEmpty() }
    val questionSet = replace("\\s".toRegex(), "").toSet();
    val filteredQuestionSet = questionSet.filter { x -> individuals.all { it.contains(x) } }
    println("printing ${individuals},\n questionSet ${questionSet}, \n filtered Set ${filteredQuestionSet}")
    return filteredQuestionSet.size
}

fun List<String>.day_6_a_solution(): Int = map { it.getQuestionCount() }.reduce { acc, i -> acc + i }
fun List<String>.day_6_b_solution(): Int = map { it.getQuestionCount_b() }.reduce { acc, i -> acc + i }

fun main() {
    val input = readAsEmptyLineSeparatedStrings("${Constants.INPUT_PATH}input_day_6.txt")
    println(input.day_6_b_solution())
}
