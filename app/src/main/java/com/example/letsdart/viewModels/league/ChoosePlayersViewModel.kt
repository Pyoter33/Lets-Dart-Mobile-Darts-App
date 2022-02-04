package com.example.letsdart.viewModels.league

import androidx.lifecycle.*
import com.example.letsdart.models.general.Rules
import com.example.letsdart.utils.GamesGenerator
import com.example.letsdart.models.league.League
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.models.general.SavedPlayer
import kotlinx.coroutines.launch

class ChoosePlayersViewModel(
    private val name: String,
    private val rules: Rules,
    private val leaguesRepository: LeaguesRepository,
    playersRepository: PlayersRepository
) : ViewModel() {

    val playersList: LiveData<List<SavedPlayer>> = playersRepository.playersList.asLiveData()
    val chosenSavedPlayers: MutableList<SavedPlayer> = mutableListOf()
    val pairsList: MutableList<Pair<SavedPlayer, Boolean>> = mutableListOf()
    private val _createLeagueResult = MutableLiveData(false)
    val createLeagueResult: LiveData<Boolean> = _createLeagueResult

    fun createPairs(list: List<SavedPlayer>): List<Pair<SavedPlayer, Boolean>> {
        for (elem in list)
            pairsList.add(Pair(elem, false))
        return pairsList
    }
    fun createLeague() {
        val chosenSeriesPlayers: List<SeriesPlayer> = chosenSavedPlayers.map { player ->
            SeriesPlayer(savedPlayer = player)
        }

        viewModelScope.launch {
            val id = leaguesRepository.insert(League(name, rules))
            val listWithIds = mutableListOf<SeriesPlayer>()
            for (player in chosenSeriesPlayers){
                val playerId = leaguesRepository.insert(id, player)
                listWithIds.add(SeriesPlayer(playerId, player.savedPlayer))
            }
            leaguesRepository.insert(id, GamesGenerator.generateGames(listWithIds, rules.rematch))
            _createLeagueResult.value = true
        }
    }
}