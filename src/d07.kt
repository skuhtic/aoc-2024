
fun main() {
    fun List<String>.process() = map {
        it.substringBefore(": ").toLong() to
                it.substringAfter(": ").split(' ').map { it.toInt() }
    }

    fun testBinary(target: Long, ns: List<Int>, ops: List<Boolean>): Boolean {
        if (ns.size == ops.size + 1) {
            val t = ns.drop(1).zip(ops)
            val ret = t.fold(ns.first().toLong()) { acc, (x, op) ->
                if (op) acc * x else acc + x
            }
            return ret == target
        } else {
            if (testBinary(target, ns, ops + true)) return true
            if (testBinary(target, ns, ops + false)) return true
            return false
        }
    }

    fun testTrinary(target: Long, ns: List<Int>, ops: List<Boolean?>): Boolean {
        if (ns.size == ops.size + 1) {
            val t = ns.drop(1).zip(ops)
            val ret = t.fold(ns.first().toLong()) { acc, (x, op) ->
                when (op) {
                    true -> acc * x
                    false -> acc + x
                    null -> (acc.toString() + x.toString()).toLong()
                }
            }
            return ret == target
        } else {
            if (testTrinary(target, ns, ops + true)) return true
            if (testTrinary(target, ns, ops + false)) return true
            if (testTrinary(target, ns, ops + null)) return true
            return false
        }
    }

    fun part1(inputLines: List<String>) = inputLines.process().sumOf { (res, nums) ->
        if (testBinary(res, nums, emptyList())) res else 0L
    }

    fun part2(inputLines: List<String>) = inputLines.process().sumOf { (res, nums) ->
        if (testTrinary(res, nums, emptyList())) res else 0L
    }

    d07.checkAll(::part1, ::part2)
    readInput("d07").printAll(::part1, ::part2)
}