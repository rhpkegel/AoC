package AoC2021

import utils.readInputAsString

private var testInput = """dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"""

private var testInput2 = """fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"""

private fun String.prepConnections(): HashMap<String, List<String>> {
    val result = HashMap<String, List<String>>()
    this.trim().lines().forEach {
        val (start, end) = it.split("-")
        if (!result.containsKey(start)){
            result[start] = listOf(end)
        } else {
            result[start] = result[start]!!.plus(end)
        }
        if (!result.containsKey(end)) {
            result[end] = listOf(start)
        } else {
            result[end] = result[end]!!.plus(start)
        }
    }
    return result
}

private fun HashMap<String, List<String>>.traverse(previous: List<String> = listOf("start")): List<List<String>> {
    val currentNode = previous.last()
    if (currentNode == "end") return listOf(previous)
    val destinations = this[currentNode]!!.filter { !(it.all {it.isLowerCase()} && previous.contains(it)) }
    if (destinations.isEmpty()) return emptyList()
    return destinations.map {this.traverse(previous.plus(it))}.flatten()
}

private fun HashMap<String, List<String>>.traverseWithSingleReturn(previous: List<String> = listOf("start"), returnedToSmall: Boolean = false): List<List<String>> {
    val currentNode = previous.last()
    if (currentNode == "end") return listOf(previous)
    val destinations = this[currentNode]!!.filter { it!="start" && (!(it.all { it.isLowerCase() } && previous.contains(it)) || it == "end" || !returnedToSmall) }
    val routes = destinations.map {
        if ((it.all { it.isLowerCase() } && previous.contains(it)) || returnedToSmall) {
            this.traverseWithSingleReturn(previous.plus(it), true)
        } else {
            this.traverseWithSingleReturn(previous.plus(it), false)
        }
    }.flatten()
    return routes
}

private fun String.solve12a(): Int {
    val routes = this.prepConnections()
    return routes.traverse().count()
}

private fun String.solve12b(): Int {
    val routes = this.prepConnections()
    return routes.traverseWithSingleReturn().count()
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_12.txt")
    println("Day 12a answer: ${input.solve12a()}")
    println("Day 12b answer: ${input.solve12b()}")
}
