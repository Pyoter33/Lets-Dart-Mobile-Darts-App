package com.example.letsdart.viewmodels.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.letsdart.models.general.Rules

class GameViewModelFactory(
    private val rules: Rules
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(rules) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}