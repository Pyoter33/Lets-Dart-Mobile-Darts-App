package com.example.letsdart.database.tournamentDatabase

import androidx.room.*
import com.example.letsdart.models.tournament.TournamentWithPlayers
import com.example.letsdart.models.tournament.Tournament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

@Dao
abstract class  TournamentsDatabaseDao {

    fun getAllTournaments(): Flow<List<Tournament>>{
        val tournamentWithPlayers = _getAllTournaments()

        return tournamentWithPlayers.transform{ elem ->
            emit(elem.map { listElem ->
                convertTournament(listElem)
            })
        }
    }

    suspend fun get(key: Long): Tournament?{
        val tournamentWithPlayers = _get(key) ?: return null

        return convertTournament(tournamentWithPlayers)
    }

    suspend fun getNewestTournament(): Tournament = convertTournament(_getNewestTournament())


    private fun convertTournament(tournamentWithPlayers: TournamentWithPlayers): Tournament {
        val tournament = tournamentWithPlayers.tournament
        tournament.playersList = tournamentWithPlayers.playersList
        val map = mutableMapOf<Int, MutableList<TournamentMatchup>>()
        for (i in 0..tournament.maxLevel)
            map[i] = mutableListOf()

        for(elem in tournamentWithPlayers.tournamentMatchups){
            map[elem.level]!!.add(elem)
        }
        tournament.matchups = map as (MutableMap<Int, List<TournamentMatchup>>)

        return tournament
    }


    @Insert
    abstract suspend fun insert(tournament: Tournament): Long

    @Update
    abstract suspend fun update(tournament: Tournament)

    @Delete
    abstract suspend fun delete(tournament: Tournament)

    @Transaction
    @Query("SELECT * FROM tournaments_table WHERE id = :key")
    abstract suspend fun _get(key: Long): TournamentWithPlayers?

    @Transaction
    @Query("SELECT * FROM tournaments_table ORDER BY id DESC")
    abstract fun _getAllTournaments(): Flow<List<TournamentWithPlayers>>

    @Transaction
    @Query("SELECT * FROM tournaments_table ORDER BY start_date DESC LIMIT 1")
    abstract suspend fun _getNewestTournament(): TournamentWithPlayers

}
