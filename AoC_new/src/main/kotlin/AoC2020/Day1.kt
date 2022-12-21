package AoC2020

import utils.readInputAsListOfIntegers

private var test_input = listOf(1721, 979, 366, 299, 675, 1456);

fun List<Int>.findBySum(desiredNumber: Int): Pair<Int, Int>? {
    val complements = this.associateBy { desiredNumber - it }
    return firstNotNullOfOrNull {
        if (complements[it] != null) Pair(it, complements[it]!!) else null
    }
}

fun List<Int>.findByTripleSum(desiredNumber: Int): Triple<Int, Int, Int>? {
    return firstNotNullOfOrNull { x ->
        findBySum(desiredNumber - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }
}

fun day_1_a_solution(sumPair: Pair<Int, Int>?): Int? {
    return sumPair?.let { sumPair.first * sumPair.second }
}

fun day_1_b_solution(sumTriple: Triple<Int, Int, Int>?): Int? {
    return sumTriple?.let { sumTriple.first * sumTriple.second * sumTriple.third }
}

fun main() {
    val input = readInputAsListOfIntegers("${Constants.INPUT_PATH}input_day_1.txt")
    println("====day_1_a====")
    println(input.findBySum(2020))
    println(day_1_a_solution(input.findBySum(2020)));
    println("====day_1_b====")
    println(input.findByTripleSum(2020));
    println(day_1_b_solution(input.findByTripleSum(2020)));
}
