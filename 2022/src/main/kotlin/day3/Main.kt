package day3

import getLines

fun Char.priority() = if (this in 'a'..'z') { this - 'a' + 1 } else { this - 'A' + 27 }

fun ptrFunc(lines: List<String>): List<Int> {
    val firstPart = lines.sumOf { line ->
        val lineParts = line.take(line.length / 2) to line.substring(line.length / 2)
        lineParts.first.find { lineParts.second.indexOf(it) != -1 }?.priority() ?: 0
    }

    val secondPart = lines.windowed(3, 3) {
        it[0].toSet().intersect(it[1].toSet().intersect(it[2].toSet())).first().priority()
    }.sum()

    return listOf(firstPart, secondPart)
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day3/day3_demo_input.txt"))
    assert(demoScore[0] == 157)
    assert(demoScore[1] == 70)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day3/day3_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
