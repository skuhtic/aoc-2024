fun main() {
    fun List<String>.process() = this

    fun part1(inputLines: List<String>) = inputLines.process().let {
    }

    fun part2(inputLines: List<String>) = inputLines.process().let {
    }

    d08.checkAll(::part1, ::part2)
    readInput("d08").printAll(::part1, ::part2)
}