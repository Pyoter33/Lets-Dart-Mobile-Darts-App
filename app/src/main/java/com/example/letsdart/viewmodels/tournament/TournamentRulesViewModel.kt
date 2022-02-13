package com.example.letsdart.viewmodels.tournament
import androidx.lifecycle.ViewModel

class TournamentRulesViewModel: ViewModel() {
    val possibleStartPoints: List<Int> = listOf(301, 501)
    val possibleCheckouts: List<String> = listOf("Straight out", "Double out")
    val possibleSetsOrLegs: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    val possibleWinConditions: List<String> = listOf("Best of", "First to")
    val possibleDeciderTypes: List<String> = listOf("Default", "Random")

    var startScore = 301
    var checkout = 0
    var numberOfSets = 1
    var numberOfLegs = 1
    var winCondition = 0
    var decider = 0
}