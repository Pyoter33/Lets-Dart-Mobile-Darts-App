package com.example.letsdart.viewModels.tournament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.models.general.Rules
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class ChoosePlayersTournamentViewModelFactory(
    private val name: String,
    private val rules: Rules,
    private val tournamentsRepository: TournamentsRepository,
    private val playersRepository: PlayersRepository

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoosePlayersTournamentViewModel::class.java)) {
            return ChoosePlayersTournamentViewModel(name, rules, tournamentsRepository, playersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}