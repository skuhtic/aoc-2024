import kotlin.math.abs

fun main() {
    fun List<String>.process(): Pair<List<Int>, List<Int>> = map { line ->
        line.split("   ").let {
            require(it.size == 2)
            it.first().toInt() to it.last().toInt()
        }
    }.unzip()

    fun part1(inputLines: List<String>): Int = inputLines.process().let { lists ->
        lists.first.sorted().zip(lists.second.sorted()).sumOf { (first, second) ->
            abs(first - second)
        }
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let { lists ->
        val freq = lists.second.groupingBy { it }.eachCount()
        lists.first.sumOf { i ->
            i * freq.getOrDefault(i, 0)
        }
    }

    d01.checkAll(::part1, ::part2)
    readInput("d01").printAll(::part1, ::part2)
}