package com.example.letsdart.database.playersDatabase

import androidx.room.*
import com.example.letsdart.models.general.SavedPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPlayersDatabaseDao {

    @Insert
    suspend fun insert(player: SavedPlayer)

    @Update
    suspend fun update(player: SavedPlayer)

    @Delete
    suspend fun delete(player: SavedPlayer)

    @Query("SELECT * FROM saved_players_table WHERE playerId = :key")
    suspend fun get(key: Long): SavedPlayer?

    @Query("SELECT * FROM saved_players_table ORDER BY playerId DESC")
    fun getAllPlayers(): Flow<List<SavedPlayer>>

}