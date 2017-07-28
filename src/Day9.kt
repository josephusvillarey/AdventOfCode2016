import java.io.File
import java.util.regex.Pattern

fun main(args: Array<String>) {
    runDay9()
}

public fun runDay9() {
    val filepath = "in/day9input.txt"
    var text = File(filepath).readText()

    fun String.isMarker(): Boolean {
        return Pattern.matches("[(][0-9]+x[0-9]+[)]", this)
    }

//    fun expand(input: String): Long {
//        var pos = 0
//        var dLength: Long = 0
//        while (pos < input.length) {
//            val thisChar = input[pos]
//            if (thisChar == '(') {
//                val markerEndPoint = input.indexOf(')', pos)
//                val marker = input.substring(pos + 1, markerEndPoint)
//                val markerSplit = marker.split("x")
//                val numChars = markerSplit[0].toInt()
//                val numOutputs = markerSplit[1].toInt()
//                val dataStartPoint = markerEndPoint + 1
//                val dataEndPoint = dataStartPoint + numChars
//                val data = input.substring(dataStartPoint, dataEndPoint)
//                for (i in 0..numOutputs - 1) {
//                    dLength += expand(data)
//                }
//                pos = dataEndPoint
//            } else {
//                dLength++
//                pos++
//            }
//        }
//        return dLength
//    }

    fun expand(string: String): Long {
        var outCount: Long = 0
        var buffer = ""
        var i = 0
        while (i < string.length) {
            buffer += string[i]
            if (buffer.isMarker()) {
                val markerLength = buffer.split("x")[0].replace("(", "").toInt()
                val markerRepeat = buffer.split("x")[1].replace(")", "").toInt()
                val forRepeat = string.substring(++i, i + markerLength)
                i += markerLength - 1
                (0 until markerRepeat).forEach {
                    outCount += expand(forRepeat)
//                    outCount+=forRepeat.length
                }
                buffer = ""
            } else {
                if (buffer.startsWith("(").not()) {
                    outCount += buffer.length
                    buffer = ""
                }
            }
            i++
        }
        return outCount
    }

    println(expand(text))

}