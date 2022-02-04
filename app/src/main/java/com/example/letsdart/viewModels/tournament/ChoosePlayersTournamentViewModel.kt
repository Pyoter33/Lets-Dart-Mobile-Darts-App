package com.example.letsdart.viewModels.tournament

import androidx.lifecycle.*
import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.utils.MatchupsGenerator
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import kotlinx.coroutines.launch

class ChoosePlayersTournamentViewModel(
    private val name: String,
    private val rules: Rules,
    private val tournamentsRepository: TournamentsRepository,
    playersRepository: PlayersRepository
) : ViewModel() {

    val playersList: LiveData<List<SavedPlayer>> = playersRepository.playersList.asLiveData()
    val chosenSavedPlayers: MutableList<SavedPlayer> = mutableListOf()
    val pairsList: MutableList<Pair<SavedPlayer, Boolean>> = mutableListOf()
    private val _createTournamentResult = MutableLiveData(false)
    val createTournamentResult: LiveData<Boolean> = _createTournamentResult

    fun createPairs(list: List<SavedPlayer>): List<Pair<SavedPlayer, Boolean>> {
        for (elem in list)
            pairsList.add(Pair(elem, false))
        return pairsList
    }

    fun createTournament() {
        val chosenSeriesPlayers: List<SeriesPlayer> = chosenSavedPlayers.map { player ->
            SeriesPlayer(savedPlayer = player)
        }
        viewModelScope.launch {
            val id = tournamentsRepository.insert(Tournament(name, rules))
            val listWithIds = mutableListOf<SeriesPlayer>()
            for (player in chosenSeriesPlayers){
               val playerId = tournamentsRepository.insert(id, player)
                listWithIds.add(SeriesPlayer(playerId, player.savedPlayer))
            }
            tournamentsRepository.insert(id, MatchupsGenerator.generateFirstMatchups(listWithIds, rules))
            _createTournamentResult.value = true
        }

    }

}