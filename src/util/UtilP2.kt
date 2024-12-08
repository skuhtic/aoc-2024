package util

import forEachCharWith2D


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

    operator fun unaryMinus() = P2(0 - x, 0 - y)

    operator fun minus(o: P2) = P2(x - o.x, y - o.y)
    operator fun plus(o: P2) = P2(x + o.x, y + o.y)
}



//TODO: Make P2Box - now, using P2 for this

//val Pair<Int, Int>.toP2 get() = P2(first, second)
val List<String>.sizeTo2D get() = P2(get(0).length, size)

// TODO: move to boards
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


interface P2Board<T> {
    val size: P2
    val maxCoords: P2

    operator fun get(p: P2): T?
    operator fun contains(p: P2): Boolean = p in maxCoords

    fun countExisting(alsoOutside: Boolean = false): Int

    fun forEachExisting(block: (p: P2, v: T) -> Unit)
    fun forEachInside(block: (p: P2, v: T) -> Unit) = forEachExisting { p, v -> if (p in maxCoords) block(p, v) }

    fun printBoard(info: String): Unit
}

// TODO: apply this
interface P2MutableBoard<T> : P2Board<T> {
    operator fun set(p: P2, value: T): Boolean
}


open class P2Board0 internal constructor(
    override val size: P2,
    private val fields: MutableSet<P2> = mutableSetOf(), // TODO: it was emptySet() UNMUTABLE!
) : P2MutableBoard<Boolean> {
    override val maxCoords: P2 by lazy { P2(size.x - 1, size.y - 1) }

    @Deprecated("DON'T USE: Mutable to Immutable")
    fun getDataCopy() = fields.toSet()
    fun copy() = P2Board0(size, fields.toSet().toMutableSet())

    override operator fun get(p: P2) = p in fields

    // MUTABLE
    override operator fun set(p: P2, value: Boolean) = if (value) fields.add(p) else fields.remove(p)
    override fun countExisting(all: Boolean): Int = if (all) fields.size else fields.count { it in maxCoords }

    override fun forEachExisting(block: (p: P2, v: Boolean) -> Unit) = fields.forEach { p -> block(p, true) }

    companion object {
        fun builder(size: P2, block: MutableSet<P2>.() -> Unit) = P2Board0(size, mutableSetOf<P2>().apply { block() })
        fun builder(sizeX: Int, sizeY: Int, block: MutableSet<P2>.() -> Unit) = builder(P2(sizeX, sizeY), block)
        fun from(board: P2Board0, data: MutableSet<P2> = board.fields) = P2Board0(board.size, data)

        fun from(input: List<String>, char: Char) = builder(input.sizeTo2D) {
            input.forEachCharWith2D { c, x, y -> add(P2(x, y)) }
        }

        fun from(input: List<String>, block: (Char, Int, Int) -> Boolean) = builder(input.sizeTo2D) {
            input.forEachCharWith2D { c, x, y -> add(P2(x, y)) }
        }
    }

    override fun printBoard(info: String): Unit = CharDraw(size, '.').run {
        fields.forEach { if (it in maxCoords) draw(it, '#') }
        drawIt(info)
    }

}


class P2ValueBoard<T> internal constructor(
    override val size: P2,
    private val emptyValue: T?,
    val fields: Map<P2, T>
) : P2Board<T> {
    override val maxCoords: P2 by lazy { P2(size.x - 1, size.y - 1) }
    val data: Map<P2, T> get() = fields

    override fun get(p: P2): T? = fields.getOrDefault(p, emptyValue)

    override fun countExisting(all: Boolean): Int = if (all) fields.size else fields.count { it.key in maxCoords }

    override fun forEachExisting(block: (p: P2, v: T) -> Unit): Unit = fields.forEach { (p, v) -> block(p, v) }

    companion object {
        fun <T> builder(size: P2, emptyValue: T? = null, block: MutableMap<P2, T>.() -> Unit): P2ValueBoard<T> =
            P2ValueBoard(size, emptyValue, buildMap { block() })

//        fun <T> from(board: P2ValueBoard<T>, emptyValue: T?, data: Map<P2, T> = board.data): P2ValueBoard<T> =
//            P2ValueBoard(board.size, emptyValue, data)

        fun from(input: List<String>, emptyValue: Char?): P2ValueBoard<Char> = builder(input.sizeTo2D, emptyValue) {
            input.forEachCharWith2D { c, x, y -> if (c != emptyValue) put(P2(x, y), c) }
        }
    }

    override fun printBoard(info: String): Unit = CharDraw(size, '.').run {
        fields.forEach { if (it.key in maxCoords) draw(it.key, it.value.toString().first()) }
        drawIt(info)
    }
}

data class P2DirOrthogonal(val pos: P2, val dir: P2Orthogonal1) {
    val destination: P2 get() = pos + dir.delta
    fun strait() = P2DirOrthogonal(pos + dir.delta, dir)
    fun rotateRight() = P2DirOrthogonal(pos, dir.rotateRight())
}
