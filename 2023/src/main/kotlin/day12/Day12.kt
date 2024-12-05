package day12

import getLines
import kotlin.math.pow

fun checkArrangement(arrangement: String, groups: String): Boolean {
//    println(arrangement.split(Regex("[.]+")).map { it.length })
    return groups.split(",").map { it.toInt() } == arrangement.split(Regex("[.]+")).map { it.length }
        .filterNot { it == 0 }
}

fun ptrFuncPart1(lines: List<String>): Int {
    return lines.sumOf { line ->
//        println(line)
        val split = line.split(" ")

        val nbToFind = split[0].count { it == '?' }
        var nbOfMatch = 0
        repeat(2.0.pow(nbToFind).toInt()) { i ->
            var bits = i
            split[0].map {
                var c: Char
                if (it == '?') {
                    c = if (bits and 1 == 1) '#' else '.'
                    bits = bits shr 1
                } else {
                    c = it
                }
                c
            }.also {
//                println(line)
//                println(it.joinToString(""))
//                println(checkArrangement(it.joinToString(""), split[1]))
                if (checkArrangement(it.joinToString(""), split[1])) {
                    nbOfMatch += 1
                }
            }
        }
//        println("Nb of possibilites: $nbOfMatch")
//        println("=================")
        nbOfMatch
    }
}

fun doStuff(map: String, groups: List<Int>) {
    println("Input: $map $groups")

    groups.forEach { nb ->

    }
}

fun ptrFuncPart2(lines: List<String>): Int {
    val test = ".?.??..??...?##. 1,1,3"
    "???.### 1,1,3"
    println(test)
    println(ptrFuncPart1(listOf(test)))

    test.split(" ").also {
        doStuff(
            it[0].replace(Regex("[.]+"), ".")
                .replace(Regex("^[.]"), "")
                .replace(Regex("[.]$"), ""),
            it[1].split(",").map { nb -> nb.toInt() }
        )
    }
    return 0
}

fun main() {
//    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day12/day12_demo_input.txt"))
//    assert(demoScore == 21)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day12/day12_demo_input.txt"))
//    assert(demoScore2 == 525152)

//    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day12/day12_input.txt"))
//    println("1st part Response is : $score")
//    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day12/day12_input.txt"))
//    println("2nd part Response is : $score2")
}