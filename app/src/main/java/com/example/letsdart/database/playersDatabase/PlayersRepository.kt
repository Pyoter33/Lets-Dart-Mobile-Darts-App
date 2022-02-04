package com.example.letsdart.database.playersDatabase

import androidx.annotation.WorkerThread
import com.example.letsdart.models.general.SavedPlayer
import kotlinx.coroutines.flow.Flow

class PlayersRepository(private val savedPlayersDatabaseDao: SavedPlayersDatabaseDao) {

    val playersList: Flow<List<SavedPlayer>> = savedPlayersDatabaseDao.getAllPlayers()

    @WorkerThread
    suspend fun get(id: Long): SavedPlayer?{
        return savedPlayersDatabaseDao.get(id)
    }

    @WorkerThread
    suspend fun insert(player: SavedPlayer){
        savedPlayersDatabaseDao.insert(player)
    }

    @WorkerThread
    suspend fun update(player: SavedPlayer){
        savedPlayersDatabaseDao.update(player)
    }

    @WorkerThread
    suspend fun delete(player: SavedPlayer){
        savedPlayersDatabaseDao.delete(player)
    }

}