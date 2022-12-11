fun main() {
    val day = "06"
    val markerCount = 4
    val messageCount = 14

    fun getUniqueWindowEndIdx(str: String, windowSize: Int): Int {
        for ((idx, _) in str.withIndex()) {
            val substring = str.substring(idx until idx + windowSize)
            if (substring.toSet().size == windowSize) {
                return idx + windowSize
            }
        }

        return -1
    }

    fun part1(input: String): Int {
        return getUniqueWindowEndIdx(input, markerCount)
    }

    fun part2(input: String): Int {
        return getUniqueWindowEndIdx(input, messageCount)
    }

    val testInput = readInput("Day${day}_test").first()
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day${day}").first()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}