fun main() {
    fun List<String>.process() = this

    fun part1(inputLines: List<String>): Int = inputLines.process().let {
        return 1
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let {
        return 1
    }

    d00.checkAll(::part1, ::part2)
    readInput("d00").printAll(::part1, ::part2)
}