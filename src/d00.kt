fun main() {
    fun List<String>.process() = this

    fun part1(inputLines: List<String>): Int {
        return inputLines.process().size
    }

    fun part2(inputLines: List<String>): Int {
        return inputLines.process().size
    }

    d00.checkAll(::part1, ::part2)
    readInput("d00").printAll(::part1, ::part2)
}