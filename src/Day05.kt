import kotlin.reflect.typeOf

fun main() {
    val day = "05"
    var charsPerCrate = 4 // assumption!

    fun part1(input: List<String>): String {
        val structureRegex = Regex("\\[([A-Z]+)\\]")
        val moveRegex = Regex("\\s*move\\s*(\\d*)\\s*from\\s*(\\d*)\\s*to\\s*(\\d*)")

        // initialize stacks based on number of characters in first line
        // assumes there are four characters per stack
        val stacks = MutableList((input.first().length / charsPerCrate) + 1) { mutableListOf<String>() }

        fun processStructureLine(line: String) {
            line.chunked(charsPerCrate).forEachIndexed { idx, stackItem ->
                val crate = structureRegex.find(stackItem)?.groupValues?.get(1)
                if (crate !== null) {
                    stacks[idx].add(crate)
                }
            }
        }

        fun processMoveLine(line: String, oneByOne: Boolean) {
            val (count, src, dest) = moveRegex.find(line)!!.destructured
            val srcStack = stacks[src.toInt() - 1]
            val destStack = stacks[dest.toInt() - 1]
            val movingCrates = srcStack.take(count.toInt())
            repeat(count.toInt()) { srcStack.removeFirst() }
            destStack.addAll(
                0,
                if (oneByOne) movingCrates.reversed() else movingCrates
            )
        }

        // read in lines
        for (line in input) {
            if (line.contains(structureRegex)) {
                processStructureLine(line)
            } else if (line.contains(moveRegex)) {
                // PART 1
                // processMoveLine(line, oneByOne = true)
                // PART 2
                processMoveLine(line, oneByOne = false)
            }
        }

        return stacks.joinToString("") { stack -> stack.first() }
    }


    val testInput = readInput("Day${day}_test")
    println(part1(testInput))
    // check(part1(testInput) == "CMZ")
     check(part1(testInput) == "MCD")

    val input = readInput("Day${day}")
    // println("Part 1: ${part1(input)}")
    println("Part 2: ${part1(input)}")
}