fun main() {
    /**
     * @param rangesStr the ranges string (e.g., '1-2,3-4')
     * @return list of integer sets per range
     */
    fun getSetsFromRanges(rangesStr: String): Pair<Set<Int>, Set<Int>> {
        val (startIdx1, endIdx1, startIdx2, endIdx2) = rangesStr.split("[,\\-]".toRegex())
        return Pair(
            (startIdx1.toInt()..endIdx1.toInt()).toSet(),
            (startIdx2.toInt()..endIdx2.toInt()).toSet()
        )
    }

    fun part1(input: List<String>): Int {
        fun isFullSubset(set: Set<Int>, subset: Set<Int>): Boolean {
            return (set + subset).size == set.size
        }

        var sum = 0

        for (line in input) {
            val (set1, set2) = getSetsFromRanges(line)
            if (isFullSubset(set1, set2) || isFullSubset(set2, set1)) {
                sum += 1
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        fun isPartialSubset(set: Set<Int>, subset: Set<Int>): Boolean {
            return (set + subset).size < (set.size + subset.size)
        }

        var sum = 0
        for (line in input) {
            val (set1, set2) = getSetsFromRanges(line)
            if (isPartialSubset(set1, set2) || isPartialSubset(set2, set1)) {
                sum += 1
            }
        }
        return sum
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}