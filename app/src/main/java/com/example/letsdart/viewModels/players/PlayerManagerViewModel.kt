package com.example.letsdart.viewModels.players

import android.util.Log
import androidx.lifecycle.*
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.models.series.Series
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PlayerManagerViewModel(
    private val playersRepository: PlayersRepository,
    private val tournamentsRepository: TournamentsRepository,
    private val leaguesRepository: LeaguesRepository
) : ViewModel() {

    val playersList: LiveData<List<SavedPlayer>> = playersRepository.playersList.asLiveData()
    private val _deletionCheckResult = MutableLiveData<SavedPlayer?>()
    val deletionCheckResult: LiveData<SavedPlayer?> = _deletionCheckResult

    fun insertPlayer(player: SavedPlayer) {
        viewModelScope.launch {
            playersRepository.insert(player)
        }
    }

    fun updatePlayer(player: SavedPlayer) {
        viewModelScope.launch {
            playersRepository.update(player)
        }
    }

    fun checkPlayerDeletion(player: SavedPlayer) {
        var inTournament: Boolean
        var inLeague: Boolean
        viewModelScope.launch {
            leaguesRepository.leaguesList.combine(tournamentsRepository.tournamentsList) { leagues, tournaments ->
                inLeague = checkPlayerInSeries(leagues, player)
                inTournament = checkPlayerInSeries(tournaments, player)
                if (inLeague || inTournament) {
                    _deletionCheckResult.value = null
                } else {
                    _deletionCheckResult.value = player
                }
            }.collect()
        }
    }

    fun deletePlayer(player: SavedPlayer) {
        viewModelScope.launch {
            playersRepository.delete(player)
        }
    }

    private fun checkPlayerInSeries(seriesList: List<Series>, player: SavedPlayer): Boolean {
        for (series in seriesList) {
            for (seriesPlayer in series.playersList) {
                if (seriesPlayer.savedPlayer.playerId == player.playerId)
                    return true
            }
        }
        return false
    }

}