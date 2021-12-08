package AoC2020

import utils.readInputAsListOfIntegers

private var test_input = listOf(
    16,
    10,
    15,
    5,
    1,
    11,
    7,
    19,
    6,
    12,
    4
)

private var test_input_2 = listOf(
    28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3
)


private fun List<Int>.getDiffs(): List<Int> {
    val sorted = this.sorted()
    val sortedIncludingStartEnd = listOf(0).plus(sorted).plus(sorted.last() + 3)
    return sortedIncludingStartEnd.mapIndexed { index, i -> if (index + 1 < sortedIncludingStartEnd.size) sortedIncludingStartEnd[index + 1] - i else null }
        .filterNotNull();
}

private fun List<Int>.countArrangements(): Int {
    if (this.size <= 1) return 1
    val currentList = this.toMutableList()
    val current = currentList.removeFirst();
    val nextElements = currentList.subList(0, if(currentList.size>=3) 3 else currentList.size).filter { it-current <= 3 }
    return nextElements.map { x-> currentList.dropWhile { it != x }.countArrangements() }.reduce{acc, i ->  acc+i}
}

private fun List<Int>.countArrangements_efficient(from: Int): Long {
//    if (from >= this.size) return 1
//    val result =
//        listOf(1,2,3).sumOf {
//            return@sumOf if (((from + it) < this.size) && (this[from + it] <= 3)) this.countArrangements_efficient(it+from) else  }
//    println("${from}, ${result}")
//    return result;
    return 0L
}

fun Int.factorial(): Long {
    var factorial: Long = 1
    for (curNum in 1..this) {
        factorial *= curNum
    }
    return factorial
}

fun main() {
    val input = readInputAsListOfIntegers("${Constants.INPUT_PATH}input_day_10.txt")
    val groupedDiffList = input.getDiffs().groupBy { it }
//    val result_a = groupedDiffList[1]!!.size * groupedDiffList[3]!!.size
//    println("Day 10a solution: ${result_a}")
    val input_b = listOf(0).plus(test_input_2.sorted()).plus(test_input_2.maxOrNull()!! +3)
    val result_b = input_b.countArrangements_efficient(0)
    println("Day 10b solution: ${result_b}")
}
