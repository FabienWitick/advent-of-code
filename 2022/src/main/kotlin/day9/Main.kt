package day9

import getLines
import kotlin.math.abs

data class Knot(var x: Int = 0, var y: Int = 0) {
    fun move(direction: Char) {
        when (direction) {
            'R' -> x += 1
            'L' -> x -= 1
            'U' -> y += 1
            'D' -> y -= 1
        }
    }

    fun moveTowards(knot: Knot) {
        if (abs(knot.x - x) > 1 && knot.y == y) {
            x += if (knot.x > x) 1 else -1
        } else if (abs(knot.y - y) > 1 && knot.x == x) {
            y += if (knot.y > y) 1 else -1
        } else if (knot.x != x && knot.y != y && (abs(knot.y - y) > 1 || abs(knot.x - x) > 1)) {
            x += if (knot.x > x) 1 else -1
            y += if (knot.y > y) 1 else -1
        }
    }
}

fun ptrFunc(lines: List<String>, nbOfKnots: Int = 2): Int {
    val knots = List(nbOfKnots) { Knot(0, 0) }
    val tailUniquePositions = mutableSetOf<Knot>()

    lines.forEach { line ->
        repeat(line.substring(2).toInt()) {
            knots.first().move(line[0])
            for (i in 1 until knots.size) {
                knots[i].moveTowards(knots[i - 1])
            }
            tailUniquePositions.add(knots.last().copy())
        }
    }
    return tailUniquePositions.size
}

fun main() {
    assert(ptrFunc(getLines("2022/src/main/kotlin/day9/day9_demo_input.txt")) == 13)
    assert(ptrFunc(getLines("2022/src/main/kotlin/day9/day9_demo_2_input.txt"), 10) == 36)

    val input = getLines("2022/src/main/kotlin/day9/day9_input.txt")
    println("1st part Response is : ${ptrFunc(input)}")
    println("2nd part Response is : ${ptrFunc(input, 10)}")
}
