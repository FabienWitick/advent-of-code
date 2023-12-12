package day09

import getLines

fun ptrFuncPart1(lines: List<String>): Int {
    return lines.map { line -> line.split(" ").map { it.toInt() } }.sumOf { nbs ->
        val sequences = mutableListOf(nbs)
        while (!sequences.last().all { it == 0 }) {
            sequences.add(sequences.last().windowed(2, 1).map { it[1] - it[0] })
        }
        sequences.reversed().drop(1).map { it.last() }.fold(0) { acc: Int, nb: Int ->
            acc + nb
        }
    }
}

fun ptrFuncPart2(lines: List<String>): Int {
    return lines.map { line -> line.split(" ").map { it.toInt() } }.sumOf { nbs ->
        val sequences = mutableListOf(nbs)
        while (!sequences.last().all { it == 0 }) {
            sequences.add(sequences.last().windowed(2, 1).map { it[1] - it[0] })
        }
        sequences.reversed().drop(1).map { it.first() }.fold(0) { acc: Int, nb: Int ->
            nb - acc
        }
    }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day09/day09_demo_input.txt"))
    assert(demoScore == 114)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day09/day09_demo_input.txt"))
    assert(demoScore2 == 2)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day09/day09_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day09/day09_input.txt"))
    println("2nd part Response is : $score2")
}