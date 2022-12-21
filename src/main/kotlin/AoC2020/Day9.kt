package AoC2020

import utils.readInputAsListOfStrings

private var test_input = listOf(
    35,
    20,
    15,
    25,
    47,
    40,
    62,
    55,
    65,
    95,
    102,
    117,
    150,
    182,
    127,
    219,
    299,
    277,
    309,
    576
)

private fun Long.getValidSum(numberMap: List<Long>): Pair<Long, Long>?{
    val complements = numberMap.associateBy { this - it }
    val result = complements.firstNotNullOfOrNull { if (numberMap.contains(it.key) && it.key != it.value) Pair(it.key, it.value) else null }
    return result;
}


private fun List<Long>.getFirstInvalidSum(preamble_length: Int): Long?{
    if(this.size <= preamble_length) return null
    val current = this[preamble_length];
    val new = current.getValidSum(this.subList(0, preamble_length))
    if (new != null) {
        val newList = this.toMutableList()
        newList.removeFirst()
        return newList.getFirstInvalidSum(preamble_length)
    } else {
        return current
    }
}

private fun List<Long>.findContiguousSum(sumTarget: Long, currentRange: IntRange = 0..1): List<Long>?{
    if (currentRange.last >= this.size) return null
    val currentNumber = this.subList(currentRange.first, currentRange.last).reduce{a,b -> a+b}
    if (currentNumber < sumTarget){
        return this.findContiguousSum(sumTarget, currentRange.first..currentRange.last+1)
    } else if (currentNumber > sumTarget){
        return this.findContiguousSum(sumTarget, currentRange.first+1..currentRange.last)
    } else return subList(currentRange.first, currentRange.last);
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_9.txt").map {it.toLong()}
    val result_a = input.getFirstInvalidSum(25)
    println("day 9a answer: ${result_a}")
    val result_b = input.findContiguousSum(result_a!!)
    print("day 9b answer: ${result_b!!.minOrNull()!! + result_b.maxOrNull()!!}")
}
