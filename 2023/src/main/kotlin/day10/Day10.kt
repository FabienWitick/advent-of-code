package day10

import getLines

fun canGoNorth(map: List<Array<Pair<Char, Int>>>, i: Int, j: Int): Boolean {
    if (i == 0 || map[i - 1][j].second != -1 || listOf('F', '-', '7').contains(map[i][j].first)) {
        return false
    }
    return listOf('F', '|', '7').contains(map[i - 1][j].first)
}

fun canGoSouth(map: List<Array<Pair<Char, Int>>>, i: Int, j: Int): Boolean {
    if (i == map.size - 1 || map[i + 1][j].second != -1 || listOf('J', '-', 'L').contains(map[i][j].first)) {
        return false
    }
    return listOf('J', '|', 'L').contains(map[i + 1][j].first)
}

fun canGoWest(map: List<Array<Pair<Char, Int>>>, i: Int, j: Int): Boolean {
    if (j == 0 || map[i][j - 1].second != -1 || listOf('F', '|', 'L').contains(map[i][j].first)) {
        return false
    }
    return listOf('F', '-', 'L').contains(map[i][j - 1].first)
}

fun canGoEast(map: List<Array<Pair<Char, Int>>>, i: Int, j: Int): Boolean {
    if (j == map[i].size - 1 || map[i][j + 1].second != -1 || listOf('J', '|', '7').contains(map[i][j].first)) {
        return false
    }
    return listOf('J', '-', '7').contains(map[i][j + 1].first)
}

fun doStuff(map: List<Array<Pair<Char, Int>>>, i: Int, j: Int): Int {
    var dist = 0
    map[i][j] = map[i][j].copy(second = 0)

    while (true) {
        var nbOfTransformation = 0
        map.mapIndexedNotNull { index, pairs ->
            index.takeIf { pairs.find { it.second == dist } != null }
        }.forEach { i ->
            map[i].mapIndexedNotNull { index, pair -> index.takeIf { pair.second == dist } }.forEach { j ->
                if (canGoNorth(map, i, j)) {
                    map[i - 1][j] = map[i - 1][j].copy(second = dist + 1)
                    nbOfTransformation += 1
                }
                if (canGoSouth(map, i, j)) {
                    map[i + 1][j] = map[i + 1][j].copy(second = dist + 1)
                    nbOfTransformation += 1
                }
                if (canGoWest(map, i, j)) {
                    map[i][j - 1] = map[i][j - 1].copy(second = dist + 1)
                    nbOfTransformation += 1
                }
                if (canGoEast(map, i, j)) {
                    map[i][j + 1] = map[i][j + 1].copy(second = dist + 1)
                    nbOfTransformation += 1
                }
            }
        }
        if (nbOfTransformation == 0) {
            return dist
        }
        dist += 1
        nbOfTransformation = 0
    }
}

fun ptrFuncPart1(lines: List<String>, startValue: Char): Int {
    val i = lines.indexOfFirst { it.find { c -> c == 'S' } != null }
    val j = lines[i].indexOf('S')
    val map = lines.map { it.map { Pair(it, -1) }.toTypedArray() }
    map[i][j] = map[i][j].copy(startValue)
    doStuff(map, i, j)
    return map.maxOf { line ->
        line.maxOf { it.second }
    }
}

fun ptrFuncPart2(lines: List<String>, startValue: Char): Int {
    val i = lines.indexOfFirst { it.find { c -> c == 'S' } != null }
    val j = lines[i].indexOf('S')
    val map = lines.map { it.map { Pair(it, -1) }.toTypedArray() }
    map[i][j] = map[i][j].copy(startValue)
    doStuff(map, i, j)

    var nbOfPoints = 0
    map.forEach { line ->
        var intersections = 0
        line.forEach {
            if (it.second != -1 && listOf('J', '|', 'L').contains(it.first)) {
                intersections += 1
            } else if (it.second == -1 && intersections % 2 != 0) {
                nbOfPoints += 1
            }
        }
    }
    return nbOfPoints
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day10/day10_demo_input.txt"), 'F')
    assert(demoScore == 8)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day10/day10_part2_demo_input.txt"), '7')
    assert(demoScore2 == 10)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day10/day10_input.txt"), '|')
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day10/day10_input.txt"), '|')
    println("2nd part Response is : $score2")
}