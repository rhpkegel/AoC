package AoC2020

import utils.readInputAsListOfStrings

data class Bag(
    val color: String,
    val contains: List<Pair<Int, String>?>
)

private var test_input = listOf(
    "light red bags contain 1 bright white bag, 2 muted yellow bags.",
    "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
    "bright white bags contain 1 shiny gold bag.",
    "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
    "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
    "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
    "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
    "faded blue bags contain no other bags.",
    "dotted black bags contain no other bags."
);

private var test_input_b = listOf(
    "shiny gold bags contain 2 dark red bags. ",
    "dark red bags contain 2 dark orange bags. ",
    "dark orange bags contain 2 dark yellow bags. ",
    "dark yellow bags contain 2 dark green bags. ",
    "dark green bags contain 2 dark blue bags. ",
    "dark blue bags contain 2 dark violet bags. ",
    "dark violet bags contain no other bags."
)

fun String.convertToBag(): Bag {
    val result = "(.+) bags contain (.+)\\.".toRegex().matchEntire(this.trim())!!.destructured.let { (color, contains) ->
        contains.split(", ")
            .map {
                "(\\d+) (.+) bags?".toRegex().matchEntire(it)?.destructured?.let { (number, subcolor) ->
                    Pair(
                        number.toInt(),
                        subcolor
                    )
                }
            }.let { Bag(color, it) }
    }
    return result
}

fun String.getBagsForColor(bags: List<String>): Set<Bag> {
    val bag_list = bags.map { it.convertToBag() }
    val filtered_bag_list = bag_list.filter {
        val valueList = it.contains.filter {
            it?.second.equals(this)
        }
        valueList.isNotEmpty()
    }

    var results = filtered_bag_list.toSet()
    if (results.isNotEmpty()) filtered_bag_list.forEach { results = results.plus(it.color.getBagsForColor(bags)) }

    return results
}

fun Bag.getBagsInColor(bags: List<Bag>): Int {
    var total: Int = 1;
    contains.forEach { subBag ->
        if (subBag != null) {
            val bag = bags.find { it.color == subBag.second }
            val subtotal = bag?.getBagsInColor(bags) ?: 0
            val toAdd = subBag.first * subtotal
            total += toAdd;
        }
    }
    return total
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_7.txt.txt")
    println("day 7a solution: ${"shiny gold".getBagsForColor(input).size}")
    println("day 7b solution: ${input.find{(it.startsWith("shiny gold"))}!!.convertToBag().getBagsInColor(input.map { it.convertToBag() })-1}"
    )
}
