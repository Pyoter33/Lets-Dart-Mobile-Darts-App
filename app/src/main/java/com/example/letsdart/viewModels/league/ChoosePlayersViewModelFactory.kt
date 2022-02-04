package com.example.letsdart.viewModels.league

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.models.general.Rules
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository

class ChoosePlayersViewModelFactory(
    private val name: String,
    private val rules: Rules,
    private val leaguesRepository: LeaguesRepository,
    private val playersRepository: PlayersRepository

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoosePlayersViewModel::class.java)) {
            return ChoosePlayersViewModel(name, rules, leaguesRepository, playersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}