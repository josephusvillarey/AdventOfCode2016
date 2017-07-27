import java.io.File

fun main(args: Array<String>) {
    runDay7()
}

public fun runDay7() {
    val filepath = "in/day7input.txt"
    var supportsTlsCounter = 0
    var supportsSslCounter = 0
    for (line in File(filepath).readLines()) {
        val sequences = line.split("[\\[\\]]".toRegex())

//        println(sequences.size)
        var hasAbbaOutside = false
        var hasAbbaInside = false
        for ((index, sequence) in sequences.withIndex()) {
            when (index % 2) {
                0 -> if (sequence.hasAbba()) hasAbbaOutside = sequence.hasAbba()
                1 -> if (sequence.hasAbba()) hasAbbaInside = sequence.hasAbba()
            }
        }
        if (hasAbbaOutside && hasAbbaInside.not()) supportsTlsCounter++

        val abaList = ArrayList<String>()
        val hypernetSequences = ArrayList<String>()
        for ((index, sequence) in sequences.withIndex()) {
            when (index % 2) {
                0 -> abaList.addAll(sequence.getAbaList())
                1 -> hypernetSequences.add(sequence)
            }
        }
        var hasBabMatch = false
        for (sequence in hypernetSequences) {
            if (sequence.matchesBab(abaList)) {
                hasBabMatch = true
                break
            }
        }
        if (hasBabMatch) supportsSslCounter++
    }
    println(supportsTlsCounter)
    println(supportsSslCounter)
}

private fun String.getAbaList(): ArrayList<String> {
    val result = ArrayList<String>()

    (0..this.length - 3)
            .map { this.substring(it, it + 3) }
            .filterTo(result) { it[0] != it[1] && it[0] == it[2] }
    return result
}

private fun String.matchesBab(abaList: ArrayList<String>): Boolean {
    var matches = false
    for (i in 0..this.length - 3) {
        val threeset = this.substring(i, i + 3)
        for (aba in abaList) {
            if (threeset[0] == aba[1] && threeset[1] == aba[0] && threeset[0] == threeset[2]) {
                matches = true
            }
        }
    }
    return matches
}


private fun String.hasAbba(): Boolean {
    (0..this.length - 4).forEach {
        val fourset = this.substring(it, it + 4)
        if (fourset[0] != fourset[1] && fourset.substring(0, 2).reversed().equals(fourset.substring(2, 4))) return true
    }
    return false
}