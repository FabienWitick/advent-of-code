package day8

import getLines

data class Tree(val height: Int, var isVisible: Boolean = false)

fun countVisibleTreeLine(trees: List<Tree>): Int {
    var minHeight = -1
    return trees.fold(0) { nb, tree ->
        if (tree.height > minHeight) {
            minHeight = tree.height
            if (tree.isVisible.not()) {
                tree.isVisible = true
                nb + 1
            } else {
                nb
            }
        } else {
            nb
        }
    }
}

fun countVisibleTrees(treeLines: List<List<Tree>>): Int = treeLines.fold(0) { total, trees ->
    total + countVisibleTreeLine(trees) + countVisibleTreeLine(trees.reversed())
}

// NoRage de mon Compute Nico
fun computeScenicScore(trees: List<Tree>, index: Int): Int {
    var score = 0
    for (i in (index + 1) until trees.size) {
        score += 1
        if (trees[index].height <= trees[i].height) {
            return score
        }
    }
    return score
}

fun ptrFunc(lines: List<String>): List<Int> {
    val rows = lines.map { line -> line.map { Tree(it.digitToIntOrNull() ?: 0) } }
    val columns = rows.indices.map { i -> rows.map { it[i] } }
    val part1Response = countVisibleTrees(rows) + countVisibleTrees(columns)

    val part2Response = rows.mapIndexed { i, trees ->
        List(trees.size) { j ->
            computeScenicScore(trees, j) * computeScenicScore(
                trees.subList(0, j + 1).reversed(),
                0
            ) * computeScenicScore(columns[j], i) * computeScenicScore(columns[j].subList(0, i + 1).reversed(), 0)
        }
    }.maxOfOrNull { it.max() } ?: 0
    return listOf(part1Response, part2Response)
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day8/day8_demo_input.txt"))
    assert(demoScore[0] == 21)
    assert(demoScore[1] == 8)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day8/day8_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
