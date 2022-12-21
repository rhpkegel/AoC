package AoC2021

import utils.*
import utils.Board
import kotlin.math.min
import kotlin.math.sqrt

private var testInput = """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581"""

private fun Board.solveLikeAnIdiot(): Int {
    val input = this.toValueList().toMap()
    val pathCosts = input.toMutableMap()
    val maxIndex = sqrt(input.values.size.toDouble()).toInt() - 1
    (1..maxIndex * 2).forEach { distance ->
        if (distance <= maxIndex) {
            val coordmax = min(distance, maxIndex)
            (0..coordmax).forEach { x ->
                val pointToCheck = (x to (coordmax - x))
                val leftOf = pathCosts[(pointToCheck.first - 1 to pointToCheck.second)]
                val topOf = pathCosts[(pointToCheck.first to pointToCheck.second - 1)]
                val minPrevCost = listOfNotNull(leftOf, topOf).minOrNull()!!
                pathCosts[pointToCheck] = pathCosts[pointToCheck]!! + minPrevCost
            }
        } else {
            val coordmin = distance - maxIndex
            (coordmin..maxIndex).forEach { x ->
                val pointToCheck = (x to (distance - x))
                val leftOf = pathCosts[(pointToCheck.first - 1 to pointToCheck.second)]
                val topOf = pathCosts[(pointToCheck.first to pointToCheck.second - 1)]
                val minPrevCost = listOfNotNull(leftOf, topOf).minOrNull()!!
                pathCosts[pointToCheck] = pathCosts[pointToCheck]!! + minPrevCost
            }
        }
    }
    return pathCosts[maxIndex to maxIndex]!! - pathCosts[0 to 0]!!
}


private fun String.getMegaBoard(): Board {
    val input = this.toDigitBoard().toValueList().toMap()
    val output = input.toMutableMap()
    val oldSize = sqrt(input.values.size.toDouble()).toInt()
    ((0 until (oldSize * 5))).forEach { y ->
        ((0 until (oldSize * 5))).forEach { x ->
            val increase = (y / (oldSize)) + (x / (oldSize))
            val newPoint = x to y
            val oldPoint = x % oldSize to y % oldSize
            val oldValue = input[oldPoint]!!
            var newValue = oldValue + increase
            if (newValue > 9) newValue %= 9
            output[newPoint] = newValue
        }
    }
    return output.toList().toBoard()
}


private fun Board.solveWithDijkstra(): Int {
    val nodeMap = this.toValueList().toMap()
    val tentativeNodes = HashMap<Point, Int>()
    val visitedNodes = HashMap<Point, Int>()
    val rootNode = 0 to 0
    rootNode
        .getAdjacentNumbers(this)
        .filter { it.second != null }
        .forEach { tentativeNodes[it.first] = it.second!! }
    visitedNodes[rootNode] = 0
    do {
        val (node, visitingNodeValue) = tentativeNodes.entries.first()
        tentativeNodes.remove(node)
        if (visitedNodes[node] != null) continue
        visitedNodes[node] = visitingNodeValue

        val neighbours = node.getAdjacentNumbers(this)
            .filter { it.second != null }
            //bereken neighbour tentative waardes
            .map { it.first to it.second!! + visitingNodeValue }
            //zit niet al in tentative set met lagere waarde
            .filter { !(tentativeNodes[it.first] != null && tentativeNodes[it.first]!! < it.second) }

        neighbours.forEach {
            //update visitedNodes to lower cost if applicable
                neighbour ->
            if (visitedNodes[neighbour.first] != null && visitedNodes[neighbour.first]!! > neighbour.second) {
                visitedNodes.remove(neighbour.first)
            }
            //add to tentative set
            tentativeNodes[neighbour.first] = neighbour.second
        }
        print("Calculating board with Dijkstra... ${String.format("%.1f", (visitedNodes.size.toDouble()/nodeMap.size.toDouble())*100)}% complete\r")
    } while (tentativeNodes.isNotEmpty())
    val finalEntry = visitedNodes.toMap().maxByOrNull { it.key.first + it.key.second }!!
    return finalEntry.value
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_15.txt")
    println("Day 15a answer: ${input.toDigitBoard().solveLikeAnIdiot()}")
    println("Day 15b answer: ${input.getMegaBoard().solveWithDijkstra()}")
}
