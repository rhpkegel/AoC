package AoC2020

import utils.readInputAsListOfStrings

private var test_input = listOf(
    "..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#"
);

fun Pair<Int,Int>.collision(slopeMap: List<String>): Boolean = slopeMap[second][first.mod(slopeMap[0].length)] == '#'

fun Pair<Int,Int>.collisionsAtSlope(slopeMap: List<String>): Int {
        var xCounter = 0;
        var yCounter = 0;
        var collisions = 0;
        while(yCounter < slopeMap.size){
                if (Pair(xCounter, yCounter).collision(slopeMap)) collisions++;
                xCounter+=first;
                yCounter+=second;
        }
        return collisions
}

fun day_3_b_solution(input: List<String>): Int{
        var slopesToCheck = listOf(3 to 1, Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
        return slopesToCheck.map{it.collisionsAtSlope(input)}.reduce{acc, i ->  acc*i}
}


fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_3.txt")
//        println(Pair(3,1).collisionsAtSlope(input))
        println(day_3_b_solution(input))
}
