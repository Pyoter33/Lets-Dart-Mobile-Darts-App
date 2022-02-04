package com.example.letsdart.database.tournamentDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
abstract class TournamentMatchupsDatabaseDao {
    suspend fun insertWithIds(id: Long, tournamentMatchups: List<TournamentMatchup>) {
        for (matchup in tournamentMatchups)
            matchup.seriesId = id
        insert(tournamentMatchups)
    }

    @Insert
    abstract suspend fun insert(tournamentMatchups: List<TournamentMatchup>)

    @Update
    abstract suspend fun update(tournamentMatchup: TournamentMatchup)

    @Delete
    abstract suspend fun delete(tournamentMatchup: TournamentMatchup)

}