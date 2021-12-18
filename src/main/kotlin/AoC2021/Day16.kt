package AoC2021

import utils.readInputAsString
import kotlin.math.min

typealias Remainder = String

data class Packet(
    val version: Int,
    val type: Int,
    val subElementsByPackage: Boolean? = null,
    val subElements: Int? = null,
    val subPackets: List<Packet>? = null,
    val literalValue: Long? = null
)

private var testInput = "9C0141080250320F1802104A08"

private fun getLeadingPacketAndRemainder(binaryString: String): Pair<Packet, Remainder> {
    val version = Integer.parseInt(binaryString.take(3), 2)
    val type = Integer.parseInt(binaryString.substring(3, 6), 2)

    if (type == 4) return getLeadingLiteralAndRemainder(binaryString)

    val subElementsByPackage = binaryString[6] == '1'
    val subElements =
        if (subElementsByPackage) binaryString.substring(7, 18).toInt(2) else binaryString.substring(7, 22).toInt(2)
    var remainder = if (subElementsByPackage) binaryString.substring(18) else binaryString.substring(22)
    var subPackets = emptyList<Packet>()
    if (subElementsByPackage) {
        repeat(subElements) {
            val (newPacket, newRemainder) = getLeadingPacketAndRemainder(remainder)
            subPackets = subPackets.plus(newPacket)
            remainder = newRemainder
        }
    } else {
        var binaryPayload = remainder.substring(0, subElements)
        remainder = remainder.substring(subElements)
        while (binaryPayload != "") {
            val (newPacket, remainingPayload) = getLeadingPacketAndRemainder(binaryPayload)
            subPackets = subPackets.plus(newPacket)
            binaryPayload = remainingPayload
        }
    }
    return Packet(
        version = version,
        type = type,
        subElementsByPackage = subElementsByPackage,
        subElements = subElements,
        subPackets = subPackets
    ) to remainder
}

private fun getLeadingLiteralAndRemainder(binaryString: String): Pair<Packet, Remainder> {
    val version = Integer.parseInt(binaryString.take(3), 2)
    val contentLength = binaryString.substring(6).withIndex().takeWhile {
        (it.index % 5 == 0 && it.value == '1') || (it.index % 5 != 0)
    }.size + 5
    val literalPayload = binaryString.substring(6, min(contentLength + 6, binaryString.length))
    val asBinaryHexes = literalPayload
        .chunked(5)
        .map{it.drop(1)}
        .filter { it.length == 4 }

    val asHexValueList = asBinaryHexes.map { it.toInt(2).toString(16) }
    val asHexString = asHexValueList.reduce { a, s -> a.plus(s) }
    val literalValue = asHexString.toLong(16)
    val packet = Packet(version = version, type = 4, literalValue = literalValue)
    return (packet to binaryString.drop(contentLength + 6))
}

private fun packetVersionSums(packet: Packet): Int =
    if (packet.subPackets != null) packet.subPackets.sumOf { packetVersionSums(it) } + packet.version else packet.version

private fun String.hexToBinary(): String {
    val binaryString = this.map {
        val asInt = Integer.parseInt(it.toString(), 16)
        val asBinary = asInt.toString(2).padStart(4, '0')
        asBinary
    }.reduce { a, i -> a.plus(i) }
    return binaryString
}

private fun computePacket(packet: Packet): Long {
    val result = when (packet.type) {
        0 -> packet.subPackets!!.sumOf { computePacket(it) }
        1 -> packet.subPackets!!.map { computePacket(it) }.reduce { a, i -> a * i }
        2 -> packet.subPackets!!.minOf { computePacket(it) }
        3 -> packet.subPackets!!.maxOf { computePacket(it) }
        5 -> packet.subPackets!!.map { computePacket(it) }.let { if (it[0] > it[1]) 1 else 0 }
        6 -> packet.subPackets!!.map { computePacket(it) }.let { if (it[0] < it[1]) 1 else 0 }
        7 -> packet.subPackets!!.map { computePacket(it) }.let { if (it[0] == it[1]) 1 else 0 }
        else -> packet.literalValue!!
    }
    return result
}

private fun String.solve16a(): Int {
    val binaryString = this.hexToBinary()
    return packetVersionSums(getLeadingPacketAndRemainder(binaryString).first)
}

private fun String.solve16b(): Long {
    val binaryString = this.hexToBinary()
    val packet = getLeadingPacketAndRemainder(binaryString).first
    return computePacket(packet)
}

fun main() {
    val input = readInputAsString("${Constants.INPUT_PATH}input_day_16.txt").trim()
    println("Day 16a answer: ${input.solve16a()}")
    println("Day 16b answer: ${input.solve16b()}")
}
