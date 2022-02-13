package com.example.letsdart.viewmodels.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class PlayerManagerViewModelFactory(private val playersRepository: PlayersRepository, private val tournamentsRepository: TournamentsRepository, private val leaguesRepository: LeaguesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerManagerViewModel::class.java)) {
            return PlayerManagerViewModel(playersRepository, tournamentsRepository, leaguesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}