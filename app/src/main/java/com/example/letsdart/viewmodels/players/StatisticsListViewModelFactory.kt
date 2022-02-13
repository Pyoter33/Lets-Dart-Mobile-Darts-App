package com.example.letsdart.viewmodels.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.playersDatabase.PlayersRepository

class StatisticsListViewModelFactory(private val playersRepository: PlayersRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsListViewModel::class.java)) {
            return StatisticsListViewModel(playersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}