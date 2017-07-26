import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    runDay4()
}

class Room(val charsStringArray: List<String>, val sectorId: Int, val checksum: String) {
    constructor(encrypted: String) : this(encrypted.split("-").dropLast(1), Integer.parseInt(encrypted.split("-").last().split("[").get(0)), encrypted.split("-").last().split("[").get(1).replace("]", ""))

    override fun toString(): String {
        return "Room(charsStringArray=$charsStringArray, sectorId=$sectorId, checksum='$checksum')"
    }

    val charmap = HashMap<Char, Int>()
    val charArray = "abcdefghijklmnopqrstuvwxyz".toCharArray()

    fun rotate(): String {
        var stringBuilder = ""

        for (charString in charsStringArray) {
            for (c in charString) {
                stringBuilder += charArray[((charArray.indexOf(c) + sectorId) % charArray.size)]
            }
            stringBuilder += " "
        }
        return stringBuilder
    }

    fun evalRoom(): Boolean {
        for (charstring in charsStringArray) {
            for (character in charstring) {
                charmap.put(character, if (charmap.containsKey(character)) charmap.get(character)!!.plus(1) else 1)
            }
        }

        val list = ArrayList<Pair<Char, Int>>()

        val iterator = charmap.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            list.add(Pair(item.key, item.value))
        }
        list.sortWith(object : Comparator<Pair<Char, Int>> {
            override fun compare(p0: Pair<Char, Int>, p1: Pair<Char, Int>): Int {
                if (p0.second == p1.second) {
                    return p0.first.compareTo(p1.first)
                } else {
                    return p1.second.compareTo(p0.second)
                }
            }

        })

        var checksumBuilder: String = ""
        for (index in 0..4) {
            checksumBuilder += list[index].first
        }
        return checksumBuilder.equals(checksum)
    }
}

public fun runDay4() {

    val day4input = File("in/day4input.txt")

    var sectorIdSum = 0
    for (line in day4input.readLines()) {
        val room = Room(line)
//        if (room.evalRoom()) sectorIdSum += room.sectorId
        if (room.evalRoom()) {
            if (room.rotate().contains("northpole"))
                println("${room.rotate()} sector: ${room.sectorId}")
        }
    }

//    println(sectorIdSum)

}