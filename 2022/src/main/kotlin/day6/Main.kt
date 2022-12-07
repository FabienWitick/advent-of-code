package day6

import getLines
import kotlin.math.min

fun ptrFunc(input: String, stepSize: Int = 4): Int {
    var index = 0
    while (index in input.indices) {
        val end = min(index + stepSize, input.length)
        input.substring(index, end).toSet().let {
            if (it.size == stepSize) {
                return index + stepSize
            }
        }
        index += 1
    }
    return 0
}

fun main() {
    assert(ptrFunc("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    assert(ptrFunc("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    assert(ptrFunc("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    assert(ptrFunc("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    assert(ptrFunc("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)
    assert(ptrFunc("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) == 19)
    assert(ptrFunc("bvwbjplbgvbhsrlpgdmjqwftvncz", 14) == 23)
    assert(ptrFunc("nppdvjthqldpwncqszvftbrmjlhg", 14) == 23)
    assert(ptrFunc("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14) == 29)
    assert(ptrFunc("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14) == 26)

    println("1st part Response is : ${ptrFunc(getLines("2022/src/main/kotlin/day6/day6_input.txt").first())}")
    println("2nd part Response is : ${ptrFunc(getLines("2022/src/main/kotlin/day6/day6_input.txt").first(), 14)}")
}
