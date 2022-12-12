package day11

import getLines

data class Monkey(
    val name: String,
    val items: MutableList<Long> = mutableListOf(),
    var operation: (Long) -> Long = { 0 },
    var divisor: Int = 1,
    var throwItem: (Long, List<Monkey>) -> Unit = { _, _ -> },
    var inspectedItems: Long = 0
) {
    fun throwItems(myMonkeyGang: List<Monkey>, worryLevelDivider: Int) {
        val itemIterator = items.iterator()
        while (itemIterator.hasNext()) {
            throwItem(operation(itemIterator.next()) / worryLevelDivider, myMonkeyGang)
            itemIterator.remove()
            inspectedItems += 1
        }
    }
}

fun buildOperation(line: String): (Long) -> Long {
    val operands = Regex(".* = (old|[0-9]*) (\\+|\\*) (old|[0-9]*)").matchEntire(line)?.groupValues ?: listOf() // More Regex
    return {
        val a = if (operands[1] == "old") { it } else { operands[1].toLong() }
        val b = if (operands[3] == "old") { it } else { operands[3].toLong() }
        if (operands[2] == "+") { a + b } else { a * b }
    }
}

// Les compute me manquent
fun buildThrowAction(line: String, divider: Int, positiveIndex: Int): (Long, List<Monkey>) -> Unit {
    return { item, monkeys ->
        val nb = item % monkeys.map { it.divisor }.reduce { acc, divisor -> acc * divisor }
        if (item.mod(divider.toLong()) == 0L) {
            monkeys[positiveIndex].items.add(nb)
        } else {
            monkeys[Regex(".* ([0-9]*)").matchEntire(line)?.groupValues?.get(1)?.toInt() ?: 0].items.add(nb) // More Regex
        }
    }
}

fun ptrFunc(lines: List<String>, worryLevelDivider: Int = 3, rounds: Int = 20): Long {
    val monkeys = mutableListOf<Monkey>()
    var currentPositiveMonkeyIndex = 0

    lines.forEach { line ->
        if (line.startsWith("Monkey")) {
            monkeys.add(Monkey(line[7].toString()))
        }
        if (line.contains("items")) {
            monkeys.last().items.addAll(
                (Regex(".*: ([0-9].*)").matchEntire(line)?.groupValues?.get(1)?.split(", ") // More Regex
                    ?: listOf()).map { it.toLong() })
        }
        if (line.contains("Operation")) {
            monkeys.last().operation = buildOperation(line)
        }
        if (line.contains("Test")) {
            monkeys.last().divisor = Regex(".* ([0-9]*)").matchEntire(line)?.groupValues?.get(1)?.toInt() ?: 1 // More Regex
        }
        if (line.contains("If true")) {
            currentPositiveMonkeyIndex = Regex(".* ([0-9]*)").matchEntire(line)?.groupValues?.get(1)?.toInt() ?: 1 // More Regex
        }
        if (line.contains("If false")) {
            monkeys.last().throwItem = buildThrowAction(line, monkeys.last().divisor, currentPositiveMonkeyIndex)
        }
    }

    repeat(rounds) {
        for (monkey in monkeys) {
            monkey.throwItems(monkeys, worryLevelDivider)
        }
    }
    return monkeys.sortedByDescending { it.inspectedItems }.map { it.inspectedItems }.subList(0, 2).reduce { acc, nb -> acc * nb }
}

// Can you guess how many RegEx did I use?
fun main() {
    val demoLines = getLines("2022/src/main/kotlin/day11/day11_demo_input.txt")
    assert(ptrFunc(demoLines) == 10605L)
    assert(ptrFunc(demoLines, 1, 10_000) == 2_713_310_158)

    val lines = getLines("2022/src/main/kotlin/day11/day11_input.txt")
    println("1st part Response is : ${ptrFunc(lines)}")
    println("2nd part Response is : ${ptrFunc(lines, 1, 10_000)}")
}
