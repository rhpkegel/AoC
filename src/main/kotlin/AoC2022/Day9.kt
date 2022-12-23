package AoC2022

import utils.Point
import utils.readInputAsListOfStrings
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

private typealias RopeState = Triple<Point, Point, List<Point>>
private data class RopeStateB(
    val head: Point,
    val knots: MutableList<Point>,
    val visitedPoints: List<Point>
)

private var testInput = ("R 4\n" +
        "U 4\n" +
        "L 3\n" +
        "D 1\n" +
        "R 4\n" +
        "D 1\n" +
        "L 5\n" +
        "R 2").split('\n')

private var testInput2 = ("R 5\n" +
        "U 8\n" +
        "L 8\n" +
        "D 3\n" +
        "R 17\n" +
        "D 10\n" +
        "L 25\n" +
        "U 20").split('\n')


private fun String.processInstruction(oldState: RopeState): RopeState {
    var headPos = oldState.first
    var tailPos = oldState.second
    var visitedPoints = oldState.third
    val (direction, amount) = this.split(' ')

    repeat(amount.toInt()) {
        val oldTailPos = tailPos
        when (direction) {
            "U" -> {
                headPos = Point(headPos.first, headPos.second + 1)
            }

            "D" -> {
                headPos = Point(headPos.first, headPos.second - 1)
            }

            "L" -> {
                headPos = Point(headPos.first - 1, headPos.second)
            }

            "R" -> {
                headPos = Point(headPos.first + 1, headPos.second)
            }
        }
        tailPos = tailPos.updatePosition(headPos)

        if (!tailPos.equals(oldTailPos)) {
            visitedPoints = visitedPoints.plus(tailPos)
        }
    }
    return RopeState(headPos, tailPos, visitedPoints)
}

private fun List<Point>.prettyPrint(currentHead: Point? = null, currentTail: Point? = null) {
    val minX = this.minOf { it.first }
    val minY = this.minOf { it.second }
    val normalizedList = this.map { Point(it.first + minX, it.second + minY) }
    val maxX = normalizedList.maxOf { it.first }
    val maxY = normalizedList.maxOf { it.second }
    (maxY + 1 downTo 0).forEach { row ->
        (0 until maxX + 2).forEach { col ->
            if (currentHead == Point(col, row)) {
                print("H")
            } else if (currentTail == Point(col, row)) {
                print("T")
            } else if (row == 0 && col == 0) {
                print("s")
            } else if (normalizedList.contains(Point(col, row))) {
                print("#")
            } else {
                print(".")
            }
        }
        print("\n")
    }
    println("")
}

private fun Point.updatePosition(head: Point): Point {
    if (abs(head.first - this.first) > 1 || abs(head.second - this.second) > 1) {
        val xAsDouble = (head.first - this.first).toDouble().div(2)
        val difX = if (head.first - this.first > 0) ceil(xAsDouble).toInt() else floor(xAsDouble).toInt()
        val newX = this.first + difX
        val yAsDouble = (head.second - this.second).toDouble().div(2)
        val difY = if (head.second - this.second > 0) ceil(yAsDouble).toInt() else floor(yAsDouble).toInt()
        val newY = this.second + difY
        return Point(newX, newY)
    }
    return this
}

private fun List<String>.solve9a(): Int {
    var headPos = Point(0, 0)
    var tailPos = Point(0, 0)
    var visitedPoints = listOf(Point(0, 0))
    this.forEach {
        val newState = it.processInstruction(RopeState(headPos, tailPos, visitedPoints))
        headPos = newState.first
        tailPos = newState.second
        visitedPoints = newState.third
    }
    val distinctPoints = visitedPoints.distinct()
    return distinctPoints.size
}

private fun String.processInstructionB(oldState: RopeStateB): RopeStateB {
    var head = oldState.head
    val knots = oldState.knots
    val oldTailPos = knots.last()
    var visitedPoints = oldState.visitedPoints
    val (direction, amount) = this.split(' ')

    repeat(amount.toInt()) {
        when (direction) {
            "U" -> {
                head = Point(head.first, head.second + 1)
            }

            "D" -> {
                head = Point(head.first, head.second - 1)
            }

            "L" -> {
                head = Point(head.first - 1, head.second)
            }

            "R" -> {
                head = Point(head.first + 1, head.second)
            }
        }
        knots[0] = knots[0].updatePosition(head)
        (1 until knots.size).forEach { index ->
            knots[index] = knots[index].updatePosition(knots[index-1])
        }

        if (oldTailPos != knots.last()) {
            visitedPoints = visitedPoints.plus(knots.last())
        }
    }
    return RopeStateB(head, knots, visitedPoints)
}

private fun List<String>.solve9b(): Int {
    val initialKnots = mutableListOf<Point>()
    val initialHead = Point(0,0)
    val initialVisitedPoints = listOf(Point(0,0))
    repeat(9){initialKnots.add(Point(0,0))}
    var ropeState = RopeStateB(initialHead, initialKnots, initialVisitedPoints)

    this.forEach { instruction ->
        ropeState = instruction.processInstructionB(ropeState)
    }
    return ropeState.visitedPoints.distinct().size
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_9.txt")
    println("Day 9a answer: ${input.solve9a()}")
    println("Day 9b answer: ${input.solve9b()}")
}
