fun main(args: Array<String>) {
    runDay1()
}

class TaxiMove(var direction: Char, var steps: Int) {
    constructor(directionString: String) : this(directionString.substring(0, 1)[0], directionString.substring(1, directionString.length).toInt())

    override fun toString(): String {
        return "TaxiMove(direction=$direction, steps=$steps)"
    }
}

class Location(var x: Int, var y: Int) {
    override fun toString(): String {
        return "Location(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        other as Location
        if (x != other.x) return false
        if (y != other.y) return false
        return true
    }
}

public fun runDay1() {
    val inputArray: List<String> = "L1, L3, L5, L3, R1, L4, L5, R1, R3, L5, R1, L3, L2, L3, R2, R2, L3, L3, R1, L2, R1, L3, L2, R4, R2, L5, R4, L5, R4, L2, R3, L2, R4, R1, L5, L4, R1, L2, R3, R1, R2, L4, R1, L2, R3, L2, L3, R5, L192, R4, L5, R4, L1, R4, L4, R2, L5, R45, L2, L5, R4, R5, L3, R5, R77, R2, R5, L5, R1, R4, L4, L4, R2, L4, L1, R191, R1, L1, L2, L2, L4, L3, R1, L3, R1, R5, R3, L1, L4, L2, L3, L1, L1, R5, L4, R1, L3, R1, L2, R1, R4, R5, L4, L2, R4, R5, L1, L2, R3, L4, R2, R2, R3, L2, L3, L5, R3, R1, L4, L3, R4, R2, R2, R2, R1, L4, R4, R1, R2, R1, L2, L2, R4, L1, L2, R3, L3, L5, L4, R4, L3, L1, L5, L3, L5, R5, L5, L4, L2, R1, L2, L4, L2, L4, L1, R4, R4, R5, R1, L4, R2, L4, L2, L4, R2, L4, L1, L2, R1, R4, R3, R2, R2, R5, L1, L2".replace(",", "").split(" ")

    val moveArray = ArrayList<TaxiMove>()
    val locationArray = ArrayList<Location>()

    for (input in inputArray) moveArray.add(TaxiMove(input))

    var direction: Int = 0 /* 0 = north
                            * 1 = east
                            * 2 = south
                            * 3 = west
                            */

    var xDistance = 0
    var yDistance = 0
    val UP = 0
    val RIGHT = 1
    val DOWN = 2
    val LEFT = 3

    locationArray.add(Location(xDistance, yDistance))
    fun logLocation() {
        val location = Location(xDistance, yDistance)
        if (locationArray.contains(location)) {
            println("we're here before: ${location.toString()}")
        }
        locationArray.add(location)
    }

    fun move(direction: Int, steps: Int) {
        var stepsCtr = steps
        while (stepsCtr > 0) {
            when (direction) {
                0 -> yDistance++
                1 -> xDistance++
                2 -> yDistance--
                3 -> xDistance--
            }
            stepsCtr--
            logLocation()
        }
    }

    for (move in moveArray) {

        var d = UP

        when (direction) {
            UP -> if (move.direction == 'L') d = LEFT else d = RIGHT
            RIGHT -> if (move.direction == 'L') d = UP else d = DOWN
            DOWN -> if (move.direction == 'L') d = RIGHT else d = LEFT
            LEFT -> if (move.direction == 'L') d = DOWN else d = UP
        }
        move(d, move.steps)
        if (move.direction == 'L') {
            direction--
            if (direction < 0) direction = 3
        } else {
            direction++
            if (direction > 3) direction = 0
        }
    }

    println("x distance: $xDistance y distance: $yDistance")
}