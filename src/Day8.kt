import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    runDay8()
}

class Move(val x: Int, val y: Int, val type: Int) {
    override fun toString(): String {
        return "Move(x=$x, y=$y, type=$type)"
    }
}

public fun runDay8() {

    val pixelsArray = Array(50) { BooleanArray(6) { false } }

    fun drawArray() {

        for (i in 0 until pixelsArray[0].size) {
            for (j in 0 until pixelsArray.size) {
                print(if (pixelsArray[j][i]) " x " else " _ ")
            }
            println()
        }
    }

    drawArray()

    fun drawRect(x: Int, y: Int) {
        for (i in 0 until x) {
            for (j in 0 until y) {
                pixelsArray[i][j] = true
            }
        }
    }

    fun rotateRow(row: Int, by: Int) {
        var array = ArrayList<Boolean>()
        for (index in 0 until pixelsArray.size) {
            array.add(pixelsArray[index][row])
        }
        val list = array.toList()
        Collections.rotate(list, by)
        for ((index, item) in list.withIndex()) {
            pixelsArray[index][row] = item
        }
    }

    fun rotateCol(col: Int, by: Int) {
        val list = pixelsArray[col].toList()
        Collections.rotate(list, by)
        pixelsArray[col] = list.toBooleanArray()
    }

    val MOVE_DRAWRECT = 0
    val MOVE_ROTATE_ROW = 1
    val MOVE_ROTATE_COL = 2

    fun interpretLine(line: String): Move {
        var x = 0
        var y = 0
        var move = 0
        if (line.startsWith("rect")) {
            x = line.replace("rect ", "").split("x")[0].toInt()
            y = line.replace("rect ", "").split("x")[1].toInt()
            move = MOVE_DRAWRECT
        } else if (line.startsWith("rotate row y=")) {
            x = line.replace("rotate row y=", "").split(" by ")[0].toInt()
            y = line.replace("rotate row y=", "").split(" by ")[1].toInt()
            move = MOVE_ROTATE_ROW
        } else {//if (line.startsWith("rotate column x=")) {
            x = line.replace("rotate column x=", "").split(" by ")[0].toInt()
            y = line.replace("rotate column x=", "").split(" by ")[1].toInt()
            move = MOVE_ROTATE_COL
        }
        return Move(x, y, move)
    }

    val filepath = "in/day8input.txt"

    for (line in File(filepath).readLines()) {
        val move = interpretLine(line)
        println("line: $line move: $move")
        when (move.type) {
            MOVE_DRAWRECT -> drawRect(move.x, move.y)
            MOVE_ROTATE_ROW -> rotateRow(move.x, move.y)
            MOVE_ROTATE_COL -> rotateCol(move.x, move.y)
        }
        drawArray()
    }

    var ctr = 0
    for (i in pixelsArray) {
        for (j in i) {
            if (j) ctr++
        }
    }

    println(ctr)
}