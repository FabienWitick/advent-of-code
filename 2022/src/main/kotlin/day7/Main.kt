package day7

import getLines

class Directory(private val name: String, private val parent: Directory? = null) {
    private val files = mutableListOf<String>()
    private val directories = mutableListOf<Directory>()
    internal var size = 0

    fun addFile(file: String) {
        files.add(file)
    }

    fun addDirectory(name: String) {
        directories.add(Directory(name, this))
    }

    fun cd(directoryName: String): Directory = when (directoryName) {
        ".." -> parent
        else -> directories.find { it.name == directoryName }
    } ?: this

    private fun computeFileSize(): Int = files.sumOf { file ->
        Regex("([0-9]*) (.*)").matchEntire(file)?.let { it.groupValues[1].toInt() } ?: 0
    }

    fun computeSize(): Int {
        for (directory in directories) {
            directory.computeSize().let {
                size += it
            }
        }
        size += computeFileSize()
        return size
    }

    fun getAllDirectories(): List<Directory> {
        val list = mutableListOf<Directory>()
        for (directory in directories) {
            list.addAll(directory.getAllDirectories())
        }
        list.addAll(directories)
        return list
    }
}

fun ptrFunc(lines: List<String>): List<Int> {
    val directory = Directory("/")
    var currentDirectory: Directory = directory

    lines.forEach { line ->
        when (line.substring(0..3)) {
            "$ cd" -> {
                currentDirectory = currentDirectory.cd(line.substring(5))
            }
            "$ ls" -> {}
            "dir " -> {
                currentDirectory.addDirectory(line.substring(4))
            }
            else -> {
                currentDirectory.addFile(line)
            }
        }
    }


    val totalSize = directory.computeSize()
    val directorySizes = directory.getAllDirectories().map { it.size }
    val sizeToRemove = 30_000_000 - 70_000_000 + totalSize

    val part1Response = directorySizes.filter { it <= 100_000 }.sum()
    val part2Response = directorySizes.filter { it >= sizeToRemove }.min()
    return listOf(part1Response, part2Response)
}

fun main() {
    val demoScore = ptrFunc(getLines("2022/src/main/kotlin/day7/day7_demo_input.txt"))
    assert(demoScore[0] == 95437)
    assert(demoScore[1] == 24933642)

    val score = ptrFunc(getLines("2022/src/main/kotlin/day7/day7_input.txt"))
    println("1st part Response is : ${score[0]}")
    println("2nd part Response is : ${score[1]}")
}
