package day10

import getLines

fun computeSignalStrength(cycle: Int, register: Int): Int = if ((cycle - 20) % 40 == 0) {
    cycle * register
} else {
    0
}

fun addPixel(image: MutableList<String>, pixelLine: StringBuilder, cycle: Int, register: Int) {
    if (cycle % 40 == 0 && pixelLine.isNotEmpty()) {
        image.add(pixelLine.toString())
        pixelLine.clear()
    }
    if ((cycle % 40) in (register - 1)..(register + 1)) {
        pixelLine.append("#")
    } else {
        pixelLine.append(".")
    }
}

fun printImage(image: List<String>) {
    image.forEach {
        println(it)
    }
}

fun ptrFunc(lines: List<String>): Int {
    var cycle = 0
    var register = 1
    val image = mutableListOf<String>()
    val pixelLine = StringBuilder()

    val part1Response = lines.fold(0) { signalStrength, line ->
        val cmd = Regex("([a-z]*) ?(-?[0-9]*)").matchEntire(line)?.groupValues ?: listOf() // C'est cadeau cette Regex
        if (cmd[1] == "noop") {
            addPixel(image, pixelLine, cycle, register)
            cycle += 1
            signalStrength + computeSignalStrength(cycle, register)
        } else {
            var tmp = 0
            repeat(2) {
                addPixel(image, pixelLine, cycle, register)
                cycle += 1
                tmp += computeSignalStrength(cycle, register)
            }
            register += cmd[2].toInt()
            signalStrength + tmp
        }
    }
    if (pixelLine.isNotEmpty()) {
        image.add(pixelLine.toString())
    }

    printImage(image)
    return part1Response
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day10/day10_demo_input.txt"))
    assert(demoScore == 13140)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day10/day10_input.txt"))
    println("1st part Response is : $score")
}
