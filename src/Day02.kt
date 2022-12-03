fun main() {
    // indices and scores
    val shapeIndices = mapOf(
        // rock
        "A" to 0,
        "X" to 0,
        // paper
        "B" to 1,
        "Y" to 1,
        // scissors
        "C" to 2,
        "Z" to 2
    )

    fun part1(input: List<String>): Int {
        fun getOutcomeScore(opponentChoice: String, myChoice: String): Int {
            val opponentIdx = shapeIndices[opponentChoice]!!
            val myIdx = shapeIndices[myChoice]!!

            // tie: 3 points
            if (opponentIdx == myIdx) {
                return 3
            }

            // win: 6 points
            if ((opponentIdx + 1) % 3 == (myIdx % 3)) {
                return 6
            }

            // loss: 0 points
            return 0
        }

        var sum = 0

        for (line in input) {
            val (opponentChoice: String, myChoice: String)= line.split(' ')
            val outcomeScore = getOutcomeScore(opponentChoice, myChoice)
            val shapeScore = shapeIndices[myChoice]!! + 1
            sum += outcomeScore + shapeScore
        }
        return sum
    }

    // you dumb elf
    // X = should lose
    // Y = should draw
    // Z = should win
    fun part2(input: List<String>): Int {
        val outcomeScores = mapOf(
            "X" to 0,
            "Y" to 3,
            "Z" to 6
        )

        var sum = 0

        for (line in input) {
            val (opponentChoice: String, intendedOutcome: String) = line.split(' ')
            val opponentIdx = shapeIndices[opponentChoice]!!
            // need to determine my shape
            val shapeScore = when (intendedOutcome) {
                // loss
                "X" -> (((opponentIdx - 1) + 3) % 3) + 1
                // tie
                "Y" -> opponentIdx + 1
                // win
                "Z" -> ((opponentIdx + 1) % 3) + 1
                else -> 0 // hmmm should probably enum these instead
            }

            sum += outcomeScores[intendedOutcome]!! + shapeScore
        }

        return sum
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}