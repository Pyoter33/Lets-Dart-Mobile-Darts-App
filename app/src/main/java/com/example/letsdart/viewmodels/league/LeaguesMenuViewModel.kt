package com.example.letsdart.viewmodels.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.letsdart.models.league.League
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import kotlinx.coroutines.launch

class LeaguesMenuViewModel(private val leaguesRepository: LeaguesRepository) : ViewModel() {

    val leagueList: LiveData<List<League>> = leaguesRepository.leaguesList.asLiveData()

    fun deleteLeague(league: League){
        viewModelScope.launch {
            for (player in league.playersList)
                leaguesRepository.delete(player)
            for (matchup in league.matchupsList)
                leaguesRepository.delete(matchup)
            leaguesRepository.delete(league)
        }
    }

}