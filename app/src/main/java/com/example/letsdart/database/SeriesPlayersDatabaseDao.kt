package com.example.letsdart.database


import androidx.room.*
import com.example.letsdart.models.series.SeriesPlayer

@Dao
abstract class SeriesPlayersDatabaseDao {

    suspend fun insertWithIds(id: Long, players: List<SeriesPlayer>){
        for (player in players)
            player.seriesId = id
        insert(players)
    }

    suspend fun insertWithId(id: Long, player: SeriesPlayer): Long{
        player.seriesId = id
        return insert(player)
    }

    @Insert
    abstract suspend fun insert(players: List<SeriesPlayer>)

    @Insert
    abstract suspend fun insert(players: SeriesPlayer): Long

    @Update
    abstract suspend fun update(player: SeriesPlayer)

    @Delete
    abstract suspend fun delete(player: SeriesPlayer)

}