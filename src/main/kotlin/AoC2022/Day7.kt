package AoC2022

import utils.readInputAsListOfStrings

data class Directory (
    val name: String,
    val parent: Directory?,
    val subdirs: MutableList<Directory> = mutableListOf(),
    val files: MutableList<Day7File> = mutableListOf()
    )

typealias Day7File = Pair<String, Int>

private var testInput = ("\$ cd /\n" +
        "\$ ls\n" +
        "dir a\n" +
        "14848514 b.txt\n" +
        "8504156 c.dat\n" +
        "dir d\n" +
        "\$ cd a\n" +
        "\$ ls\n" +
        "dir e\n" +
        "29116 f\n" +
        "2557 g\n" +
        "62596 h.lst\n" +
        "\$ cd e\n" +
        "\$ ls\n" +
        "584 i\n" +
        "\$ cd ..\n" +
        "\$ cd ..\n" +
        "\$ cd d\n" +
        "\$ ls\n" +
        "4060174 j\n" +
        "8033020 d.log\n" +
        "5626152 d.ext\n" +
        "7214296 k").split("\n")

private fun String.processLine(directory: Directory): Directory {
    val splitCommand = this.trim().split(' ')
    if(splitCommand[0] == "dir"){
        val dirName = this.split(" ")[1]
        directory.subdirs.add(Directory(dirName, directory))
    } else if(splitCommand[0][0].isDigit()) {
        directory.files.add(Day7File(splitCommand[1], splitCommand[0].toInt()))
    } else if(splitCommand[0] == "$" && splitCommand[1] == "cd" && splitCommand[2] == ".."){
        return directory.parent!!
    } else if(splitCommand[0] == "$" && splitCommand[1] == "cd"){
        return directory.subdirs.find { it.name == splitCommand[2] }!!
    }
    return directory;
}

private fun List<String>.processToTree(): Directory {
    val rootDir = Directory("/", null)
    var currentDir = rootDir
    this.subList(1,size).forEach{
        currentDir = it.processLine(currentDir)
    }
    return rootDir
}

private fun Directory.prettyPrint(depth: Int = 0){
    repeat(depth){print(' ')}
    println("-${this.name} (dir, size=${this.getDirSize()})")
    this.files.forEach{
        repeat(depth+1){print(' ')}
        println("- ${it.first} (file, size=${it.second})")
    }
    this.subdirs.forEach{
        it.prettyPrint(depth+1)
    }
}

private fun Directory.getDirSize(): Int{
    var size = 0
    files.forEach { size += it.second }
    subdirs.forEach { size += it.getDirSize() }
    return size
}

private fun Directory.dirsAsList(sizeFilter: Int = -1): List<Directory>{
    var result = subdirs.flatMap { it.dirsAsList(sizeFilter) }
    if (sizeFilter < 0 || this.getDirSize() < sizeFilter){
        result = result.plus(this)
    }
    return result
}

private fun List<String>.solve7a(): Int {
    val directory = this.processToTree()
    return directory.dirsAsList(100000).sumOf { it.getDirSize() }
}

private fun List<String>.solve7b(): Int {
    val rootDir = this.processToTree()
    val totalDiskSpace = 70000000
    val requiredSpace = 30000000
    val currentSpace = totalDiskSpace - rootDir.getDirSize()
    val spaceToFree = requiredSpace - currentSpace
    return rootDir.dirsAsList().map{it.getDirSize()}.filter { it >= spaceToFree }.min()
}

fun main() {
    val input = readInputAsListOfStrings("${Constants.INPUT_PATH}input_day_7.txt")
    println("Day 7a answer: ${input.solve7a()}")
    println("Day 7b answer: ${input.solve7b()}")
}
