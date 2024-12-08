package util

// TODO: Optimize for memory and performance
class CharDraw(val size: P2, val back:Char) {
    val screen = List(size = size.y) { back.toString().repeat(size.x).toCharArray() }

    operator fun set(pos: P2, c: Char) {
        screen[pos.y][pos.x] = c
    }

    fun draw(pos: P2, c: Char) {
        this[pos] = c
    }

    fun draw(data: Collection<P2>, c: Char) {
        data.forEach { pos ->
            this[pos] = c
        }
    }

    fun drawIt(info: String) {
        println(info)
        screen.forEach { println(it) }
        println()
    }

    companion object {
        fun fromP2Board0(b :P2Board0) = CharDraw(b.size, '.').apply {
            draw(b.getDataCopy(), '#')
        }
    }
}