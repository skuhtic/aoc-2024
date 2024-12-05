import util.*

fun main() {
    fun List<String>.process() = toP2ValueNotNull { it }

    fun part1(inputLines: List<String>): Int = inputLines.process().let { b ->
        b.mapNotNull {
            if (it.value == 'X') it.key else null
        }.sumOf { p1 ->
            p1.neighbours.mapNotNull { p2 ->
                if (b[p2] != 'M') null else {
                    val dir = p2 - p1
                    val p3 = p2 + dir
                    val p4 = p3 + dir
                    if (b[p3] == 'A' && b[p4] == 'S') 1 else null
                }
            }.count()
        }
    }

    fun part2(inputLines: List<String>): Int = inputLines.process().let { b ->
        b.mapNotNull {
            if (it.value == 'A') it.key else null
        }.count { a ->
            ((b[a.ld] == 'M' && b[a.ru] == 'S') || (b[a.ld] == 'S' && b[a.ru] == 'M')) and
                    ((b[a.lu] == 'M' && b[a.rd] == 'S') || (b[a.lu] == 'S' && b[a.rd] == 'M'))
        }
    }

    d04.checkAll(::part1, ::part2)
    readInput("d04").printAll(::part1, ::part2)
}