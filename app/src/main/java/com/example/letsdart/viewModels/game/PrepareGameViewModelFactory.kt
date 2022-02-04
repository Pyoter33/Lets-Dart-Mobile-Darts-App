package com.example.letsdart.viewModels.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.playersDatabase.PlayersRepository

class PrepareGameViewModelFactory(private val repository: PlayersRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrepareGameViewModel::class.java)) {
            return PrepareGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}