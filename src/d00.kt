fun main() {
    fun List<String>.process() = this

    fun part1(inputLines: List<String>): Int {
        return inputLines.process().size
    }

    fun part2(inputLines: List<String>): Int {
        return inputLines.process().size
    }

    val testInput = d00.lines()
    part1(testInput).checkIt(s00.first)
    part2(testInput).checkIt(s00.second)

    val input = readInput("d00")
    part1(input).printIt("PART #1")
    part2(input).printIt("PART #2")
}