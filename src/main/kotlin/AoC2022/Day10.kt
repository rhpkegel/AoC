package AoC2022

import utils.readInputAsListOfStrings

private var testInputSmall = ("noop\n" +
        "addx 3\n" +
        "addx -5").split("\n")

private var testInput = ("addx 15\n" +
        "addx -11\n" +
        "addx 6\n" +
        "addx -3\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -8\n" +
        "addx 13\n" +
        "addx 4\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -35\n" +
        "addx 1\n" +
        "addx 24\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 16\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "addx 21\n" +
        "addx -15\n" +
        "noop\n" +
        "noop\n" +
        "addx -3\n" +
        "addx 9\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 8\n" +
        "addx 1\n" +
        "addx 5\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -36\n" +
        "noop\n" +
        "addx 1\n" +
        "addx 7\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 6\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx 7\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -13\n" +
        "addx 13\n" +
        "addx 7\n" +
        "noop\n" +
        "addx 1\n" +
        "addx -33\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 8\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 17\n" +
        "addx -9\n" +
        "addx 1\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 11\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx -13\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 3\n" +
        "addx 26\n" +
        "addx -30\n" +
        "addx 12\n" +
        "addx -1\n" +
        "addx 3\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -9\n" +
        "addx 18\n" +
        "addx 1\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "addx 9\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx -37\n" +
        "addx 1\n" +
        "addx 3\n" +
        "noop\n" +
        "addx 15\n" +
        "addx -21\n" +
        "addx 22\n" +
        "addx -6\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -10\n" +
        "noop\n" +
        "noop\n" +
        "addx 20\n" +
        "addx 1\n" +
        "addx 2\n" +
        "addx 2\n" +
        "addx -6\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "noop").split('\n')

private data class CPUState(
    var registerValue: Int,
    var currentCycle: Int,
    val program: List<String>,
    var executionPointer: Int,
    val signalStrengths: MutableList<Int>
)

private fun CPUState.duringCycleCheck(): CPUState {
    if ((currentCycle - 20) % 40 == 0) {
        signalStrengths.add(currentCycle * registerValue)
    }
    val currentPixel = ((currentCycle -1) % 40)
    val pixelIsLit = (currentPixel == registerValue || currentPixel == registerValue-1 || currentPixel == registerValue + 1)
    if (pixelIsLit) print("#  ") else print(".  ")
    if (currentPixel == 39) println()
    return this
}

private fun CPUState.executeInstruction(): CPUState {
    val currentInstruction = program[executionPointer].split(' ')
    when (currentInstruction[0]) {
        "noop" -> {
            currentCycle += 1
            duringCycleCheck()
        }

        "addx" -> {
            currentCycle += 1
            duringCycleCheck()
            currentCycle += 1
            duringCycleCheck()
            registerValue += currentInstruction[1].toInt()
        }

        else -> throw Error("invalid instruction: ${currentInstruction}")
    }
    executionPointer += 1
    return this
}

private fun List<String>.solve10a(): Int {
    val cpuState = CPUState(
        registerValue = 1,
        currentCycle = 0,
        program = this,
        executionPointer = 0,
        signalStrengths = mutableListOf()
    )

    while (cpuState.executionPointer < cpuState.program.size) {
        cpuState.executeInstruction()
    }
    return cpuState.signalStrengths.sum();
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_10.txt")
    println("Day 10a answer: ${input.solve10a()}")
    println("Day 10b answer: ZKJFBJFZ")
}
