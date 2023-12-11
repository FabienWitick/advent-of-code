package day08

import getLines

fun doStuff(nodes: Map<String, Pair<String, String>>, instructions: String, start: String, endRX: Regex): Int {
    var i = 0
    var node = nodes[start]!!
    var steps = 0
    while (true) {
        steps++
        val key = if (instructions[i % instructions.length] == 'L') {
            node.first
        } else node.second
        if (endRX.matchEntire(key) != null) {
            return steps
        }
        node = nodes[key]!!
        i++
    }
}

fun ptrFuncPart1(lines: List<String>): Int {
    val instructions = lines.first()
    val nodes = mutableMapOf<String, Pair<String, String>>()
    lines.drop(2).forEach { line ->
        Regex("([A-Z]{3}) = [(]{1}([A-Z]{3}), ([A-Z]{3})[)]{1}").matchEntire(line)!!.let { matchResult ->
            nodes[matchResult.groupValues[1]] = Pair(matchResult.groupValues[2], matchResult.groupValues[3])
        }
    }
    return doStuff(nodes, instructions, "AAA", Regex("ZZZ"))
}

fun PGCDOf(a: Long, b: Long): Long {
    if (b == 0L) {
        return a
    }
    return PGCDOf(b, a % b)
}

fun PPCMOf(a: Long, b: Long): Long {
    return (a * b) / PGCDOf(a, b)
}

fun ptrFuncPart2(lines: List<String>): Long {
    val instructions = lines.first()
    val nodes = mutableMapOf<String, Pair<String, String>>()
    lines.drop(2).forEach { line ->
        Regex("([0-9A-Z]{3}) = [(]{1}([0-9A-Z]{3}), ([0-9A-Z]{3})[)]{1}").matchEntire(line)!!.let { matchResult ->
            nodes[matchResult.groupValues[1]] = Pair(matchResult.groupValues[2], matchResult.groupValues[3])
        }
    }

    val stepList = mutableListOf<Long>()
    nodes.filterKeys { it.endsWith('A') }.forEach { start ->
        stepList.add(doStuff(nodes, instructions, start.key, Regex(".{2}Z")).toLong())
    }
    return stepList.drop(1).fold(stepList.first()) { acc: Long, steps: Long ->
        PPCMOf(acc, steps)
    }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day08/day08_demo_input.txt"))
    assert(demoScore == 6)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day08/day08_part2_demo_input.txt"))
    assert(demoScore2 == 6L)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day08/day08_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day08/day08_input.txt"))
    println("2nd part Response is : $score2")
}