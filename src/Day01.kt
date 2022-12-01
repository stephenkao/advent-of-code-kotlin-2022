fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        var max = 0

        for (line in input) {
            if (line == "") {
                if (max < sum) {
                    max = sum;
                }

                sum = 0;
            } else {
                sum += line.toInt()
            }
        }

        return max
    }

    fun part2(input: List<String>): Int {
        val sumList = mutableListOf<Int>()
        var currentSum = 0
        for ((index, line) in input.withIndex()) {
            if (line == "") {
                sumList.add(currentSum)
                currentSum = 0
            } else {
                currentSum += line.toInt()
            }

            if (index == input.lastIndex) {
                sumList.add(currentSum)
                currentSum = 0
            }
        }

        return sumList.sortedDescending().take(3).sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}