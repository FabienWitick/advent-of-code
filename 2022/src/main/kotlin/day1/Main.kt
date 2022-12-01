package day1

import java.io.File

fun main() {
    val inputStream = File("2022/src/main/kotlin/day1/day1_input.txt").inputStream()
    val list = mutableListOf<Int>()
    inputStream.bufferedReader().useLines { lines ->
        var currentValue = 0
        lines.forEach {
            if (it.isEmpty()) {
                insertElement(list, currentValue)
                currentValue = 0
            } else {
                currentValue += it.toInt()
            }
        }
    }

    println("1st part Response is : ${list.first()}")
    println("2nd part Response is : ${list[0] + list[1] + list[2]}")
}

fun insertElement(list: MutableList<Int>, value: Int) {
    for (i in list.indices) {
        if (value > list[i]) {
            list.add(i, value)
            return
        }
    }
    list.add(value)
}