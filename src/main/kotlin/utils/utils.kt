package utils

import java.io.File

fun readInputAsListOfIntegers(path: String): List<Int> {
    return File(path).readLines().map { it -> it.toInt() }
}

fun readInputAsListOfStrings(path: String): List<String> {
    return File(path).readLines()
}

fun readInputAsString(path: String): String {
    return File(path).readText(Charsets.UTF_8)
}

fun readAsEmptyLineSeparatedStrings(path: String): List<String> {
    return File(path).readText(Charsets.UTF_8).split("\n\n", "\r\n\r\n")
}
