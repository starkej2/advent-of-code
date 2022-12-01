private const val DAY_NAME = "Day01"
fun main() {
    val testInput = readInput("${DAY_NAME}_test")
    check(part1(testInput) == 24000)

    val input = readInput(DAY_NAME)
    println(part1(input))
}

private fun part1(input: List<String>): Int {
    val elfHauls = parseInput(input)
    return elfHauls.maxOf { it.totalCalories }
}

private fun parseInput(input: List<String>): List<ElfHaul> {
    val elfHauls = mutableListOf<ElfHaul>()

    val iterator = input.iterator()
    while (iterator.hasNext()) {
        val foodItems = mutableListOf<FoodItem>()
        do {
            val nextInput = iterator.next().toIntOrNull()
            if (nextInput != null) {
                foodItems.add(FoodItem(nextInput))
            }
        } while (iterator.hasNext() && nextInput != null)

        elfHauls.add(ElfHaul(foodItems))
    }

    return elfHauls
}

data class ElfHaul(
    val foodItems: List<FoodItem>,
) {
    val totalCalories: Int = foodItems.sumOf { it.calories }
}

@JvmInline
value class FoodItem(val calories: Int)