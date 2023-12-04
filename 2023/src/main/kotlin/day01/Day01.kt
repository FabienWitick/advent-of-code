package day01

import getLines

fun replaceSpelledDigitsByDigit(line: String): String {
    var newLine = line

    /*
     *  Here I replace digits name by the actual digit but surrounded by the first and last letter
     *  This way I avoid collisions like twone = 21 => or sevenine = 79
     */
    val digits = mapOf(
        "one" to "o1e",
        "two" to "t2o",
        "three" to "t3e",
        "four" to "f4r",
        "five" to "f5e",
        "six" to "s6x",
        "seven" to "s7n",
        "eight" to "e8t",
        "nine" to "n9e"
    )
    digits.forEach { (name, digit) ->
        newLine = newLine.replace(name, digit + name)
    }
    return newLine
}

fun ptrFunc(lines: List<String>): Int {
    return lines.sumOf { line ->
        val finalLine = replaceSpelledDigitsByDigit(line)
        (finalLine.first { it.isDigit() }.toString() + finalLine.last { it.isDigit() }.toString()).toInt()
    }
}

fun main() {
    val demoScore = ptrFunc(getLines("2023/src/main/kotlin/day01/day01_demo_input.txt"))
    assert(demoScore == 281)

    val score = ptrFunc(getLines("2023/src/main/kotlin/day01/day01_input.txt"))
    println("1st part Response is : $score")
}