fun main() {
    fun List<String>.process() = map { line ->
        line.split(' ').map { it.toInt() }.toList()
    }

    fun List<Int>.isSafe(): Boolean {
        var dir: Boolean? = null

        println()
        zipWithNext { a, b ->
            when(b - a) {
                in 1..3 -> if(dir == null) dir = true else if(dir == false) return false else Unit
                in -3..-1 -> if(dir == null) dir = false else if(dir == true) return false else Unit
                else -> return false
            }
        }
        return true
    }

    fun part1(inputLines: List<String>): Int = inputLines.process().count { it.isSafe() }

    fun part2(inputLines: List<String>): Int = inputLines.process().count { report ->
        if (report.isSafe()) true else report.indices.any {
            report.toMutableList().apply { removeAt(it) }.isSafe()
        }
    }

    d02.checkAll(::part1, ::part2)
    readInput("d02").printAll(::part1, ::part2)
}