package day03

import getLines

fun getNumberLength(str: String): Int {
    str.forEachIndexed { index, c -> if (c.isDigit().not()) return index }
    return str.length
}

fun checkIfLineHasSymbol(line: String, start: Int, length: Int): Boolean {
    val i = if (start == 0) 0 else start - 1
    val end = if (length == line.length) length else length + 1
    return line.substring(i, end).find { it != '.' } != null
}

fun ptrFuncPart1(lines: List<String>): Int {
    var sum = 0
    for (i in lines.indices) {
        var j = 0
        while (j < lines[i].length) {
            val start = lines[i].substring(j).indexOfFirst { it.isDigit() }
            if (start != -1) {
                val length = getNumberLength(lines[i].substring(j + start))
                if (j + start > 0 && lines[i][j + start - 1] != '.') {
                    sum += lines[i].substring(j + start, j + start + length).toInt()
                } else if (j + start + length + 1 < lines[i].length && lines[i][j + start + length] != '.') {
                    sum += lines[i].substring(j + start, j + start + length).toInt()
                } else if (i > 0 && checkIfLineHasSymbol(lines[i - 1], j + start, j + start + length)) {
                    sum += lines[i].substring(j + start, j + start + length).toInt()
                } else if (i + 1 < lines.size && checkIfLineHasSymbol(lines[i + 1], j + start, j + start + length)) {
                    sum += lines[i].substring(j + start, j + start + length).toInt()
                }
                j += start + length
            } else {
                j = lines[i].length
            }
        }
    }
    return sum
}

fun getNbsInLineNextToEngine(line: String, enginePos: Int): List<Long> {
    val nbs = mutableListOf<Long>()
    if (enginePos > 0 && line[enginePos - 1].isDigit()) {
        val index = line.substring(0, enginePos).reversed().indexOfFirst { it.isDigit().not() }
        nbs.add(line.substring(enginePos - (if (index == -1) enginePos else index)).takeWhile {
            it.isDigit()
        }.toLong())
        if (line[enginePos].isDigit().not() && enginePos + 1 < line.length && line[enginePos + 1].isDigit()) {
            nbs.add(line.substring(enginePos + 1).takeWhile { it.isDigit() }.toLong())
        }
    } else if (line[enginePos].isDigit()) {
        nbs.add(line.substring(enginePos).takeWhile { it.isDigit() }.toLong())
    } else if (enginePos + 1 < line.length && line[enginePos + 1].isDigit()) {
        nbs.add(line.substring(enginePos + 1).takeWhile { it.isDigit() }.toLong())
    }
    return nbs
}

fun ptrFuncPart2(lines: List<String>): Long {
    var sum = 0L
    for (i in lines.indices) {
        var j = 0
        while (j < lines[i].length) {
            val pos = lines[i].substring(j).indexOf('*')
            if (pos != -1) {
                val nbs = mutableListOf<Long>()
                if (j + pos > 0 && lines[i][j + pos - 1].isDigit()) {
                    nbs.add(lines[i].substring(0, j + pos).takeLastWhile { it.isDigit() }.toLong())
                }
                if (j + pos + 1 < lines[i].length && lines[i][j + pos + 1].isDigit()) {
                    nbs.add(lines[i].substring(j + pos + 1).takeWhile { it.isDigit() }.toLong())
                }
                if (i > 0) {
                    nbs.addAll(getNbsInLineNextToEngine(lines[i - 1], j + pos))
                }
                if (i + 1 < lines.size) {
                    nbs.addAll(getNbsInLineNextToEngine(lines[i + 1], j + pos))
                }
                if (nbs.size == 2) {
                    sum += nbs.fold(1L) { acc, nb -> acc * nb }
                }
                j += pos + 1
            } else {
                j = lines[i].length
            }
        }
    }
    return sum
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day03/day03_demo_input.txt"))
    assert(demoScore == 4361)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day03/day03_demo_input.txt"))
    assert(demoScore2 == 467835L)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day03/day03_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day03/day03_input.txt"))
    println("2nd part Response is : $score2")
}