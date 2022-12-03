fun main() {
    fun getAdjustedCharCode(c: Char): Int {
        return when(c) {
            in 'A'..'Z' -> c.code - 38
            in 'a'..'z' -> c.code - 96
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        fun getCommonCharPriority(str: String): Int {
            val halfIdx = str.length / 2
            val charSet = str.substring(0, halfIdx).toSet()

            for (idx in halfIdx until str.length) {
                if (charSet.contains(str[idx])) {
                    return getAdjustedCharCode(str[idx]);
                }
            }

            return 0
        }

        var sum = 0
        for (line in input) {
            sum += getCommonCharPriority(line)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        for (group in input.chunked(3)) {
            val charSet1 = group[0].toSet()
            val charSet2 = group[1].toSet()
            groupLoop@ for (idx in 0 until group[2].length) {
                val char = group[2][idx]
                if (charSet1.contains(char) && charSet2.contains(char)) {
                    sum += getAdjustedCharCode(char)
                    break@groupLoop
                }
            }
        }

        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}