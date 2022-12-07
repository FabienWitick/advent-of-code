package day5

import getLines

fun ptrFunc(lines: List<String>): List<String> {
    var dequeuesPart1: List<ArrayDeque<String>>? = null
    var dequeuesPart2: List<ArrayDeque<String>>? = null

    lines.forEach { line ->
        if (line.contains("[")) {
            // Build one Dequeue for each Part
            val results = Regex("( {4}|\\[[A-Z]])").findAll(line)
            if (dequeuesPart1 == null) {
                dequeuesPart1 = results.map { ArrayDeque<String>() }.toList()
                dequeuesPart2 = results.map { ArrayDeque<String>() }.toList()
            }
            results.forEachIndexed { i, result ->
                result.groupValues[1].takeIf { it[0] == '[' }?.let {
                    dequeuesPart1?.get(i)?.add(it[1].toString())
                    dequeuesPart2?.get(i)?.add(it[1].toString())
                }
            }
        } else if (line.contains("move")) {
            Regex("move ([0-9]*) from ([0-9]*) to ([0-9]*)").matchEntire(line)?.let { result ->
                repeat(result.groupValues[1].toInt()) { index ->
                    // First part
                    dequeuesPart1?.get(result.groupValues[2].toInt() - 1)?.removeFirst()?.let {
                        dequeuesPart1?.get(result.groupValues[3].toInt() - 1)?.addFirst(it)
                    }

                    // Second part
                    dequeuesPart2?.get(result.groupValues[2].toInt() - 1)
                        ?.removeAt(result.groupValues[1].toInt() - 1 - index)
                        ?.let {
                            dequeuesPart2?.get(result.groupValues[3].toInt() - 1)?.addFirst(it)
                        }
                }
            }

        }
    }
    return listOf(
        dequeuesPart1?.joinToString(separator = "") { it.first() } ?: "",
        dequeuesPart2?.joinToString(separator = "") { it.first() } ?: ""
    )
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day5/day5_demo_input.txt"))
    assert(demoScore[0] == "CMZ")
    assert(demoScore[1] == "MCD")

    val score = ptrFunc(getLines("2022/src/main/kotlin/day5/day5_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
