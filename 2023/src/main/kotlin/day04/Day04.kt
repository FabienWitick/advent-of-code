package day04

import getLines
import kotlin.math.pow

fun ptrFuncPart1(lines: List<String>): Int {
    return lines.sumOf { line ->
        val matchResult =
            Regex("Card *([0-9]*): *(.*) [|] *(.*)").matchEntire(line)!! // Another Regex for you sweet bridou_n
        val winningNbs = matchResult.groupValues[2].split(Regex(" +")).map { it.toInt() }
        val nbs = matchResult.groupValues[3].split(Regex(" +")).map { it.toInt() }
        winningNbs.intersect(nbs.toSet()).takeIf { it.isNotEmpty() }?.let {
            2.0.pow(it.size - 1).toInt()
        } ?: 0
    }
}

fun countCard(cardNumber: Int, cardNbMap: MutableMap<Int, Int>, cardMatchesMap: Map<Int, Int>) {
    cardNbMap[cardNumber] = cardNbMap[cardNumber]?.plus(1) ?: 1
    repeat(cardMatchesMap[cardNumber]!!) {
        countCard(cardNumber + it + 1, cardNbMap, cardMatchesMap)
    }
}

fun ptrFuncPart2(lines: List<String>): Int {
    val cardMatchesMap = mutableMapOf<Int, Int>()
    val cardNbMap = mutableMapOf<Int, Int>()
    lines.forEach { line ->
        val matchResult = Regex("Card *([0-9]*): *(.*) [|] *(.*)").matchEntire(line)!!
        val cardNumber = matchResult.groupValues[1].toInt()
        val winningNbs = matchResult.groupValues[2].split(Regex(" +"))
        val nbs = matchResult.groupValues[3].split(Regex(" +"))
        cardMatchesMap[cardNumber] = winningNbs.intersect(nbs.toSet()).size
    }
    cardMatchesMap.forEach { (cardNumber, _) ->
        countCard(cardNumber, cardNbMap, cardMatchesMap)
    }
    return cardNbMap.values.fold(0) { acc, i -> acc + i }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day04/day04_demo_input.txt"))
    assert(demoScore == 13)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day04/day04_demo_input.txt"))
    assert(demoScore2 == 30)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day04/day04_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day04/day04_input.txt"))
    println("2nd part Response is : $score2")
}