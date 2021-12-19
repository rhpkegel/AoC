package AoC2021

import utils.readInputAsString
import kotlin.math.abs

typealias Beacon = List<Int>
typealias BeaconList = List<List<Int>>
typealias ScannerList = List<BeaconList>

private var testInput = """--- scanner 0 ---
404,-588,-901
528,-643,409
-838,591,734
390,-675,-793
-537,-823,-458
-485,-357,347
-345,-311,381
-661,-816,-575
-876,649,763
-618,-824,-621
553,345,-567
474,580,667
-447,-329,318
-584,868,-557
544,-627,-890
564,392,-477
455,729,728
-892,524,684
-689,845,-530
423,-701,434
7,-33,-71
630,319,-379
443,580,662
-789,900,-551
459,-707,401

--- scanner 1 ---
686,422,578
605,423,415
515,917,-361
-336,658,858
95,138,22
-476,619,847
-340,-569,-846
567,-361,727
-460,603,-452
669,-402,600
729,430,532
-500,-761,534
-322,571,750
-466,-666,-811
-429,-592,574
-355,545,-477
703,-491,-529
-328,-685,520
413,935,-424
-391,539,-444
586,-435,557
-364,-763,-893
807,-499,-711
755,-354,-619
553,889,-390

--- scanner 2 ---
649,640,665
682,-795,504
-784,533,-524
-644,584,-595
-588,-843,648
-30,6,44
-674,560,763
500,723,-460
609,671,-379
-555,-800,653
-675,-892,-343
697,-426,-610
578,704,681
493,664,-388
-671,-858,530
-667,343,800
571,-461,-707
-138,-166,112
-889,563,-600
646,-828,498
640,759,510
-630,509,768
-681,-892,-333
673,-379,-804
-742,-814,-386
577,-820,562

--- scanner 3 ---
-589,542,597
605,-692,669
-500,565,-823
-660,373,557
-458,-679,-417
-488,449,543
-626,468,-788
338,-750,-386
528,-832,-391
562,-778,733
-938,-730,414
543,643,-506
-524,371,-870
407,773,750
-104,29,83
378,-903,-323
-778,-728,485
426,699,580
-438,-605,-362
-469,-447,-387
509,732,623
647,635,-688
-868,-804,481
614,-800,639
595,780,-596

--- scanner 4 ---
727,592,562
-293,-554,779
441,611,-461
-714,465,-776
-743,427,-804
-660,-479,-426
832,-632,460
927,-485,-438
408,393,-506
466,436,-512
110,16,151
-258,-428,682
-393,719,612
-211,-452,876
808,-476,-593
-575,615,604
-485,667,467
-680,325,-822
-627,-443,-432
872,-547,-609
833,512,582
807,604,487
839,-516,451
891,-625,532
-652,-548,-490
30,-46,-14"""

private val testInput2 = """
    --- scanner 1 ---
    686,422,578
    605,423,415
    515,917,-361
    -336,658,858
    95,138,22
    -476,619,847
    -340,-569,-846
    567,-361,727
    -460,603,-452
    669,-402,600
    729,430,532
    -500,-761,534
    -322,571,750
    -466,-666,-811
    -429,-592,574
    -355,545,-477
    703,-491,-529
    -328,-685,520
    413,935,-424
    -391,539,-444
    586,-435,557
    -364,-763,-893
    807,-499,-711
    755,-354,-619
    553,889,-390
    
    --- scanner 4 ---
    727,592,562
    -293,-554,779
    441,611,-461
    -714,465,-776
    -743,427,-804
    -660,-479,-426
    832,-632,460
    927,-485,-438
    408,393,-506
    466,436,-512
    110,16,151
    -258,-428,682
    -393,719,612
    -211,-452,876
    808,-476,-593
    -575,615,604
    -485,667,467
    -680,325,-822
    -627,-443,-432
    872,-547,-609
    833,512,582
    807,604,487
    839,-516,451
    891,-625,532
    -652,-548,-490
    30,-46,-14
""".trimIndent()

