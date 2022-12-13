package day12

import getLines

fun canGoTo(source: String, dest: String, treasures: List<Char>): Boolean {
    val lastSteps = if (treasures.contains('a')) listOf('a', 'b') else listOf('a')
    return dest.length == 1 && ((treasures.contains(dest.first()) && lastSteps.contains(source.first())) || (source.first() <= dest.first() || source.first() == dest.first() + 1))
}

/*
 * Wave Propagation like a pro, but not like Corinne, don't forget she (or he? :D) played against Ma Long
 * For each step, I print the distance traveled from the start and then I try do go to the next step with distance + 1
 */
fun findMyWayInsideThisAwesomeLabyrinth(
    map: List<MutableList<String>>,
    poses: List<Pair<Int, Int>>,
    treasures: List<Char>,
    length: Int = 1
): Int {
    val nextPoses = mutableListOf<Pair<Int, Int>>()
    for (pos in poses) {
        val x = pos.first
        val y = pos.second

        // If we reach the fabulous treasure of Zeus we return the distance traveled
        if (treasures.contains(map[x][y].first())) {
            return map[x][y].substring(1).toInt()
        }

        if (x > 0 && canGoTo(map[x][y], map[x - 1][y], treasures)) {
            map[x - 1][y] = map[x - 1][y].substring(0, 1) + length.toString()
            nextPoses.add(Pair(x - 1, y))
        }
        if (x < (map.size - 1) && canGoTo(map[x][y], map[x + 1][y], treasures)) {
            map[x + 1][y] = map[x + 1][y].substring(0, 1) + length.toString()
            nextPoses.add(Pair(x + 1, y))
        }
        if (y > 0 && canGoTo(map[x][y], map[x][y - 1], treasures)) {
            map[x][y - 1] = map[x][y - 1].substring(0, 1) + length.toString()
            nextPoses.add(Pair(x, y - 1))
        }
        if (y < (map[x].size - 1) && canGoTo(map[x][y], map[x][y + 1], treasures)) {
            map[x][y + 1] = map[x][y + 1].substring(0, 1) + length.toString()
            nextPoses.add(Pair(x, y + 1))
        }
    }
    return if (nextPoses.size > 0) {
        findMyWayInsideThisAwesomeLabyrinth(map, nextPoses, treasures, length + 1)
    } else {
        0
    }
}

fun ptrFunc(lines: List<String>, treasures: List<Char> = listOf('S')): Int {
    val map = mutableListOf<MutableList<String>>()
    var start: Pair<Int, Int> = Pair(0, 0)

    lines.forEachIndexed { i, row ->
        val j = row.indexOf('E')
        if (j != -1) {
            start = Pair(i, j)
        }
        map.add(row.map { it.toString() }.toMutableList())
    }
    map[start.first][start.second] = "z0"  // We Replace S by elevation a and distance set to 0
    return findMyWayInsideThisAwesomeLabyrinth(map, listOf(start), treasures)
}

fun main() {
    val demoLines = getLines("2022/src/main/kotlin/day12/day12_demo_input.txt")
    assert(ptrFunc(demoLines) == 31)
    assert(ptrFunc(demoLines, listOf('S', 'a')) == 29)

    val inputLines = getLines("2022/src/main/kotlin/day12/day12_input.txt")
    println("1st part Response is : ${ptrFunc(inputLines)}")
    println("2nd part Response is : ${ptrFunc(inputLines, listOf('S', 'a'))}")
}
