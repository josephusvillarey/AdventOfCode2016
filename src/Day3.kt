import java.io.File

fun main(args: Array<String>) {
    runDay3()
}

class Triangle(var sides: List<Int>) {
    fun isValidTriangle(): Boolean {
        return sumSmallerSides() > largestSide()
    }

    constructor(triangleString: String) : this(emptyList()) {
        val stringArray = triangleString.trim().replace("\\s+".toRegex(), " ").split(" ")
        var intArray = IntArray(stringArray.size)
        for ((index, string) in stringArray.withIndex()) {
            intArray[index] = Integer.parseInt(string)
        }
        sides = intArray.toCollection(ArrayList<Int>())
    }

    private fun largestSide() = sides.sorted().last()
    private fun sumSmallerSides() = sides.sorted().get(0) + sides.sorted().get(1)

    override fun toString(): String {
        return "Triangle(sides=$sides)"
    }


}

public fun runDay3() {
    val day3Input = File("in/day3input.txt")
    val triangleArray = ArrayList<Triangle>()
    var validTriangleCount = 0

    var count = 2
    var firstLineHolder: String = ""
    var secondLineHolder: String = ""

    fun generateTriangles(lines: List<String>) {
        println("calling generate with $lines")
        var arraysArray = arrayOf(ArrayList<Int>(), ArrayList<Int>(), ArrayList<Int>())
        lines.forEach {
            var lineArray = it.trim().replace("\\s+".toRegex(), " ").split(" ")
            var counter = 0
            arraysArray.forEach { it.add(Integer.parseInt(lineArray[counter++])) }
        }

        arraysArray.forEach {
            val triangle = Triangle(it)
            println(triangle)
            if (triangle.isValidTriangle()) validTriangleCount++
        }
    }

    day3Input.readLines().forEach {
        println(it)
        when (count) {
            2 -> firstLineHolder = it
            1 -> secondLineHolder = it
            0 -> generateTriangles(listOf(firstLineHolder, secondLineHolder, it))
        }
        count--
        if (count < 0) count = 2
    }

//    for (line in day3Input.readLines()) {
//        val triangle = Triangle(line)
//        triangleArray.add(triangle)
//        if (triangle.isValidTriangle()) {
//            validTriangleCount++
//        }
//    }
    println(validTriangleCount)
}