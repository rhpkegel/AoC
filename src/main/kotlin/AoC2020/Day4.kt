package AoC2020

import utils.readInputAsString

data class Passport(
    val byr: String?,
    val iyr: String?,
    val eyr: String?,
    val hgt: String?,
    val hcl: String?,
    val ecl: String?,
    val pid: String?,
    val cid: String?
)

private var test_input =
"""ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in"""

fun String.parseDay4Input(): List<String>{
    return this.split("\n\n", "\r\n\r\n").map{it.replace("\n", " ")}
}

fun String.toPassport(): Passport =
    Passport(
        "byr:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "iyr:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "eyr:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "hgt:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "hcl:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "ecl:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "pid:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1),
        "cid:([^\\s]+)".toRegex().find(this)?.groupValues?.get(1))

fun Passport.getValidSum(): Boolean {
    return byr != null && byr.toInt() in 1920..2002
            && iyr != null && iyr.toInt() in 2010..2020
            && eyr != null && eyr.toInt() in 2020..2030
            && hgt != null && ((hgt.endsWith("cm") && hgt.substringBefore("cm").toInt() in 150..193) || (hgt.endsWith("in") && hgt.substringBefore("in").toInt() in 59..76))
            && hcl != null && hcl.matches("#[0-9a-f]{6}".toRegex())
            && ecl != null && ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            && pid != null && pid.matches("\\d{9}".toRegex())
}


fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_4.txt")
     println(input.parseDay4Input().map {it.toPassport().getValidSum()}.count{it})
}
