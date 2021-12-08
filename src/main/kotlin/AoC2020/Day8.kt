package AoC2020

import utils.readInputAsListOfStrings

private var test_input = listOf(
    "nop +0",
    "acc +1",
    "jmp +4",
    "acc +3",
    "jmp -3",
    "acc -99",
    "acc +1",
    "jmp -4",
    "acc +6"
)

fun List<String>.runProgramUntilLoopOrTerminate(acc: Int, cursor: Int, runInstructions: Set<Int>): Pair<Int, Int> {
        if (runInstructions.contains(cursor)){
                return Pair(acc, 1)
        }
        if (cursor >= this.size){
                return Pair(acc, 0)
        }
        else {
                val instruction = this[cursor].split(" ");
            println("processing ${instruction} at cursor ${cursor}")
                return this.runProgramUntilLoopOrTerminate(
                        if (instruction[0] == "acc") acc +instruction[1].toInt() else acc,
                        if (instruction[0] == "jmp") cursor+instruction[1].toInt() else cursor +1,
                        runInstructions.plus(cursor)
                )
        }
}


fun List<String>.findTerminatingInstruction(): Pair<Int, Int>? {
    this.indices.forEach{
        if (this[it].split(" ")[0] != "acc"){
            val changedProgram = this.toMutableList();
            val instructionToChange = changedProgram[it]
            val changedInstruction ="${if (instructionToChange.split(" ")[0] == "jmp") changedProgram[it] = "acc" else changedProgram[it] = "jmp"} ${instructionToChange.split(" ")[1]}"
            changedProgram[it] = changedInstruction
            val result = changedProgram.runProgramUntilLoopOrTerminate(0, 0, emptySet())
            if (result.second == 0) return Pair(it, result.first)
        }
    }
    return null
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_8.txt")
    println("Day 8a: program looped with: ${input.runProgramUntilLoopOrTerminate(0,0, emptySet())}")
    println("Day 8b: program terminated with altered instructions: ${input.findTerminatingInstruction()}")
}
