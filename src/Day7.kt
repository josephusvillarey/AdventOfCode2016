import java.io.File
import java.util.regex.Pattern

fun main(args: Array<String>) {
    runDay7()
}

public fun runDay7() {
    val filepath = "in/day7input.txt"
    var counter = 0
    for (line in File(filepath).readLines()) {
        val sequences = line.split("[\\[\\]]".toRegex())

        println(sequences.size)
        var hasAbbaOutside = false
        var hasAbbaInside = false
        for ((index, sequence) in sequences.withIndex()) {
            when (index % 2) {
                0 -> {
                    if (sequence.hasAbba()) {
                        hasAbbaOutside = sequence.hasAbba()
                    }
                }
                1 -> {
                    if (sequence.hasAbba()) {
                        hasAbbaInside = sequence.hasAbba()
                    }
                }
            }
        }
        if (hasAbbaOutside && hasAbbaInside.not()) counter++
    }
    println(counter)
}

private fun String.hasAbba(): Boolean {
    for (i in 0..this.length - 4) {
        val fourset = this.substring(i, i + 4)
//        println("fourset: $fourset firstset: ${fourset.substring(0, 2)} secondset: ${fourset.substring(2, 4)} isSameChars: ${fourset[0] == fourset[1]}")
        if (fourset[0] != fourset[1] && fourset.substring(0, 2).reversed().equals(fourset.substring(2, 4))) {
//            println(fourset)
            return true
        }
    }
    return false
}