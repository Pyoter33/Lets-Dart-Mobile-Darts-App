package com.example.letsdart.viewmodels.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.models.tournament.Matchup
import com.example.letsdart.models.general.Results
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class FinishedGameViewModelFactory(
    private val firstPlayerResults: Results,
    private val secondPlayerResults: Results,
    private val matchup: Matchup,
    private val leaguesRepository: LeaguesRepository,
    private val tournamentsRepository: TournamentsRepository,
    private val playersRepository: PlayersRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishedGameViewModel::class.java)) {
            return FinishedGameViewModel(
               firstPlayerResults, secondPlayerResults, matchup, leaguesRepository, tournamentsRepository, playersRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}