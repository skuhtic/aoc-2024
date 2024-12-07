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

//val Pair<Int, Int>.toP2 get() = P2(first, second)
val List<String>.sizeTo2D get() = P2(get(0).length, size)

operator fun P2.contains(other: P2): Boolean = (other.x >= 0 && other.y >= 0 && other.x <= x && other.y <= x)


interface P2Move {
    val delta: P2
}

enum class P2Orthogonal1(override val delta: P2) : P2Move {
    Le(P2(-1, 0)),
    Ri(P2(1, 0)),
    Up(P2(0, -1)),
    Do(P2(0, 1)),
    ;

    fun rotateRight() = when (this) {
        Le -> Up
        Up -> Ri
        Ri -> Do
        Do -> Le
    }
}

enum class P2Move1(override val delta: P2) : P2Move {
    Le(P2(-1, 0)),
    Ri(P2(1, 0)),
    Up(P2(0, -1)),
    Do(P2(0, 1)),
    LU(P2(-1, -1)),
    RU(P2(1, -1)),
    LD(P2(-1, 1)),
    DD(P2(1, 1)),
}

data class P2Value<T>(val pos: P2, val v: T)

data class P2Board0 private constructor(
    val data: Set<P2>,
    val size2: P2,
) {
    val border: P2 = P2(size2.x-1, size2.y -1)

    operator fun get(p: P2) = p in data
    operator fun get(x: Int, y: Int) = get(P2(x, y))

    operator fun contains(p: P2) = p in border

    companion object {
        fun builder(size: P2, block: MutableSet<P2>.() -> Unit) = buildSet {
            block()
        }.let { P2Board0(it, size) }

        fun builder(sizeX: Int, sizeY: Int, block: MutableSet<P2>.() -> Unit) = builder(P2(sizeX, sizeY), block)

        fun from(board: P2Board0, data: Set<P2> = board.data) = P2Board0(data, board.size2)

        fun from(input: List<String>, char: Char) = input.let {
            val s = it.sizeTo2D
            val b = buildSet {
                it.forEachIndexed { y, s ->
                    s.forEachIndexed { x, c ->
                        if (c == char) add(P2(x, y))
                    }
                }
            }
            P2Board0(b, s)
        }

        fun from(input: List<String>, block: (Char, Int, Int) -> Boolean) = input.let {
            val s = it.sizeTo2D
            val b = buildSet {
                it.forEachIndexed { y, s ->
                    s.forEachIndexed { x, c ->
                        if (block(c, x, y)) add(P2(x, y))
                    }
                }
            }
            P2Board0(b, s)
        }
    }
}

data class P2ValueBoard<T>(
    val size: P2,
    private val emptyValue: T,
    private val board: MutableSet<P2Value<T>> = mutableSetOf()
) {
    companion object {
//        fun <T> from(emptyValue: T, input: List<String>) = input.let {
//            val s = ()
//        }
    }
}

data class P2DirOrthogonal(val pos: P2, val dir: P2Orthogonal1) {
    val destination: P2 get() = pos + dir.delta
    fun strait() = P2DirOrthogonal(pos + dir.delta, dir)
    fun rotateRight() = P2DirOrthogonal(pos, dir.rotateRight())
}
