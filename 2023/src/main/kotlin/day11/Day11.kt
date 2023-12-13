package day11

import getLines

fun expendUniverse(universe: List<String>, expenseRatio: Int = 2): List<Array<Pair<Char, Long>>> {
    return universe.map { row ->
        row.mapIndexed { index, c ->
            if (c == '#') {
                Pair(c, -1L)
            } else if (universe.filter { s -> s[index] == '.' }.size == universe.size) { // check if column is full of void spaces
                Pair('.', expenseRatio.toLong())
            } else {
                Pair('.', 1L)
            }
        }.toTypedArray()
    }
}

fun getGalaxyDistances(universe: List<Array<Pair<Char, Long>>>, i: Int, j: Int, expenseRatio: Int = 2): Long {
    var sum = 0L
    universe[i][j] = universe[i][j].copy(second = 1)
    universe.forEachIndexed { x, row ->
        row.mapIndexedNotNull { index, pair -> index.takeIf { pair.second == -1L } }.forEach { y ->
            sum += row.toList().subList(if (y < j) y else j, if (y < j) j else y)
                .sumOf { if (it.second == -1L) 1 else it.second }
            sum += universe.subList(i, x).sumOf {
                if (it.all { pair -> pair.first == '.' }) expenseRatio else 1
            }
        }
    }
    return sum
}

fun ptrFunc(lines: List<String>, expenseRatio: Int): Long {
    val universe = expendUniverse(lines, expenseRatio)
    return universe.mapIndexed { i, row ->
        row.mapIndexedNotNull { index, pair -> index.takeIf { pair.second == -1L } }.sumOf { j ->
            getGalaxyDistances(universe, i, j, expenseRatio)
        }
    }.sum()
}

fun main() {
    val demoScore = ptrFunc(getLines("2023/src/main/kotlin/day11/day11_demo_input.txt"), 2)
    assert(demoScore == 374L)
    val demoScore2 = ptrFunc(getLines("2023/src/main/kotlin/day11/day11_demo_input.txt"), 100)
    assert(demoScore2 == 8410L)

    val score = ptrFunc(getLines("2023/src/main/kotlin/day11/day11_input.txt"), 2)
    println("1st part Response is : $score")
    val score2 = ptrFunc(getLines("2023/src/main/kotlin/day11/day11_input.txt"), 1_000_000)
    println("2nd part Response is : $score2")
}