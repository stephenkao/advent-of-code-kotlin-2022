enum class NodeType {
    FILE,
    DIRECTORY
}

class Node(nodeType: NodeType, filename: String, filesize: Int = 0, parentDirectory: Node? = null) {
    val nodeType = nodeType
    val filename = filename
    val filesize = filesize // only for files
    var parentDirectory = parentDirectory // only for directories

    var nodes = mutableListOf<Node>()

    fun getTotalSize(): Int {
        return nodes.fold(0) { acc, node ->
            when (node.nodeType) {
                NodeType.FILE -> acc + node.filesize
                NodeType.DIRECTORY -> acc + node.getTotalSize()
            }
        }
    }

    fun getFiles(): List<Node> {
        return nodes.filter { it.nodeType == NodeType.FILE }
    }

    fun getDirectories(): List<Node> {
        return nodes.filter { it.nodeType == NodeType.DIRECTORY }
    }

    fun isFile() = nodeType == NodeType.FILE
    fun isDirectory() = nodeType == NodeType.DIRECTORY
}

fun printTree(node: Node, depth: Int = 0) {
    val indentation = "    ".repeat(depth)
    if (depth == 0) {
        println("$indentation ${node.filename} (${node.filesize})")
    }

    node.getFiles().forEach { node ->
        println("$indentation | ${node.filename} ${node.filesize}")
    }

    node.getDirectories().forEach { node ->
        println("$indentation | ${node.filename} (${node.filesize})")
        printTree(node, depth + 1)
    }
}

fun main() {
    val day = "07"

    val cdRegex = Regex("^\\$\\scd\\s(.*)$")
    val dirRegex = Regex("^dir\\s(.*)$")
    val fileRegex = Regex("^(\\d*)\\s(.*)$")


    fun generateFileSystem(input: List<String>): Node {
        val rootDirectory = Node(NodeType.DIRECTORY, "/")
        var currentDirectory = rootDirectory

        for (line in input) {
            if (cdRegex.matches(line)) {
                val (filename) = cdRegex.find(line)!!.destructured
                if (filename == "..") {
                    currentDirectory = currentDirectory.parentDirectory!!
                    // println("CHANGING TO .. (${currentDirectory.filename})")
                } else if (filename != "/") {
                    currentDirectory = currentDirectory.getDirectories().find { it.filename == filename }!!
                    // println("CHANGING TO $filename")
                }
            } else if (dirRegex.matches(line)) {
                val (filename) = dirRegex.find(line)!!.destructured
                currentDirectory.nodes.add(Node(NodeType.DIRECTORY, filename, parentDirectory = currentDirectory))
                // println("ADD DIRECTORY $filename to ${currentDirectory.filename}")
            } else if (fileRegex.matches(line)) {
                val (filesize, filename) = fileRegex.find(line)!!.destructured
                currentDirectory.nodes.add(Node(NodeType.FILE, filename, filesize.toInt()))
                // println("ADD FILE $filename to ${currentDirectory.filename}")
            }
        }

        return rootDirectory
    }

    fun part1(input: List<String>): Int {
        val maxSize = 100000
        val rootDirectory = generateFileSystem(input)

        fun getDeletionCandidateSum(node: Node, sum: Int = 0): Int {
            if (node.isFile() || node.nodes.size == 0) {
                return sum
            }

            val totalSize = node.getTotalSize()
            var baseVal = if (totalSize <= maxSize) sum + totalSize else sum

            return node.nodes.fold(baseVal) { acc, node -> getDeletionCandidateSum(node, acc) }
        }

        return getDeletionCandidateSum(rootDirectory)
    }

    fun part2(input: List<String>): Int {
        val rootDirectory = generateFileSystem(input)
        val unusedSpace = 70000000 - rootDirectory.getTotalSize()
        val requiredSpace = 30000000 - unusedSpace

        fun getDeletionCandidateSize(node: Node, size: Int): Int {
            val totalSize = node.getTotalSize()

            var smallestSize = size
            if (totalSize in requiredSpace..smallestSize) {
                smallestSize = totalSize
            }

            node.getDirectories().forEach { node ->
                smallestSize = getDeletionCandidateSize(node, smallestSize)
            }

            return smallestSize
        }

        return getDeletionCandidateSize(rootDirectory, rootDirectory.getTotalSize())
    }

    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day${day}")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}