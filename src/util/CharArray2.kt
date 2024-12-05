package util

typealias CharArray2 = Array<CharArray>

fun List<String>.toCharArray2() = Array(size) { get(it).toCharArray() }

fun <T> List<String>.toP2ValueNotNull(transform: (c: Char) -> T): Map<P2, T> {
    val ret = mutableMapOf<P2, T>()
    val ySize = size
    var xSize: Int? = null
    forEachIndexed { y, line ->
        if (xSize == null) xSize = line.length
        require(xSize == line.length) { "Row has size ${line.length}, but expected $xSize" }
        line.forEachIndexed { x, c ->
            transform(c)?.let {
                ret[P2(x, y)] = it
            }
        }
    }
    require(ret.size == xSize!! * ySize)
    return ret
}

fun CharArray2.size2(): P2 {
    val y = size
    val x = get(0).size
    for (i in 1..<y) require(get(i).size == x) { "Row $i has size ${get(i)}, but expected $x" }
    return P2(x, y)
}

inline fun CharArray2.forEachIndexed2(action: (i: Int, j: Int, c: Char) -> Unit) {
    for (i in indices) {
        val b = get(i)
        for (j in b.indices) {
            action(i, j, b[j])
        }
    }
}

inline fun <R> CharArray2.mapIndexed2NotNull(transform: (i: Int, j: Int, c: Char) -> R?): List<R> =
    buildList {
        forEachIndexed2 { i, j, c ->
            transform(i, j, c)?.let { add(it) }
        }
    }

inline fun CharArray2.toListOfP2If(predicate: (c: Char) -> Boolean): List<P2> = buildList {
    forEachIndexed2 { y, x, c ->
        if (predicate(c)) add(P2(x, y))
    }
}
