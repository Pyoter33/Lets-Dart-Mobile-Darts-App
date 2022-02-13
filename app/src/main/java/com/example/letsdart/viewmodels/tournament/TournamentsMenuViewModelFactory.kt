package com.example.letsdart.viewmodels.tournament


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class TournamentsMenuViewModelFactory(private val tournamentsRepository: TournamentsRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TournamentsMenuViewModel::class.java)) {
            return TournamentsMenuViewModel(tournamentsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}