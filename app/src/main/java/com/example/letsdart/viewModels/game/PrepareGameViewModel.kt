package com.example.letsdart.viewModels.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.models.general.SavedPlayer

class PrepareGameViewModel(repository: PlayersRepository) : ViewModel() {

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

    val playersList: LiveData<List<SavedPlayer>> = repository.playersList.asLiveData()
    val chosenPlayersList: MutableList<SavedPlayer> = mutableListOf()
    val pairsList: MutableList<Pair<SavedPlayer, Boolean>> = mutableListOf()


    fun addPlayerToList(player: SavedPlayer) {
        chosenPlayersList.add(player)
    }

    fun removePlayerFromList(player: SavedPlayer) {
        chosenPlayersList.remove(player)
    }

    fun checkNames(): Boolean = chosenPlayersList.size == 2

}