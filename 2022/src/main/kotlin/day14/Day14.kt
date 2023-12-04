package day14

import getLines
import java.lang.Integer.max
import java.lang.Integer.min

fun ptrFunc(lines: List<String>): List<Int> {
    val pairs = mutableSetOf<Pair<Int, Int>>()

    lines.forEach {line ->
        line.split(" -> ").windowed(2, 1) { coords ->
            val a = coords.first().split(",").let { Pair(it.first().toInt(), it[1].toInt()) }
            val b = coords[1].split(",").let { Pair(it.first().toInt(), it[1].toInt()) }
            println("$a $b")

            if (a.first == b.first){
                pairs.addAll((min(a.second, b.second)..max(a.second, b.second)).map { Pair(a.first, it) })
            } else {
                pairs.addAll((min(a.first, b.first)..max(a.first, b.first)).map { Pair(it, a.second) })
            }
        }
    }
    println(pairs)
    return listOf(
        0,
        0
    )
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day14/day14_demo_input.txt"))
    assert(demoScore[0] == 24)
//    assert(demoScore[1] == 140)

//    val score = ptrFunc(getLines("2022/src/main/kotlin/day14/day14_input.txt"))
//    println("1st part Response is : ${score[0]}")
//    println("2nd part Response is : ${score[1]}")
}
