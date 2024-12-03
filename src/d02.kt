fun main() {
    fun List<String>.process() = map { line ->
        line.split(' ').map { it.toInt() }.toList()
    }

    fun List<Int>.isSafe(): Boolean {
        var dir: Boolean? = null
        var lst: Int? = null
        forEachIndexed { index, i ->
            when {
                index == 0 -> lst = i
                i == lst!! -> return false
                i > lst!! + 3 -> return false
                i < lst!! - 3 -> return false
                i > lst!! -> when (dir) {
                    null -> dir = true
                    false -> return false
                    else -> Unit
                }

                else -> when (dir) {
                    null -> dir = false
                    true -> return false
                    else -> Unit
                }
            }
            lst = i
        }
        return true
    }

    fun part1(inputLines: List<String>): Int = inputLines.process().count { it.isSafe() }

    fun part2(inputLines: List<String>): Int = inputLines.process().count { report ->
        if (report.isSafe()) true else report.indices.any {
            val t = report.toMutableList()
            t.removeAt(it)
            t.isSafe()
        }
    }

    d02.checkAll(::part1, ::part2)
    readInput("d02").printAll(::part1, ::part2)
}