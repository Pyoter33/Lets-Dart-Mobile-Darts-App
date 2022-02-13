package com.example.letsdart.viewmodels.tournament

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letsdart.utils.LeaguePlayersComparator
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import com.example.letsdart.utils.MatchupsGenerator
import kotlinx.coroutines.launch

class TournamentPagerViewModel(private val tournamentsRepository: TournamentsRepository) :
    ViewModel() {

    private val _tournament = MutableLiveData<Tournament?>(null)
    val tournament: LiveData<Tournament?> = _tournament

    private val _finished = MutableLiveData(false)
    val finished: LiveData<Boolean> = _finished

    fun initializeNewestTournament() {
        viewModelScope.launch {
            _tournament.value = tournamentsRepository.getNewestTournament()
            tournamentsRepository.update(_tournament.value!!)
        }
    }

    fun initializeSavedTournament(id: Long) {
        viewModelScope.launch {
            val updatedTournament = tournamentsRepository.get(id)
            Log.i("tournament", updatedTournament!!.playersList.toString())
            updateWinners(updatedTournament!!)

            val newGames = checkForNewMatchups(updatedTournament)
            when {
                updatedTournament.matchups[updatedTournament.maxLevel]!!.size == 1 && newGames -> _finished.value = true
               newGames -> {
                   val newMatchups = MatchupsGenerator.generateNextMatchups(updatedTournament.matchups[updatedTournament.maxLevel]!!, updatedTournament.rules, ++updatedTournament.maxLevel)
                   updatedTournament.matchups[updatedTournament.maxLevel] = newMatchups
                   tournamentsRepository.insert(updatedTournament.id, newMatchups)
                   tournamentsRepository.update(updatedTournament)
                }
            }
            _tournament.value = tournamentsRepository.get(id)
        }
    }

    private suspend fun updateWinners(updatedTournament: Tournament) {
        for (matchup in updatedTournament.matchups[updatedTournament.maxLevel]!!) {
            if (matchup.winnerId == null) {
                if (matchup.remainingGames <= 0) {
                    val tempList = listOf(matchup.firstPlayer, matchup.secondPlayer).sortedWith(LeaguePlayersComparator)
                    matchup.winnerId = tempList[0].seriesPlayerId
                    tournamentsRepository.update(matchup)
                }
            }
        }
    }

    fun deleteFinishedTournament() {
        viewModelScope.launch {
            Log.i("tournament", tournament.value!!.playersList.toString())
            for (player in tournament.value!!.playersList)
                tournamentsRepository.delete(player)
            for (key in tournament.value!!.matchups.keys)
                for (matchup in tournament.value!!.matchups[key]!!)
                    tournamentsRepository.delete(matchup)
            tournamentsRepository.delete(tournament.value!!)
        }

    }

    private fun checkForNewMatchups(tournament: Tournament): Boolean {
        if(tournament.matchups[tournament.maxLevel]!!.isEmpty())
            return false
        for (elem in tournament.matchups[tournament.maxLevel]!!) {
            if (elem.winnerId == null) {
                return false
            }
        }
        return true
    }
}