package AoC2021

import utils.readInputAsString
import utils.readInputAsString

typealias PolymerInput = Pair<String, InsertionRuleMap>
typealias InsertionRuleMap = Map<String, Char>

private var testInput = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"""

private fun String.parseInput(): Pair<String, InsertionRuleMap> {
    val (input, insertionRules) = this.split("\n\n", "\r\n\r\n")
    val insertionRuleMap = insertionRules.trim().lines().map {
        val split = it.split(" -> ")
        split[0] to split[1].last()
    }.toMap()
    return input to insertionRuleMap
}

private fun PolymerInput.insert(): String {
    val (polymer, rules) = this
    var result = polymer
    polymer.forEachIndexed { index, c ->
        if (index < polymer.length - 1) {
            val secondChar = polymer[index + 1]
            val charToInsert = rules["$c$secondChar"]!!
            result = StringBuilder(result).insert(index * 2 + 1, charToInsert).toString()
        }
    }
    return result
}

private fun String.solve14a(): Int {
    val (polymer, rules) = this.parseInput()
    var result = polymer
    repeat(10) {result = (result to rules).insert()}
    val countedResult = result.groupingBy { it }.eachCount().values
    return countedResult.maxOrNull()!! - countedResult.minOrNull()!!
}

private fun String.solve14b(): Long {
    val (polymer, rules) = this.parseInput()
    var buckets = rules.map { it.key to 0L}.toMap().toMutableMap()
    var lastPair = polymer.takeLast(2)
    polymer.forEachIndexed { index, c ->
        if (index < polymer.length - 1) {
            val secondChar = polymer[index + 1]
            if (buckets["$c$secondChar"] == null) buckets["$c$secondChar"] = 1L else buckets["$c$secondChar"] = buckets["$c$secondChar"]!! + 1
        } }
    repeat(40){
        val newBuckets = buckets.toMutableMap()
        lastPair = "${rules[lastPair]!!}${lastPair.last()}"
        buckets.forEach{entry ->
            val charToInsert = rules[entry.key]!!
            val firstBucket = "${entry.key.first()}$charToInsert"
            val secondBucket = "$charToInsert${entry.key.last()}"
            newBuckets[entry.key] = newBuckets[entry.key]!! - buckets[entry.key]!!
            newBuckets[firstBucket] = newBuckets[firstBucket]!! + entry.value
            newBuckets[secondBucket] = newBuckets[secondBucket]!! + entry.value
        }
        buckets = newBuckets
    }

    val finalMap = HashMap<Char, Long>()
    buckets.forEach { entry ->
        val charToInsert = entry.key.first()
        if (finalMap[charToInsert] != null) finalMap[charToInsert] = finalMap[charToInsert]!! + entry.value
        else finalMap[charToInsert] = entry.value
    }
    finalMap[lastPair.last()] = finalMap[lastPair.last()]!! + 1
    return finalMap.maxByOrNull { it.value }!!.value - finalMap.minByOrNull { it.value }!!.value
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_14.txt")
    println("Day 14a answer: ${input.solve14a()}")
    println("Day 14b answer: ${input.solve14b()}")
}
