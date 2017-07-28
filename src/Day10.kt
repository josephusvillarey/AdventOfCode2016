import java.io.File
import java.util.*

fun main(args: Array<String>) {
    runDay10()
}

// type 0 = Bot
// type 1 = Output
abstract class Receiver(val type: Int, val num: Int) {
    abstract fun receiveChip(chipNum: Int)
}

class Output(val outputNum: Int) : Receiver(1, outputNum) {
    val chipsList = ArrayList<Int>()
    override fun receiveChip(chipNum: Int) {
        // do nothing
        chipsList.add(chipNum)
    }

    override fun toString(): String {
        return "Output(outputNum=$outputNum, chipsList=$chipsList)"
    }

}

class Bot(val botNum: Int) : Receiver(0, botNum) {
    var hasInstructions = false
    var isInitialized = false
    lateinit var lowDest: Receiver
    lateinit var highDest: Receiver
    var chips = arrayOf(0, 0)
    fun setInstructions(low: Receiver, high: Receiver) {
        lowDest = low
        highDest = high
        isInitialized = true
        distributeChips()
    }

    override fun receiveChip(chipNum: Int) {
        if (chips.filter { it > 0 }.size > 1) {
            // can't receive chip, hand is full
            throw IllegalStateException("can't receive chip, hand is full")
        } else {
            // receive chip
            chips[chips.indexOf(0)] = chipNum
        }
        distributeChips()
    }

    fun distributeChips() {
        if (isInitialized && chips.filter { it > 0 }.size == 2) {
            println("bot number ${botNum} is distributing chips: low=${chips.min()} high=${chips.max()}")
            lowDest.receiveChip(chips.min()!!)
            highDest.receiveChip(chips.max()!!)
            chips = arrayOf(0, 0)
        }
    }

    override fun toString(): String {
        return "Bot(botNum=$botNum, hasInstructions=$hasInstructions, isInitialized=$isInitialized, lowDest=$lowDest, highDest=$highDest, chips=${Arrays.toString(chips)})"
    }


}

public fun runDay10() {
    val filepath = "in/day10input.txt"

    val botInstructionsRegex = "bot [0-9]+ gives low to (bot|output) [0-9]+ and high to (bot|output) [0-9]+".toRegex()
    val chipAssignmentRegex = "value [0-9]+ goes to bot [0-9]+".toRegex()

    val lowReceiverRegex = "low to (bot|output) [0-9]+".toRegex()
    val highReceiverRegex = "high to (bot|output) [0-9]+".toRegex()

    val botMap = HashMap<Int, Bot>()
    val outputMap = HashMap<Int, Output>()

    for (i in File(filepath).readLines()) {
        if (i.matches(botInstructionsRegex)) {
            val botSource = i.split(" ")[1].toInt()
            if (botMap.containsKey(botSource).not()) {
                botMap.put(botSource, Bot(botSource))
            }

            val lowReceiverText = lowReceiverRegex.find(i)?.value
            val highReceiverText = highReceiverRegex.find(i)?.value

            val lowValue = lowReceiverText?.split(" ")?.get(3)?.toInt()
            val highValue = highReceiverText?.split(" ")?.get(3)?.toInt()

            val lowType = if (lowReceiverText?.split(" ")?.get(2).equals("bot")) 0 else 1
            val highType = if (highReceiverText?.split(" ")?.get(2).equals("bot")) 0 else 1

            if (lowType == 0) {
                if (botMap.containsKey(lowValue).not()) {
                    botMap.put(lowValue!!, Bot(lowValue!!))
                }
            } else {
                if (outputMap.containsKey(lowValue).not()) {
                    outputMap.put(lowValue!!, Output(lowValue!!))
                }
            }

            if (highType == 0) {
                if (botMap.containsKey(highValue).not()) {
                    botMap.put(highValue!!, Bot(highValue!!))
                }
            } else {
                if (outputMap.containsKey(highValue).not()) {
                    outputMap.put(highValue!!, Output(highValue!!))
                }
            }

            val low = if (lowType == 0) botMap.get(lowValue) else outputMap.get(lowValue)
            val high = if (highType == 0) botMap.get(highValue) else outputMap.get(highValue)

            botMap.get(botSource)?.setInstructions(low!!, high!!)
        } else if (i.matches(chipAssignmentRegex)) {

            val value = i.split(" ")[1].toInt()
            val botNum = i.split(" ")[5].toInt()

            if (botMap.containsKey(botNum).not()) {
                botMap.put(botNum, Bot(botNum))
            }

            botMap.get(botNum)?.receiveChip(value)
        }
    }
    println(outputMap.get(0)!!.chipsList.first() * outputMap.get(1)!!.chipsList.first() * outputMap.get(2)!!.chipsList.first())
}