private fun String.parse(): ScannerList {
    return this.split("---.*?---".toRegex())
        .map { it.trim() }
        .filter { it != "" }
        .map {
            it
                .lines()
                .map { line ->
                    line
                        .split(",")
                        .map { number ->
                            number
                                .toInt()
                        }
                }
        }
}

private fun Beacon.getOffset(other: Beacon): Triple<Int, Int, Int> {
    val xOffset = other[0] - this[0]
    val yOffset = other[1] - this[1]
    val zOffset = other[2] - this[2]
    return Triple(xOffset, yOffset, zOffset)
}

private fun BeaconList.getTransposedBeaconList(other: BeaconList): BeaconList?{
    var offset: Triple<Int, Int, Int>
    other.getPermutations().forEach outer@{ permutation ->
        this.forEach { thisBeacon ->
            permutation.forEach { otherBeacon ->
                offset = otherBeacon.getOffset(thisBeacon)

                val alignedPermutation: BeaconList =
                    permutation.map { beacon ->
                        listOf(
                            beacon[0] + offset.first,
                            beacon[1] + offset.second,
                            beacon[2] + offset.third
                        )
                    }
                val matches = alignedPermutation.count { this.contains(it) }
                if (matches >= 12) {
                    return alignedPermutation
                }
            }
        }
    }
    return null
}

private fun BeaconList.getPermutations(): List<BeaconList> {
    var result = listOf<BeaconList>()
    (0..2).forEach { x ->
        (0..2).forEach { y ->
            (0..2).forEach { z ->
                if (x != y && x != z && y != z) {
                    result = result.plus(
                        listOf(this.map { beacon -> listOf(beacon[x], beacon[y], beacon[z]) },
                            this.map { beacon -> listOf(-beacon[x], beacon[y], beacon[z]) },
                            this.map { beacon -> listOf(beacon[x], -beacon[y], beacon[z]) },
                            this.map { beacon -> listOf(beacon[x], beacon[y], -beacon[z]) },
                            this.map { beacon -> listOf(-beacon[x], -beacon[y], beacon[z]) },
                            this.map { beacon -> listOf(-beacon[x], beacon[y], -beacon[z]) },
                            this.map { beacon -> listOf(beacon[x], -beacon[y], -beacon[z]) },
                            this.map { beacon -> listOf(-beacon[x], -beacon[y], -beacon[z]) })
                    )
                }
            }
        }
    }
    return result
}


private fun String.solve19a(): Int {
    val scannerList = this.parse().toMutableList()
    var result = scannerList.removeFirst()
    var scannersMatched = 0
    val totalScanners = scannerList.size
    while (scannerList.isNotEmpty()) {
        val currentElements = scannerList.toList()
            currentElements.forEach forLoop@{ beaconList ->
            val transposedList = result.getTransposedBeaconList(beaconList)
            if (transposedList != null){
                result = result.plus(transposedList).distinct()
                scannerList.remove(beaconList)
                scannersMatched+=1
                print("Scanners Matched: $scannersMatched / $totalScanners\r")
                return@forLoop
            }
        }
    }
    return result.size
}

private fun Triple<Int, Int, Int>.manhattanDistance(other: Triple<Int, Int, Int>): Int {
    return abs(this.first - other.first) +
    abs(this.second - other.second) +
    abs(this.third - other.third)
}


private fun String.solve19b() {
    val parsed = this.lines().filter{it != ""}.map {
        val values = it.drop(1).dropLast(1).split(",").map(String::trim).map(Integer::parseInt)
        Triple(values[0], values[1], values[2])
    }

    val maxDistances = parsed.mapNotNull { point -> parsed.map { point.manhattanDistance(it) }.maxOrNull() }
    println(maxDistances.maxOrNull())
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_19.txt")
    val inputb = readInputAsString("${Constants.INPUT_PATH}input_day_19b.txt")
    println("Day 19a answer: ${input.solve19a()}")
    println("Day 19b answer: ${inputb.solve19b()}")
}
