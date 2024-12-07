import kotlinx.coroutines.*
import util.*
import util.P2Orthogonal1.*

fun main() {
    data class GuardGallivant2(
        val obstacles: P2Board0,
        val guard: P2DirOrthogonal,
    ) {
        val initPath by lazy { path().toList() }
        fun path(
            obstacleAdded: P2? = null,
            start: P2DirOrthogonal = guard
        ) = sequence<P2DirOrthogonal> {
            val boardVariant = if (obstacleAdded == null) obstacles else
                P2Board0.from(obstacles, obstacles.data + obstacleAdded)
            var gpd = start
            yield(gpd)
            while (true) {
                val next = gpd.strait()
                if (next.pos !in boardVariant) {
                    break
                } else if (next.pos in boardVariant.data) {
                    gpd = gpd.rotateRight()
                    yield(gpd)
                } else {
                    gpd = next
                    yield(next)
                }
            }
        }
    }

    fun List<String>.process2(): GuardGallivant2 {
        var g: P2DirOrthogonal? = null
        val b = P2Board0.builder(sizeTo2D) {
            forEachCharWith2D { c, x, y ->
                when (c) {
                    '.' -> Unit
                    '#' -> add(P2(x, y))
                    '^' -> g = P2DirOrthogonal(P2(x, y), Up)
                }
            }
        }
        return GuardGallivant2(b, g!!)
    }

    fun part1(inputLines: List<String>): Int = inputLines.process2().initPath.distinctBy { it.pos }.count()

    fun CoroutineScope.isLoop(gg: GuardGallivant2, addedObstacle: P2) = async {
        gg.initPath.firstOrNull { it.destination == addedObstacle }?.let { start ->
            require(start.destination == addedObstacle)
            val visited = mutableListOf<P2DirOrthogonal>()
            gg.path(start.destination, start).forEach { step ->
                if (step in visited) {
//                    CharDraw.fromP2Board0(gg.obstacles).apply {
//                        draw(addedObstacle, 'O')
//                        draw(visited.map { it.pos }, '+')
//                    }.drawIt("Loop in $addedObstacle")
                    return@async addedObstacle
                }
                visited.add(step)
            }
        } ?: run { return@async null }
        null
    }

    fun part2(inputLines: List<String>): Int = runBlocking(Dispatchers.Default) {
        inputLines.process2().let { gg ->
            gg.initPath.dropLast(1).map { it.destination }.distinct().map {
                isLoop(gg, it)
            }.toList().awaitAll()
        }.count { it != null }
    }

    d06.checkAll(::part1, ::part2)
    readInput("d06").printAll(::part1, ::part2)
}
