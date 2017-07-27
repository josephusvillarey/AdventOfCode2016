import java.io.File
import java.util.regex.Pattern

fun main(args: Array<String>) {
    runDay9()
}

public fun runDay9() {
    val filepath = "in/day9input.txt"
    val text = File(filepath).readText()

    var output = ""
    var buffer = ""

    fun isMarker(str: String): Boolean {
        return Pattern.matches("[(][0-9]+x[0-9]+[)]", str)
    }

    var markerLength = 0
    var markerRepeat = 0

    var i = 0

    while (i < text.length) {
        buffer += text[i]
        if (isMarker(buffer)) {
            markerLength = buffer.split("x")[0].replace("(", "").toInt()
            markerRepeat = buffer.split("x")[1].replace(")", "").toInt()

            val forRepeat = text.substring(++i, i + markerLength)
            i += markerLength - 1

            (0 until markerRepeat).forEach { output += forRepeat }

            buffer = ""
        } else if (buffer.startsWith("(").not()) {
            output += buffer
            buffer = ""
        }

        i++
    }
    println("${output.length} output: $output")
}