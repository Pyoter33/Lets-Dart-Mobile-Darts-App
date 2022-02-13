package com.example.letsdart.viewmodels.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letsdart.models.league.League
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.models.league.LeagueMatchup
import kotlinx.coroutines.launch

class LeaguePagerViewModel(private val leaguesRepository: LeaguesRepository) :
    ViewModel() {

    private val _league = MutableLiveData<League?>(null)
    val league: LiveData<League?> = _league

    fun initializeNewestLeague() {
        viewModelScope.launch {
            _league.value = leaguesRepository.getNewestLeague()
        }
    }

    fun getUnfinishedMatchups(matchups: List<LeagueMatchup>): List<LeagueMatchup> {
        val unfinishedMatchups = mutableListOf<LeagueMatchup>()
        for (elem in matchups)
            if (elem.winnerId == null)
                unfinishedMatchups.add(elem)
        return unfinishedMatchups
    }

    fun initializeSavedLeague(id: Long) {
        viewModelScope.launch {
            _league.value = leaguesRepository.get(id)
        }
    }

    fun deleteFinishedLeague() {
        viewModelScope.launch {
            for (player in league.value!!.playersList)
                leaguesRepository.delete(player)
            for (matchup in league.value!!.matchupsList)
                leaguesRepository.delete(matchup)
            leaguesRepository.delete(league.value!!)
        }
    }
}