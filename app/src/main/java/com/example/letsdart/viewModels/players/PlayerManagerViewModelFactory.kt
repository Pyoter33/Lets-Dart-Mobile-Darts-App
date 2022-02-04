package com.example.letsdart.viewModels.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.playersDatabase.PlayersRepository

class PlayerManagerViewModelFactory(private val repository: PlayersRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerManagerViewModel::class.java)) {
            return PlayerManagerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}