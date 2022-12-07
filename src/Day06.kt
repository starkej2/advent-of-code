private const val DAY_NAME = "Day06"
fun main() {
    fun part1(input: List<String>): Int {
        return findMarkerIndex(
            input = input.first(),
            numUniqueCharacters = 4,
        )
    }

    fun part2(input: List<String>): Int {
        return findMarkerIndex(
            input = input.first(),
            numUniqueCharacters = 14,
        )
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${DAY_NAME}_test")
    check(part1(testInput) == 10)

    val input = readInput(DAY_NAME)
    println("part 1 result = ${part1(input)}")
    println("part 2 result = ${part2(input)}")
}

private fun findMarkerIndex(input: String, numUniqueCharacters: Int): Int {
    val characterSet = mutableSetOf<Char>()
    var numCharactersAdded = 0
    var result = -1

    run breaking@{
        input.toCharArray()
            .forEachIndexed { index, character ->
                characterSet.add(character)
                numCharactersAdded += 1

                if (characterSet.size == numUniqueCharacters) {
                    // println("got a result! $characterSet at index ${index + 1}")
                    characterSet.clear()
                    numCharactersAdded = 0
                    result = index + 1
                    return@breaking
                } else if (characterSet.size < numCharactersAdded) {
                    val indexOfDuplicate = characterSet.indexOf(character)
                    val remainingItems = characterSet.toList().subList(indexOfDuplicate + 1, characterSet.size)
                    characterSet.clear()
                    characterSet.addAll(remainingItems)
                    characterSet.add(character)
                    numCharactersAdded = characterSet.size
                    // println("RESET")
                }
                // println("($index) added character $character, set=${characterSet}")
            }
    }
    return result
}