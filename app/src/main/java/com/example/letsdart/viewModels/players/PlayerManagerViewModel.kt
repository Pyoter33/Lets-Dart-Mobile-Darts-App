package com.example.letsdart.viewModels.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.models.general.SavedPlayer
import kotlinx.coroutines.launch

class PlayerManagerViewModel(private val repository: PlayersRepository) : ViewModel() {

    val playersList: LiveData<List<SavedPlayer>> = repository.playersList.asLiveData()

    fun insertPlayer(player: SavedPlayer) {
        viewModelScope.launch {
            repository.insert(player)
        }
    }

    fun updatePlayer(player: SavedPlayer) {
        viewModelScope.launch {
            repository.update(player)
        }
    }

    fun deletePlayer(player: SavedPlayer){
        viewModelScope.launch {
            repository.delete(player)
        }
    }

}