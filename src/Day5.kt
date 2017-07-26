import utils.HASH

fun main(args: Array<String>) {
    runDay5()
}

public fun runDay5() {

    val passwordBuilder = CharArray(8, { ' ' })

    val puzzleInput = "ojvtpuvg"
//    val puzzleInput = "abc"
    var ctr = 0;

    for (index in 0..Int.MAX_VALUE) {

        var success = false

        val forHash = puzzleInput + index
        val hashed = HASH.md5(forHash)

        if (hashed.startsWith("00000")) try {
            val i = Integer.parseInt(hashed.get(5) + "")
            if (i >= 0 && i < passwordBuilder.size && passwordBuilder[i] == ' ') {
                passwordBuilder[i] = hashed.get(6)
                ctr++
                success = true
            }
        } catch (nmf: NumberFormatException) {
        }

        if (success) println(passwordBuilder)
        if (ctr == 8) break
    }
}