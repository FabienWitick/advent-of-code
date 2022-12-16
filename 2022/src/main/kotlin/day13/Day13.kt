package day13

import getLines

fun indexOfClosingBracket(list: String): Int {
    var openedBrackets = 1
    var closedBrackets = 0

    for (i in list.indices) {
        when (list[i]) {
            '[' -> openedBrackets += 1
            ']' -> closedBrackets += 1
            else -> {}
        }
        if (openedBrackets == closedBrackets) {
            return i + 1
        }
    }
    return -1
}

fun nbOfDigits(str: String): Int {
    for (i in str.indices) {
        if (str[i].isDigit().not()) {
            return i
        }
    }
    return str.length
}

fun build(line: String): List<Any> {
    val list = mutableListOf<Any>()

    var tmp = line
    while (tmp.isNotEmpty()) {
        tmp = when (tmp.first()) {
            '[' -> {
                val indexOfClosingBracket = indexOfClosingBracket(tmp.substring(1))
                list.add(build(tmp.substring(1, indexOfClosingBracket)))
                tmp.substring(indexOfClosingBracket + 1)
            }

            ',' -> tmp.substring(1)
            else -> {
                val nbOfDigits = nbOfDigits(tmp)
                list.add(tmp.substring(0, nbOfDigits).toInt())
                tmp.substring(nbOfDigits)
            }
        }
    }
    return list
}

fun compare(left: List<Any>, right: List<Any>): Int {
    val leftIterator = left.iterator()
    val rightIterator = right.iterator()
    while (leftIterator.hasNext()) {
        if (rightIterator.hasNext().not()) {
            return 1
        }

        var leftValue = leftIterator.next()
        var rightValue = rightIterator.next()
        if (leftValue is Int && rightValue is Int) {
            if (leftValue > rightValue) {
                return 1
            }
            if (leftValue < rightValue) {
                return -1
            }
        } else {
            if (leftValue is Int) {
                leftValue = listOf(leftValue)
            }
            if (rightValue is Int) {
                rightValue = listOf(rightValue)
            }
            val compare = compare(leftValue as List<Any>, rightValue as List<Any>)
            if (compare == 1) {
                return 1
            }
            if (compare == -1) {
                return -1
            }
        }
    }
    return if (rightIterator.hasNext()) -1 else 0
}

fun ptrFunc(lines: List<String>): List<Int> {
    val packets = mutableListOf<List<Any>>()

    var index = 1
    val indexesInRightOrder = mutableListOf<Int>()
    lines.forEach { line ->
        if (line.isNotEmpty()) {
            packets.add(build(line.substring(1, line.length - 1))) // remove enclosing brackets
        } else {
            if (compare(packets[packets.size - 2], packets.last()) <= 0) {
                indexesInRightOrder.add(index)
            }
            index += 1
        }
    }
    if (compare(packets[packets.size - 2], packets.last()) <= 0) {
        indexesInRightOrder.add(index)
    }

    // Part 2
    packets.add(build("[2]"))
    packets.add(build("[6]"))
    return listOf(
        indexesInRightOrder.sum(),
        packets.sortedWith { left, right -> compare(left, right) }
            .map { it.toString() }
            .let { (it.indexOf("[[2]]") + 1) * (it.indexOf("[[6]]") + 1) }
    )
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day13/day13_demo_input.txt"))
    assert(demoScore[0] == 13)
    assert(demoScore[1] == 140)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day13/day13_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
