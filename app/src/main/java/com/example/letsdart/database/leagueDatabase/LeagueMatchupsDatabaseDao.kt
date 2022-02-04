package com.example.letsdart.database.leagueDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

import com.example.letsdart.models.league.LeagueMatchup
//TODO create base dao and inherit
@Dao
abstract class LeagueMatchupsDatabaseDao {
    suspend fun insertWithIds(id: Long, tournamentMatchups: List<LeagueMatchup>) {
        for (matchup in tournamentMatchups)
            matchup.seriesId = id
        insert(tournamentMatchups)
    }

    @Insert
    abstract suspend fun insert(tournamentMatchups: List<LeagueMatchup>)

    @Update
    abstract suspend fun update(tournamentMatchup: LeagueMatchup)

    @Delete
    abstract suspend fun delete(tournamentMatchup: LeagueMatchup)

}