fun main() {
    fun List<String>.process() = joinToString(" ")

    val re = Regex("""mul[(](\d{1,3}),(\d{1,3})[)]""")
    val reStarts = Regex("""do[(][)]""")
    val reStops = Regex("""don't[(][)]""")

    fun part1(inputLines: List<String>): Int = inputLines.process().let { xx ->
        return re.findAll(xx).sumOf { mr ->
            mr.groupValues.let {
                it[1].toInt() * it[2].toInt()
            }
        }
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let { xx ->
        val starts = reStarts.findAll(xx).map { it.groups.first()!!.range.first }
        val exclude = reStops.findAll(xx).map { mr ->
            val s = mr.groups.first()!!.range.first
            IntRange(s, starts.dropWhile { it <= s }.first())
        }
        return re.findAll(xx).sumOf { mr ->
            mr.groups.let { mgc ->
                if (exclude.any { mgc[0]!!.range.first in it }) 0
                else mgc[1]!!.value.toInt() * mgc[2]!!.value.toInt()
            }
        }
    }

    d03_1.check1st(::part1)
    d03_2.check2nd(::part2)
    readInput("d03").printAll(::part1, ::part2)
}