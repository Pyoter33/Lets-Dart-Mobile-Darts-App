package com.example.letsdart.viewModels.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import com.example.letsdart.models.general.QuickMatchup
import com.example.letsdart.models.general.Results
import com.example.letsdart.models.league.League
import com.example.letsdart.models.league.LeagueMatchup
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.models.tournament.Matchup
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.utils.LeaguePlayersComparator
import kotlinx.coroutines.launch

class FinishedGameViewModel(
    private val firstPlayerResults: Results,
    private val secondPlayerResults: Results,
    private val matchup: Matchup,
    private val leaguesRepository: LeaguesRepository,
    private val tournamentsRepository: TournamentsRepository,
    private val playersRepository: PlayersRepository
) : ViewModel() {
    private val _isGameEvaluated = MutableLiveData(false)
    val isGameEvaluated: LiveData<Boolean> = _isGameEvaluated
    private val _areStatsUpdated = MutableLiveData(false)
    val areStatsUpdated: LiveData<Boolean> = _areStatsUpdated

    fun evaluateGame() {
        when (matchup) {
            is TournamentMatchup ->
                updateTournament()

            is LeagueMatchup ->
                updateLeague()

            is QuickMatchup ->
                _isGameEvaluated.value = true

        }
    }

    fun updateStats() {
        viewModelScope.launch {
            updateSavedPlayerStats(matchup.firstPlayer.savedPlayer.playerId, firstPlayerResults)
            updateSavedPlayerStats(matchup.secondPlayer.savedPlayer.playerId, secondPlayerResults)
            _areStatsUpdated.value = true
        }
    }


    private fun updateLeague() {
        viewModelScope.launch {
            val league: League = leaguesRepository.get((matchup as LeagueMatchup).seriesId)!!

            for (player in league.playersList) {
                if (player.seriesPlayerId == matchup.firstPlayer.seriesPlayerId) {
                    updateCertainPlayerStats(player, firstPlayerResults, secondPlayerResults)
                    leaguesRepository.update(player)
                }
                if (player.seriesPlayerId == matchup.secondPlayer.seriesPlayerId) {
                    updateCertainPlayerStats(player, secondPlayerResults, firstPlayerResults)
                    leaguesRepository.update(player)
                }
            }
            if (firstPlayerResults.wonGame)
                matchup.winnerId = matchup.firstPlayer.seriesPlayerId
            else
                matchup.winnerId = matchup.secondPlayer.seriesPlayerId

            league.playersList = league.playersList.sortedWith(LeaguePlayersComparator)
            leaguesRepository.update(matchup)
            leaguesRepository.update(league)
            _isGameEvaluated.value = true
        }
    }


    private fun updateTournament() {
        viewModelScope.launch {
            val tournament: Tournament = tournamentsRepository.get((matchup as TournamentMatchup).seriesId)!!

            val currentTournamentMatchup: TournamentMatchup = matchup
            updateCertainPlayerStats(currentTournamentMatchup.firstPlayer, firstPlayerResults, secondPlayerResults)
            updateCertainPlayerStats(currentTournamentMatchup.secondPlayer, secondPlayerResults, firstPlayerResults)
            currentTournamentMatchup.remainingGames--

            for (player in tournament.playersList) {
                if (player.seriesPlayerId == currentTournamentMatchup.firstPlayer.seriesPlayerId) {
                    updateCertainPlayerStats(player, firstPlayerResults, secondPlayerResults)
                    tournamentsRepository.update(player)
                }
                if (player.seriesPlayerId == currentTournamentMatchup.secondPlayer.seriesPlayerId) {
                    updateCertainPlayerStats(player, secondPlayerResults, firstPlayerResults)
                    tournamentsRepository.update(player)
                }
            }
            tournamentsRepository.update(currentTournamentMatchup)
            tournamentsRepository.update(tournament)
            _isGameEvaluated.value = true
        }
    }


    private fun updateCertainPlayerStats(player: SeriesPlayer, firstPlayerResults: Results, secondPlayerResults: Results) {
        player.pointsDiff += (firstPlayerResults.statistics.scoreSum - secondPlayerResults.statistics.scoreSum).toInt()
        player.wonLegs += firstPlayerResults.statistics.wonLegs
        player.lostLegs += secondPlayerResults.statistics.wonLegs

        if (firstPlayerResults.wonGame) {
            player.wins++
            return
        }
        player.defeats++
    }

    private suspend fun updateSavedPlayerStats(playerId: Long, results: Results) {
        val player = playersRepository.get(playerId)!!
        player.statistics.gamesPlayed += results.statistics.gamesPlayed
        player.statistics.wins += results.statistics.wins
        player.statistics.defeats += results.statistics.defeats
        player.statistics.wonLegs += results.statistics.wonLegs
        player.statistics.lostLegs += results.statistics.lostLegs
        player.statistics.scoreSum += results.statistics.scoreSum
        player.statistics.scoreCount += results.statistics.scoreCount
        player.statistics.singles += results.statistics.singles
        player.statistics.doubles += results.statistics.doubles
        player.statistics.triples += results.statistics.triples

        for (elem in results.statistics.scoresMap)
            player.statistics.scoresMap[elem.key] = player.statistics.scoresMap[elem.key]!! + elem.value

        playersRepository.update(player)
    }

}