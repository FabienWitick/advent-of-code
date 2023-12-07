package day05

import getLines


data class AlmanacLine(
    var sourceRange: Long,
    var destRange: Long,
    var length: Long
)

fun computeNextRanges(seeds: MutableList<Long>, maps: List<AlmanacLine>) {
    seeds.forEachIndexed { i, range ->
        seeds[i] = maps.firstOrNull { range >= it.sourceRange && range < it.sourceRange + it.length }?.let {
            it.destRange + range - it.sourceRange
        } ?: range
    }
}

fun ptrFuncPart1(lines: List<String>): Long {
    val seeds = Regex("seeds: (.*)").matchEntire(lines.first())!!.let { matchResult ->
        matchResult.groupValues[1].split(" ").map { it.toLong() }
    }.toMutableList()

    val maps = mutableListOf<AlmanacLine>()
    lines.subList(3, lines.size).forEach { line ->
        if (line.isNotEmpty() && line.first().isDigit()) {
            line.split(" ").map { it.toLong() }.also {
                maps.add(AlmanacLine(sourceRange = it[1], destRange = it[0], length = it[2]))
            }
        } else if (line.isEmpty()) {
            computeNextRanges(seeds, maps)
            maps.clear()
        }
    }
    computeNextRanges(seeds, maps)
    return seeds.min()
}

data class SeedRanges(
    val min: Long,
    val max: Long
)

fun computeSeedRanges(seeds: MutableList<SeedRanges>, maps: List<AlmanacLine>) {
    val nextSeedRanges = mutableListOf<SeedRanges>()

    maps.forEach { map ->
        val seedsIterator = seeds.listIterator()
        val seedsWithSameRange = mutableListOf<SeedRanges>()
        while (seedsIterator.hasNext()) {
            val seed = seedsIterator.next()
            if (seed.min < map.sourceRange && seed.max >= map.sourceRange) {
                seedsWithSameRange.add(SeedRanges(min = seed.min, max = map.sourceRange - 1))

                if (seed.max < map.sourceRange + map.length) {
                    nextSeedRanges.add(
                        SeedRanges(min = map.destRange, max = map.destRange + (seed.max - map.sourceRange))
                    )
                } else {
                    nextSeedRanges.add(
                        SeedRanges(min = map.destRange, max = map.destRange + map.length - 1)
                    )
                    seedsWithSameRange.add(SeedRanges(min = map.sourceRange + map.length, max = seed.max))
                }
                seedsIterator.remove()
            } else if (seed.min >= map.sourceRange && seed.min < map.sourceRange + map.length) {
                if (seed.max < map.sourceRange + map.length) {
                    nextSeedRanges.add(
                        SeedRanges(
                            min = map.destRange + (seed.min - map.sourceRange),
                            max = map.destRange + (seed.max - map.sourceRange)
                        )
                    )
                } else {
                    nextSeedRanges.add(
                        SeedRanges(
                            min = map.destRange + (seed.min - map.sourceRange),
                            max = map.destRange + map.length - 1
                        )
                    )
                    seedsWithSameRange.add(SeedRanges(min = map.sourceRange + map.length, max = seed.max))
                }
                seedsIterator.remove()
            }
        }
        seeds.addAll(seedsWithSameRange)
    }
    seeds.addAll(nextSeedRanges)
}

fun ptrFuncPart2(lines: List<String>): Long {
    val seeds = Regex("seeds: (.*)").matchEntire(lines.first())!!.let { matchResult ->
        matchResult.groupValues[1].split(" ").windowed(2, 2).map {
            val min = it[0].toLong()
            SeedRanges(min = min, max = min + it[1].toLong() - 1)
        }
    }.toMutableList()

    val maps = mutableListOf<AlmanacLine>()
    lines.subList(3, lines.size).forEach { line ->
        if (line.isNotEmpty() && line.first().isDigit()) {
            line.split(" ").map { it.toLong() }.also {
                maps.add(AlmanacLine(sourceRange = it[1], destRange = it[0], length = it[2]))
            }
        } else if (line.isEmpty()) {
            computeSeedRanges(seeds, maps)
            maps.clear()
        }
    }
    computeSeedRanges(seeds, maps)
    return seeds.minOf { it.min }
}

fun main() {
    val demoScore = ptrFuncPart1(getLines("2023/src/main/kotlin/day05/day05_demo_input.txt"))
    assert(demoScore == 35L)
    val demoScore2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day05/day05_demo_input.txt"))
    assert(demoScore2 == 46L)

    val score = ptrFuncPart1(getLines("2023/src/main/kotlin/day05/day05_input.txt"))
    println("1st part Response is : $score")
    val score2 = ptrFuncPart2(getLines("2023/src/main/kotlin/day05/day05_input.txt"))
    println("2nd part Response is : $score2")
}