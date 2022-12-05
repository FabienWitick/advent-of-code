package day4

import getLines

fun IntRange.contains(range: IntRange): Boolean {
    return first <= range.first && last >= range.last
}

fun ptrFunc(lines: List<String>): List<Int> {
    val intRangeList = lines.map {
        it.split(",").map { it.split("-").let { it[0].toInt()..it[1].toInt() } }
    }

    val firstPart = intRangeList.map { it[0].contains(it[1]) || it[1].contains(it[0]) }.count { it }
    val secondPart = intRangeList.map { it[0].intersect(it[1]).isNotEmpty() }.count { it }
    return listOf(firstPart, secondPart)
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day4/day4_demo_input.txt"))
    assert(demoScore[0] == 2)
    assert(demoScore[1] == 4)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day4/day4_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
