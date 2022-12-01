private const val DAY_NAME = "Day00"
fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${DAY_NAME}_test")
    check(part1(testInput) == 1)

    val input = readInput(DAY_NAME)
    println(part1(input))
    println(part2(input))
}
