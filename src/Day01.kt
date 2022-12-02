private const val DAY_NAME = "Day01"
fun main() {
    val testInput = readInput("${DAY_NAME}_test")
    check(calculatePart1Result(testInput) == 24000)

    val input = readInput(DAY_NAME)
    println("part 1 result = ${calculatePart1Result(input)}")
    println("part 2 result = ${calculatePart2Result(input)}")
}

private fun calculatePart1Result(input: List<String>): Int {
    val elfInventories = parseInput(input)
    return elfInventories.maxOf { it.totalCalories }
}

private fun calculatePart2Result(input: List<String>): Int {
    val elfInventories = parseInput(input)
    return elfInventories
        .sortedByDescending { it.totalCalories }
        .take(3)
        .sumOf { it.totalCalories }
}

private fun parseInput(input: List<String>): List<ElfInventory> {
    val test = input
        .divide { it.isBlank() }
        .map { valuesGroup ->
            ElfInventory(
                items = valuesGroup
                    .mapNotNull { it.toIntOrNull() }
                    .map(::FoodItem)
            )
        }
    return test
}

private inline fun <T> List<T>.divide(isDivider: (T) -> Boolean): List<List<T>> {
    val originalList = this
    val iterator = originalList.listIterator()
    var windowStartIndex = 0
    val result = mutableListOf<List<T>>()

    while (iterator.hasNext()) {
        val itemIndex = iterator.nextIndex()
        val item = iterator.next()

        if (isDivider(item)) {
            result.add(originalList.subList(windowStartIndex, itemIndex))
            windowStartIndex = itemIndex + 1
        }
    }

    result.add(originalList.subList(windowStartIndex, originalList.size))
    return result
}

data class ElfInventory(
    val items: List<FoodItem>,
) {
    val totalCalories: Int = items.sumOf { it.calories }
}

@JvmInline
value class FoodItem(val calories: Int)