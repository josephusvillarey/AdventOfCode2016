import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    runDay6()
}

public fun runDay6() {

    val filePath = "in/day6input.txt"
    val inputList = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset())

    val listMap = Array<HashMap<Char, Int>>(inputList.first().length, init = { HashMap() })

    for (line in inputList) {
        for ((index, c) in line.withIndex()) {
            if (listMap.get(index).containsKey(c).not()) {
                listMap.get(index).put(c, 1)
            } else {
                listMap.get(index).put(c, listMap.get(index).get(c)!! + 1)
            }
        }
    }

    for (map in listMap) {
        val list = ArrayList<Pair<Char, Int>>()
        val iterator = map.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            list.add(Pair(item.key, item.value))
        }
        list.sortWith(object : Comparator<Pair<Char, Int>> {
            override fun compare(p0: Pair<Char, Int>, p1: Pair<Char, Int>): Int {
                if (p0.second == p1.second) {
                    return p0.first.compareTo(p1.first)
                } else {
                    return p1.second.compareTo(p0.second)
                }
            }
        })
        print(list.first().first)
        print(list.last().first)
    }

}