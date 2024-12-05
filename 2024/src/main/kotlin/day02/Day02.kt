package fab.sand.box.day02

import fab.sand.box.getLines
import kotlin.math.abs

fun isReportSafe(report: List<Int>): Boolean {
    val isDescending = report[0] - report[1] > 0

    report.windowed(2).forEach {
        val diff = it[0] - it[1]

        if (diff > 0 != isDescending) return false
        if (abs(diff) !in 1..3) return false
    }
    return true
}

fun ptrFunc1(lines: List<String>): Int {
    return lines.count { line ->
        isReportSafe(line.split(" ").map(String::toInt))
    }
}

fun ptrFunc2(lines: List<String>): Int {
    return lines.count { line ->
        val report = line.split(" ").map(String::toInt)
        for (i in 0..report.lastIndex) {
            if (isReportSafe(report.filterIndexed { index, _ -> index != i })) return@count true
        }
        return@count false
    }
}

fun main() {
    val demoScore = ptrFunc1(getLines("2024/src/main/kotlin/day02/day02_demo_input.txt"))
    assert(demoScore == 2)

    val demoScore2 = ptrFunc2(getLines("2024/src/main/kotlin/day02/day02_demo_input.txt"))
    assert(demoScore2 == 4)

    val score = ptrFunc1(getLines("2024/src/main/kotlin/day02/day02_input.txt"))
    println("1st part Response is : $score")

    val scorePart2 = ptrFunc2(getLines("2024/src/main/kotlin/day02/day02_input.txt"))
    println("2nd part Response is : $scorePart2")
}