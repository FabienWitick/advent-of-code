package fab.sand.box

import java.io.File

fun getLines(path: String): List<String> {
    return File(path).readLines()
}