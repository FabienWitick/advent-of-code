package day07

import getLines

val hands = listOf(
    listOf(1, 1, 1, 1, 1), // Hight card
    listOf(2, 1, 1, 1),    // One pair
    listOf(2, 2, 1),       // Two paris
    listOf(3, 1, 1),       // Three of a kind
    listOf(3, 2),          // Full house
    listOf(4, 1),          // Four of a kind
    listOf(5),             // Five of a kind
)

data class Hand(val hand: String, val handValue: MutableList<Int>, val bid: Int)

fun compareTwoHands(h1: Hand, h2: Hand, cards: String): Int {
    val compare = hands.indexOfFirst { it == h1.handValue } - hands.indexOfFirst { it == h2.handValue }
    if (compare == 0) {
        h1.hand.forEachIndexed { i, c ->
            if (c != h2.hand[i]) {
                return cards.indexOf(c) - cards.indexOf(h2.hand[i])
            }
        }
    }
    return compare
}

fun ptrFuncPart1(lines: List<String>): Int {
    val cards = "23456789TJQKA"
    return lines.map { line ->
        line.split(" ").let {
            Hand(it[0], it[0].groupBy { it }.map { it.value.size }.sortedDescending().toMutableList(), it[1].toInt())
        }
    }.sortedWith { h1, h2 ->
        compareTwoHands(h1, h2, cards)
    }.foldIndexed(0) { i, acc, hand ->
        acc + (i + 1) * hand.bid
    }
}

fun ptrFuncPart2(lines: List<String>): Int {
    val cards = "J23456789TQKA"
    return lines.map { line ->
        line.split(" ").let {
            val nbOfJ = line.count { it == 'J' }
            val lineWithoutJ = it[0].replace("J", "")
            Hand(
                it[0],
                lineWithoutJ.groupBy { it }.map { it.value.size }.sortedDescending().toMutableList(),
                it[1].toInt()
            ).also {
                if (it.handValue.size == 0) { // it means the hand is full of J
                    it.handValue.add(5)
                } else {
                    it.handValue[0] += nbOfJ
                }
            }
        }
    }.sortedWith { h1, h2 ->
        compareTwoHands(h1, h2, cards)
    }.foldIndexed(0) { i, acc, hand ->
        acc + (i + 1) * hand.bid
    }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day07/day07_demo_input.txt"))
    assert(demoScore == 6440)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day07/day07_demo_input.txt"))
    assert(demoScore2 == 5905)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day07/day07_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day07/day07_input.txt"))
    println("2nd part Response is : $score2")
}