package AoC2021

import utils.readInputAsString

private var test_input =
    """3,4,3,1,2""".split(",").map { it.toInt() }

private fun initBuckets(init: List<Int>): HashMap<Int, Long> {
    val buckets = HashMap<Int, Long>()
    (0..8).forEach { buckets.put(it, 0) }
    init.forEach { buckets.put(it, buckets.get(it)!! + 1) }
    return buckets
}

private fun HashMap<Int, Long>.updateBuckets(): HashMap<Int, Long> {
    val newBuckets = HashMap<Int, Long>()
    (1..8).forEach { newBuckets.put(it - 1, this.get(it)!!) }
    newBuckets.put(6, newBuckets.get(6)!! + this.get(0)!!)
    newBuckets.put(8, this.get(0)!!)
    return newBuckets
}

private fun List<Int>.solve(days: Int): Long{
    var buckets = initBuckets(this)
    repeat(days) { buckets = buckets.updateBuckets() }
    return buckets.values.sum()
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_6.txt").split(",").map{it.trim().toInt()}
    println("Day 6a solution: ${input.solve(80)}")
    println("Day 6b solution: ${input.solve(256)}")
}
