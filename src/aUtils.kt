import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/inputs/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16).padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.printIt(prefix: String = "") = println("$prefix: $this")

fun Any?.checkIt(expected: Any?, prefix: String = "") =
    check(this == expected) { "Check${" $prefix".trim()} failed: $this (expected: $expected)" }

fun List<String>.printAll(part1: (List<String>) -> Any?, part2: (List<String>) -> Any?) {
    part1(this).printIt("PART #1")
    part2(this).printIt("PART #2")
}

fun Pair<List<String>, Pair<Any?, Any?>>.checkAll(part1: (List<String>) -> Any?, part2: (List<String>) -> Any?) {
    checkOne(part1)
    check2nd(part2)
}

fun Pair<List<String>, Pair<Any?, Any?>>.checkOne(part: (List<String>) -> Any?) =
    part.invoke(first).checkIt(second.first, "1")

private fun Pair<List<String>, Pair<Any?, Any?>>.check2nd(part: (List<String>) -> Any?) =
    part.invoke(first).checkIt(second.second, "2")
