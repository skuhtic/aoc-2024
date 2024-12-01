import kotlin.math.abs

fun main() {
    fun List<String>.process(): Pair<List<Int>, List<Int>> = map { line ->
        line.split("   ").let {
            require(it.size == 2)
            it.first().toInt() to it.last().toInt()
        }
    }.unzip()

    fun part1(inputLines: List<String>): Int = inputLines.process().let { lists ->
        lists.first.sorted().zip(lists.second.sorted()).sumOf { i ->
            abs(i.first - i.second)
        }
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let { lists ->
        val freq = lists.second.groupingBy { it }.eachCount()
        lists.first.sumOf { i ->
            i * freq.getOrDefault(i, 0)
        }
    }

    val testInput = d01.lines()
    part1(testInput).checkIt(s01.first)
    part2(testInput).checkIt(s01.second)

    val input = readInput("d01")
    part1(input).printIt("PART #1")
    part2(input).printIt("PART #2")
}