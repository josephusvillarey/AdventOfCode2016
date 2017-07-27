import com.sun.org.apache.xpath.internal.operations.Bool
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

    for (i in 0..text.length) {
        buffer += text[i]
        if (isMarker(buffer)) {
            markerLength = buffer.split("x")[0].toInt()
            markerRepeat = buffer.split("x")[1].toInt()

//            val forRepeat = text.substring(++i, )
        }
    }

}