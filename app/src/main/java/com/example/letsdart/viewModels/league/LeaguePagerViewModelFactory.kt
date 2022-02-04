package com.example.letsdart.viewModels.league

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.database.leagueDatabase.LeaguesRepository

class LeaguePagerViewModelFactory(
    private val leaguesRepository: LeaguesRepository

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaguePagerViewModel::class.java)) {
            return LeaguePagerViewModel(leaguesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}