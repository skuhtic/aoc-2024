import util.P2Board0
import util.P2ValueBoard
import util.forEachSelfPaired

fun main() {
    data class ResonantCollinearity(val antennas: P2ValueBoard<Char>) {
        val antennaGroups = antennas.data.entries.groupBy { it.value }.mapValues { it.value.map { it.key } }

        fun part1(): Int {
            val antiNodes = P2Board0(antennas.size)
            antennaGroups.values.forEach { same ->
                same.forEachSelfPaired(false) { p1, p2 ->
                    val dist = p2 - p1
                    antiNodes[p2 + dist] = true
                    antiNodes[p1 - dist] = true
                }
            }
            return antiNodes.countExisting()
        }

        fun part2(): Int {
            val antiNodes = P2Board0(antennas.size)
            antennaGroups.values.forEach { same ->
                same.forEachSelfPaired(false) { p1, p2 ->
                    val dist = p2 - p1
                    generateSequence(p1) { it - dist }.takeWhile { it in antennas }.forEach { antiNodes[it] = true }
                    generateSequence(p2) { it + dist }.takeWhile { it in antennas }.forEach { antiNodes[it] = true }
                }
            }
            return antiNodes.countExisting()
        }
    }

    fun List<String>.process() = ResonantCollinearity(P2ValueBoard.from(this, '.'))
    fun part1(inputLines: List<String>) = inputLines.process().part1()
    fun part2(inputLines: List<String>) = inputLines.process().part2()
    d08.checkAll(::part1, ::part2)
    readInput("d08").printAll(::part1, ::part2)
}