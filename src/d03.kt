fun main() {
    fun List<String>.process() = joinToString("")

    val re = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val reStarts = Regex("""do\(\)""")
    val reStops = Regex("""don't\(\)""")

    fun part1(inputLines: List<String>): Int = inputLines.process().let { line ->
        return re.findAll(line).sumOf { mr ->
            mr.destructured.let { (x, y) -> x.toInt() * y.toInt() }
        }
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let { line ->
        val starts = reStarts.findAll(line).map { it.range.first }
        val exclude = reStops.findAll(line).map { mr ->
            val s = mr.range.first
            IntRange(s, starts.dropWhile { it <= s }.first())
        }
        return re.findAll(line).sumOf { mr ->
            if (exclude.any { mr.range.first in it }) 0
            else mr.destructured.let { (x, y) -> x.toInt() * y.toInt() }
        }
    }

    d03_1.check1st(::part1)
    d03_2.check2nd(::part2)
    readInput("d03").printAll(::part1, ::part2)
}