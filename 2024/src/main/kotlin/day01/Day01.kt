package day01

import fab.sand.box.getLines
import kotlin.math.abs

fun ptrFunc1(lines: List<String>): Int {
    return lines.map {
        it.split(Regex("( )+")).map(String::toInt)
    }.let { list ->
        val left = list.map { it.first() }.sorted()
        val right = list.map { it.last() }.sorted()

        left.foldIndexed(0) { i, acc, value ->
            acc + abs(value - right[i])
        }
    }
}

fun ptrFunc2(lines: List<String>): Int {
    return lines.map {
        it.split(Regex("( )+")).map(String::toInt)
    }.let { list ->
        val left = list.map { it.first() }
        val right = list.map { it.last() }

        left.fold(0) { acc, value ->
            acc + value * right.count { it == value }
        }
    }
}

fun main() {
    val demoScore = ptrFunc1(getLines("2024/src/main/kotlin/day01/day01_demo_input.txt"))
    assert(demoScore == 11)

    val demoScore2 = ptrFunc2(getLines("2024/src/main/kotlin/day01/day01_demo_input.txt"))
    assert(demoScore2 == 31)

    val score = ptrFunc1(getLines("2024/src/main/kotlin/day01/day01_input.txt"))
    println("1st part Response is : $score")

    val scorePart2 = ptrFunc2(getLines("2024/src/main/kotlin/day01/day01_input.txt"))
    println("2nd part Response is : $scorePart2")
}