package day06


import getLines
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.math.truncate

/*
 *  Return the number of solutions for the equation -k² + nk - R > 0 which is the speed equation of the race problem
 *  Where n is the Time of the race and R the race record (the distance)
 *  This function finds the numbers of k that matches the solutions of this equation
 */
fun getNbOfSolutions(time: Long, raceRecord: Long): Double {
    val delta = sqrt(time.toDouble() * time - 4 * raceRecord)
    return ceil((time + delta) / 2 - 1) - truncate((time - delta) / 2 + 1) + 1
}

fun ptrFuncPart1(lines: List<String>): Int {
    val raceTimes = lines.first().split(Regex(" +")).drop(1).map { it.toInt() }
    val raceRecords = lines.last().split(Regex(" +")).drop(1).map { it.toInt() }
    return raceTimes.foldIndexed(1) { i, acc, time ->
        acc * getNbOfSolutions(time.toLong(), raceRecords[i].toLong()).toInt()
    }
}

fun ptrFuncPart2(lines: List<String>): Long {
    val raceTime = lines.first().replace(" ", "").split(":").last().toLong()
    val raceRecord = lines.last().replace(" ", "").split(":").last().toLong()
    return getNbOfSolutions(raceTime, raceRecord).toLong()
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day06/day06_demo_input.txt"))
    assert(demoScore == 288)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day06/day06_demo_input.txt"))
    assert(demoScore2 == 71503L)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day06/day06_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day06/day06_input.txt"))
    println("2nd part Response is : $score2")
}