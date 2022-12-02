import java.security.InvalidParameterException

private const val DAY_NAME = "Day02"
fun main() {
    fun part1(input: List<String>): Int {
        val rounds = input.map {
            val (opponentMoveKey, myMoveKey) = it.split(" ")
            GameRound(
                opponentMove = Shape.fromKey(opponentMoveKey),
                myMove = Shape.fromKey(myMoveKey)
            )
        }

        return Game(rounds = rounds).totalScore
    }

    fun part2(input: List<String>): Int {
        val rounds = input.map {
            val (opponentMoveKey, expectedResultKey) = it.split(" ")
            val opponentMove = Shape.fromKey(opponentMoveKey)
            GameRound(
                opponentMove = opponentMove,
                myMove = Shape.fromExpectedResult(
                    opponentMove = opponentMove,
                    expectedResult = GameRound.Result.fromKey(expectedResultKey)
                )
            )
        }

        return Game(rounds = rounds).totalScore
    }

    val testInput = readInput("${DAY_NAME}_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput(DAY_NAME)
    println("part 1 result = ${part1(input)}")
    println("part 2 result = ${part2(input)}")
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

        fun fromExpectedResult(
            opponentMove: Shape,
            expectedResult: GameRound.Result,
        ): Shape {
            return when (opponentMove) {
                ROCK -> {
                    when (expectedResult) {
                        GameRound.Result.LOSE -> SCISSORS
                        GameRound.Result.DRAW -> ROCK
                        GameRound.Result.WIN -> PAPER
                    }
                }

                PAPER -> {
                    when (expectedResult) {
                        GameRound.Result.LOSE -> ROCK
                        GameRound.Result.DRAW -> PAPER
                        GameRound.Result.WIN -> SCISSORS
                    }
                }

                SCISSORS -> {
                    when (expectedResult) {
                        GameRound.Result.LOSE -> PAPER
                        GameRound.Result.DRAW -> SCISSORS
                        GameRound.Result.WIN -> ROCK
                    }
                }
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
        WIN(6);

        companion object {
            fun fromKey(key: String): Result {
                return when (key.lowercase()) {
                    "x" -> LOSE
                    "y" -> DRAW
                    "z" -> WIN
                    else -> throw InvalidParameterException()
                }
            }
        }
    }
}

private data class Game(
    val rounds: List<GameRound>
) {
    val totalScore: Int = rounds.sumOf { it.score }
}