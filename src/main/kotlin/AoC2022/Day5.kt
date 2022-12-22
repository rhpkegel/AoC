package AoC2022

import utils.readInputAsListOfStrings
import utils.readInputAsString
import java.util.*

private var testInput = ""
private typealias CargoField = Map<Int, Stack<Char>>

private fun String.toCargoField(): CargoField {
    val fieldPerLine = this.split("\n", "\r\n").map { it.chunked(4).map { it.trim() } }
    val cargoField = HashMap<Int, Stack<Char>>()
    fieldPerLine.last().forEach { cargoField.put(it.toInt(), Stack<Char>()) }
    fieldPerLine.reversed().forEach {
        it.forEachIndexed { index, s ->
            if (s.length > 1) {
                cargoField.get(index + 1)?.push(s[1])
            }
        }
    }
    return cargoField
}

private fun String.process9000Instruction(cargoField: CargoField){
    """move (\d+) from (\d+) to (\d+)""".toRegex().matchEntire(this)!!.destructured.let{ (amount,from,to) ->
        repeat(amount.toInt()){
            val cargo = cargoField.get(from.toInt())!!.pop();
            cargoField.get(to.toInt())!!.push(cargo);
        }
    }
}

private fun String.process9001Instruction(cargoField: CargoField){
    """move (\d+) from (\d+) to (\d+)""".toRegex().matchEntire(this)!!.destructured.let{ (amount,from,to) ->
        val crates = mutableListOf<Char>()
        repeat(amount.toInt()){
            val cargo = cargoField.get(from.toInt())!!.pop();
            crates.add(cargo)
        }
        crates.reversed().forEach{char -> cargoField.get(to.toInt())!!.push(char)}
    }
}

private fun CargoField.process9000Instructions(instructionList: List<String>){
    instructionList.forEach{
        it.process9000Instruction(this)
    }
}

private fun CargoField.process9001Instructions(instructionList: List<String>){
    instructionList.forEach{
        it.process9001Instruction(this)
    }
}

private fun List<String>.solve5a(inputCargo: String): String {
    val cargoField = inputCargo.toCargoField()
    cargoField.process9000Instructions(this)
    val result = String(cargoField.map { it.value.pop() }.toCharArray())
    return result;
}

private fun List<String>.solve5b(inputCargo: String): String {
    val cargoField = inputCargo.toCargoField()
    cargoField.process9001Instructions(this)
    return String(cargoField.map { it.value.pop() }.toCharArray())
}

fun main() {
    val input_cargo = readInputAsString("${Constants.INPUT_PATH}input_day_5b.txt")
    val input_instructions = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_5a.txt")
    println("Day 5a answer: ${input_instructions.solve5a(input_cargo)}")
    println("Day 5b answer: ${input_instructions.solve5b(input_cargo)}")
}
