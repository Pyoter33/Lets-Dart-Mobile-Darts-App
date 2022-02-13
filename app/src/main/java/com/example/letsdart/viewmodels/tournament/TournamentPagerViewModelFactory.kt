package com.example.letsdart.viewmodels.tournament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class TournamentPagerViewModelFactory(
    private val tournamentsRepository: TournamentsRepository

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TournamentPagerViewModel::class.java)) {
            return TournamentPagerViewModel(tournamentsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}