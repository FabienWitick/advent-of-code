package day02

import getLines
import kotlin.math.max


fun ptrFuncPart1(lines: List<String>): Int {
    return lines.sumOf { line ->
        val cubeMap = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val matchResult = Regex("Game ([0-9]*): (.*)").matchEntire(line) // Hop la regex, petite dédicasse à bridou_n
        val gameId: Int = matchResult!!.groupValues[1].toInt()
        matchResult.groupValues[2].split("; ").forEach { cubeSet ->
            cubeSet.split(", ").onEach {
                it.split(" ").also { cube ->
                    if (cube[0].toInt() > cubeMap[cube[1]]!!) return@sumOf 0
                }
            }
        }
        gameId
    }
}

fun ptrFuncPart2(lines: List<String>): Long {
    return lines.sumOf { line ->
        val cubeMap = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        val matchResult = Regex("Game ([0-9]*): (.*)").matchEntire(line)
        matchResult!!.groupValues[2].split(Regex("[,;] ")).forEach {
            it.split(" ").also { cube ->
                cubeMap[cube[1]] = max(cubeMap[cube[1]]!!, cube[0].toInt())
            }
        }

        cubeMap.values.fold(1) { acc, i -> acc * i }.toLong()
    }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day02/day02_demo_input.txt"))
    assert(demoScore == 8)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day02/day02_demo_input.txt"))
    assert(demoScore2 == 2286L)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day02/day02_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day02/day02_input.txt"))
    println("2nd part Response is : $score2")
}