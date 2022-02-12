package com.example.letsdart.viewModels.players

import android.util.Log
import androidx.lifecycle.*
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.models.series.Series
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class StatisticsListViewModel(
    playersRepository: PlayersRepository,
) : ViewModel() {
    val playersList: LiveData<List<SavedPlayer>> = playersRepository.playersList.asLiveData()

}