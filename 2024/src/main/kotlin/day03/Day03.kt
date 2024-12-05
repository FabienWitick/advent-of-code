package fab.sand.box.day03

import fab.sand.box.getLines

fun ptrFunc1(lines: List<String>): Int {
    return Regex("mul[(]([0-9]*),([0-9]*)[)]").findAll(lines.joinToString("")).sumOf {
        it.groupValues[1].toInt() * it.groupValues[2].toInt()
    }
}

fun ptrFunc2(lines: List<String>): Int {
    var isEnabled = true
    return Regex("mul[(]([0-9]*),([0-9]*)[)]|do[(][)]|don't[(][)]").findAll(lines.joinToString("")).sumOf {
        if (it.groupValues[0] == "do()") isEnabled = true
        else if (it.groupValues[0] == "don't()") isEnabled = false
        else if (isEnabled)
            return@sumOf it.groupValues[1].toInt() * it.groupValues[2].toInt()
        return@sumOf 0
    }
}

fun main() {
    val demoScore = ptrFunc1(getLines("2024/src/main/kotlin/day03/day03_demo_input.txt"))
    assert(demoScore == 161)

    val demoScore2 = ptrFunc2(getLines("2024/src/main/kotlin/day03/day03_demo_input.txt"))
    assert(demoScore2 == 48)

    val score = ptrFunc1(getLines("2024/src/main/kotlin/day03/day03_input.txt"))
    println("1st part Response is : $score")

    val scorePart2 = ptrFunc2(getLines("2024/src/main/kotlin/day03/day03_input.txt"))
    println("2nd part Response is : $scorePart2")
}