package com.example.letsdart.viewmodels.players

import androidx.lifecycle.*
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.models.general.SavedPlayer

class StatisticsListViewModel(
    playersRepository: PlayersRepository,
) : ViewModel() {
    val playersList: LiveData<List<SavedPlayer>> = playersRepository.playersList.asLiveData()

}