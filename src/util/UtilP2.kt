package util

/**
 * (Row, Column) coordinate in 2D grid.
 */
data class P2(val x: Int, val y: Int) {
    val l get() = P2(x - 1, y)
    val r get() = P2(x + 1, y)
    val u get() = P2(x, y - 1)
    val d get() = P2(x, y + 1)
    val lu get() = P2(x - 1, y - 1)
    val ru get() = P2(x + 1, y - 1)
    val rd get() = P2(x + 1, y + 1)
    val ld get() = P2(x - 1, y + 1)

    val orthogonal get() = setOf(l, r, u, d)
    val diagonal get() = setOf(lu, ru, rd, ld)
    val neighbours get() = orthogonal + diagonal

    operator fun minus(o: P2) = P2(x - o.x, y - o.y)
    operator fun plus(o: P2) = P2(x + o.x, y + o.y)
}


data class P2Value<T>(val xy: P2, val v: T)