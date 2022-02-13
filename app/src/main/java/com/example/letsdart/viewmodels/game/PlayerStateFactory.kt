package com.example.letsdart.viewmodels.game

import android.util.Log
import com.example.letsdart.models.general.*
import java.lang.Math.round

class PlayerStateFactory {

    companion object {
        fun createPlayerState(scoredPoints: Int, previousState: PlayerState, isDoubleEnabled: Boolean, isTripleEnabled: Boolean): PlayerState {
            val currentMainScore: Int
            val currentRoundScore: Int
            val currentScoreSum: Int
            val currentAverage: Float
            var currentSingles = previousState.singles
            var currentDoubles = previousState.doubles
            var currentTriples = previousState.triples
            val currentPartialScore = previousState.partialScore.toMutableList()
            val currentScoreCount = previousState.scoreCount + 1
            val currentScoresMap = previousState.scoresMap.toMutableMap()

            when {
                isDoubleEnabled -> {
                    currentMainScore = previousState.mainScore - scoredPoints * 2
                    currentRoundScore = if (previousState.roundScore == -1)
                        scoredPoints * 2
                    else
                        previousState.roundScore + scoredPoints * 2
                    currentScoreSum = previousState.scoreSum + scoredPoints * 2
                    currentAverage = countAverage(currentScoreSum, currentScoreCount)
                    currentPartialScore.add("D$scoredPoints")
                    currentScoresMap["D$scoredPoints"] = previousState.scoresMap["D$scoredPoints"]!! + 1
                    currentDoubles++

                    return DoublePlayerState(
                        currentMainScore,
                        currentRoundScore,
                        currentPartialScore,
                        currentScoreSum,
                        currentScoreCount,
                        currentAverage,
                        previousState.wonLegs,
                        previousState.wonSets,
                        currentSingles,
                        currentDoubles,
                        currentTriples,
                        previousState.allWonLegs,
                        currentScoresMap
                    )
                }
                isTripleEnabled -> {
                    currentMainScore = previousState.mainScore - scoredPoints * 3
                    currentRoundScore = if (previousState.roundScore == -1)
                        scoredPoints * 3
                    else
                        previousState.roundScore + scoredPoints * 3
                    currentScoreSum = previousState.scoreSum + scoredPoints * 3
                    currentAverage = countAverage(currentScoreSum, currentScoreCount)
                    currentPartialScore.add("T$scoredPoints")
                    currentScoresMap["T$scoredPoints"] = previousState.scoresMap["T$scoredPoints"]!! + 1
                    currentTriples++

                    return TriplePlayerState(
                        currentMainScore,
                        currentRoundScore,
                        currentPartialScore,
                        currentScoreSum,
                        currentScoreCount,
                        currentAverage,
                        previousState.wonLegs,
                        previousState.wonSets,
                        currentSingles,
                        currentDoubles,
                        currentTriples,
                        previousState.allWonLegs,
                        currentScoresMap
                    )
                }
                else -> {
                    currentMainScore = previousState.mainScore - scoredPoints
                    currentRoundScore = if (previousState.roundScore == -1)
                        scoredPoints
                    else
                        previousState.roundScore + scoredPoints
                    currentScoreSum = previousState.scoreSum + scoredPoints
                    currentAverage = countAverage(currentScoreSum, currentScoreCount)


                    currentPartialScore.add("$scoredPoints")
                    currentScoresMap["$scoredPoints"] = previousState.scoresMap["$scoredPoints"]!! + 1
                    currentSingles++

                    return DefaultPlayerState(
                        currentMainScore,
                        currentRoundScore,
                        currentPartialScore,
                        currentScoreSum,
                        currentScoreCount,
                        currentAverage,
                        previousState.wonLegs,
                        previousState.wonSets,
                        currentSingles,
                        currentDoubles,
                        currentTriples,
                        previousState.allWonLegs,
                        currentScoresMap
                    )
                }
            }
        }

        private fun countAverage(currentScoreSum: Int, currentScoreCount: Int): Float {
            Log.i("average", currentScoreSum.toString())
            Log.i("average", currentScoreCount.toString())

            return if (currentScoreCount < 3)
                -1f
            else {
                val currentAverage: Float = currentScoreSum.toFloat() / (currentScoreCount / 3)
                round(currentAverage * 100) / 100f
            }
        }

        fun createPlayerStateOverthrow(roundState: PlayerState, previousState: PlayerState): PlayerState {
            val currentRoundScore = 0
            val currentScoreSum = roundState.scoreSum
            val currentScoreCount = roundState.scoreCount + 3
            val currentAverage = countAverage(currentScoreSum, currentScoreCount)
            val currentPartialScore = previousState.partialScore.toMutableList()
            currentPartialScore.add("OT")
            val currentScoresMap = roundState.scoresMap.toMutableMap()
            currentScoresMap["OT"] = currentScoreCount - roundState.singles - roundState.doubles - roundState.triples

            return OverthrowPlayerState(
                roundState.mainScore,
                currentRoundScore,
                currentPartialScore,
                currentScoreSum,
                currentScoreCount,
                currentAverage,
                roundState.wonLegs,
                roundState.wonSets,
                roundState.singles,
                roundState.doubles,
                roundState.triples,
                previousState.allWonLegs,
                currentScoresMap
            )
        }

        fun createPlayerStateNewRound(
            scoredPoints: Int?,
            previousState: PlayerState?,
            startScore: Int? = null,
            wonLeg: Boolean = false,
            newSet: Boolean = false,
            wonSet: Boolean = false,
            isDoubleEnabled: Boolean = false,
            isTripleEnabled: Boolean = false
        ): PlayerState {

            if (previousState == null) {
                val scoresMap = Player.createFirstScoresMap()

                return StartGamePlayerState(
                    // default values in declaration?
                    startScore!!,
                    scoresMap = scoresMap
                )
            }

            val currentRoundScore = -1
            val currentPartialScore = listOf<String>()
            var currentLegs = previousState.wonLegs
            var currentSets = previousState.wonSets
            var currentAllWonLegs = previousState.allWonLegs
            var currentSingles = previousState.singles
            var currentDoubles = previousState.doubles
            var currentTriples = previousState.triples

            if (startScore == null)
                return NewRoundPlayerState(
                    previousState.mainScore,
                    currentRoundScore,
                    currentPartialScore,
                    previousState.scoreSum,
                    previousState.scoreCount,
                    previousState.average,
                    currentLegs,
                    currentSets,
                    previousState.singles,
                    previousState.doubles,
                    previousState.triples,
                    previousState.allWonLegs,
                    previousState.scoresMap.toMutableMap()
                )

            var currentScoreCount = previousState.scoreCount
            var currentScoreSum = previousState.scoreSum
            val currentScoresMap = previousState.scoresMap.toMutableMap()

            if (wonSet)
                currentSets++


            if (wonLeg)
                currentLegs++


            if (wonLeg || wonSet) {
                currentScoreCount++
                currentAllWonLegs++
                when {
                    isDoubleEnabled -> {
                        currentDoubles++
                        currentScoreSum += 2 * scoredPoints!!
                        currentScoresMap["D$scoredPoints"] = previousState.scoresMap["D$scoredPoints"]!! + 1
                    }
                    isTripleEnabled -> {
                        currentTriples++
                        currentScoreSum += 3 * scoredPoints!!
                        currentScoresMap["T$scoredPoints"] = previousState.scoresMap["T$scoredPoints"]!! + 1
                    }
                    else -> {
                        currentSingles++
                        currentScoreSum += scoredPoints!!
                        currentScoresMap["$scoredPoints"] = previousState.scoresMap["$scoredPoints"]!! + 1
                    }
                }
            }
            val currentAverage = countAverage(currentScoreSum, currentScoreCount)

            if (newSet) {
                currentLegs = 0
                return NewSetPlayerState(
                    startScore,
                    currentRoundScore,
                    currentPartialScore,
                    currentScoreSum,
                    currentScoreCount,
                    currentAverage,
                    currentLegs,
                    currentSets,
                    currentSingles,
                    currentDoubles,
                    currentTriples,
                    currentAllWonLegs,
                    currentScoresMap
                )
            }

            return NewLegPlayerState(
                startScore,
                currentRoundScore,
                currentPartialScore,
                currentScoreSum,
                currentScoreCount,
                currentAverage,
                currentLegs,
                currentSets,
                currentSingles,
                currentDoubles,
                currentTriples,
                currentAllWonLegs,
                currentScoresMap
            )
        }
    }
}