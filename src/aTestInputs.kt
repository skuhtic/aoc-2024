val d00 = """
""".trimIndent().lines() to (1 to 1)

val d04 = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
""".trimIndent().lines() to (18 to 9)

val d03_2 = """
xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
""".trimIndent().lines() to (1 to 48)

val d03_1 = """
xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
""".trimIndent().lines() to (161 to 1)

val d02 = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
""".trimIndent().lines() to (2 to 4)

val d01 = """
3   4
4   3
2   5
1   3
3   9
3   3
""".trimIndent().lines() to (11 to 31)
