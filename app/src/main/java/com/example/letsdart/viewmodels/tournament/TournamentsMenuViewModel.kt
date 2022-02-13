package com.example.letsdart.viewmodels.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import kotlinx.coroutines.launch

class TournamentsMenuViewModel(private val tournamentsRepository: TournamentsRepository) : ViewModel() {

    val tournamentList: LiveData<List<Tournament>> = tournamentsRepository.tournamentsList.asLiveData()

    fun deleteTournament(tournament: Tournament){
        viewModelScope.launch {
            for (player in tournament.playersList)
                tournamentsRepository.delete(player)
            for (key in tournament.matchups.keys)
                for (matchup in tournament.matchups[key]!!)
                    tournamentsRepository.delete(matchup)
            tournamentsRepository.delete(tournament)
        }
    }
}