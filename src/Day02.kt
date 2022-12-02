import java.security.InvalidParameterException

private const val DAY_NAME = "Day02"
fun main() {
    fun part1(input: List<String>): Int {
        val game = parseInput(input)
        return game.totalScore
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("${DAY_NAME}_test")
    check(part1(testInput) == 15)

    val input = readInput(DAY_NAME)
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>): Game {
    val rounds = input.map {
        val (opponentMoveKey, myMoveKey) = it.split(" ")
        GameRound(
            opponentMove = Shape.fromKey(opponentMoveKey),
            myMove = Shape.fromKey(myMoveKey)
        )
    }
    return Game(rounds = rounds)
}

private enum class Shape(
    val pointsValue: Int,
) {
    ROCK(pointsValue = 1) {
        override fun getResultAgainst(otherShape: Shape): GameRound.Result {
            return when (otherShape) {
                ROCK -> GameRound.Result.DRAW
                PAPER -> GameRound.Result.LOSE
                SCISSORS -> GameRound.Result.WIN
            }
        }
    },

    PAPER(pointsValue = 2) {
        override fun getResultAgainst(otherShape: Shape): GameRound.Result {
            return when (otherShape) {
                ROCK -> GameRound.Result.WIN
                PAPER -> GameRound.Result.DRAW
                SCISSORS -> GameRound.Result.LOSE
            }
        }
    },

    SCISSORS(pointsValue = 3) {
        override fun getResultAgainst(otherShape: Shape): GameRound.Result {
            return when (otherShape) {
                ROCK -> GameRound.Result.LOSE
                PAPER -> GameRound.Result.WIN
                SCISSORS -> GameRound.Result.DRAW
            }
        }
    };

    abstract fun getResultAgainst(otherShape: Shape): GameRound.Result

    companion object {
        fun fromKey(key: String): Shape {
            return when (key.lowercase()) {
                "a", "x" -> ROCK
                "b", "y" -> PAPER
                "c", "z" -> SCISSORS
                else -> throw InvalidParameterException()
            }
        }
    }
}

private data class GameRound(
    private val opponentMove: Shape,
    private val myMove: Shape,
) {
    val result: Result = myMove.getResultAgainst(opponentMove)
    val score: Int = myMove.pointsValue + result.pointsValue

    enum class Result(
        val pointsValue: Int
    ) {
        LOSE(0),
        DRAW(3),
        WIN(6),
    }
}

private data class Game(
    val rounds: List<GameRound>
) {
    val totalScore: Int = rounds.sumOf { it.score }
}