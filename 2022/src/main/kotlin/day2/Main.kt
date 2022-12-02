package day2

import java.io.File
import java.io.FileInputStream

class Weapon constructor(
    val name: String,
    private val weakerWeapon: String,
    private val strongerWeapon: String,
    private val points: Int
) {

    fun fightWeapon(weapon: String): Int {
        return when (weapon) {
            strongerWeapon -> 0 // Loss
            name -> 3           // Draw
            weakerWeapon -> 6   // Win
            else -> 0
        } + points
    }

    /*
     * Possible points Array
     *      | X | Y | Z |
     * =================|
     * A(1) | 3 | 1 | 2 |
     * B(2) | 1 | 2 | 3 |
     * C(3) | 2 | 3 | 1 |
     *
     * There is a pattern for each strategy
     *
     */
    fun secondFight(strategy: String): Int {
        return when (strategy) {
            "X" -> (points + 1) % 3 + 1 + 0 // Lose
            "Y" -> points + 3               // Draw
            "Z" -> points % 3 + 1 + 6       // Win
            else -> 0
        }
    }
}

fun xyzToAbc(value: String): String {
    return when (value) {
        "X" -> "A"
        "Y" -> "B"
        "Z" -> "C"
        else -> ""
    }
}

fun ptrFunc(inputStream: FileInputStream): List<Int> {
    val weapons = listOf(
        Weapon("A", "C", "B", 1),
        Weapon("B", "A", "C", 2),
        Weapon("C", "B", "A", 3)
    )
    var firstPartPoints = 0
    var secondPartPoints = 0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            // First part
            weapons.find { it.name == xyzToAbc(line[2].toString()) }?.let { weapon ->
                firstPartPoints += weapon.fightWeapon(line[0].toString())
            }

            // Second part
            weapons.find { it.name == line[0].toString() }?.let { weapon ->
                secondPartPoints += weapon.secondFight(line[2].toString())
            }
        }
    }
    return listOf(firstPartPoints, secondPartPoints)
}

fun main() {
    val demoScore = ptrFunc(File("2022/src/main/kotlin/day2/day2_demo_input.txt").inputStream())
    println("1st part Demo Response is : ${demoScore[0]}") // Should be 15
    println("2nd part Demo Response is : ${demoScore[1]}") // Should be 12

    val score = ptrFunc(File("2022/src/main/kotlin/day2/day2_input.txt").inputStream())
    println("1st part Response is : ${score[0]}") // Should be 11666
    println("2nd part Response is : ${score[1]}") // Should be 12767
}
