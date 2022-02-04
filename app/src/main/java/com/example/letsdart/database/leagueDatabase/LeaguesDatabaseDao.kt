package com.example.letsdart.database.leagueDatabase

import androidx.room.*
import com.example.letsdart.models.league.League
import com.example.letsdart.models.league.LeagueWithPlayers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.transform

@Dao
abstract class LeaguesDatabaseDao {

    fun getAllLeagues(): Flow<List<League>>{
        val leaguesWithPlayers = _getAllLeagues()

        return leaguesWithPlayers.transform{ elem ->
                emit(elem.map { listElem ->
                    convertLeague(listElem)
                })
        }
    }

    suspend fun get(key: Long): League?{
        val leagueWithPlayers = _get(key) ?: return null

        return convertLeague(leagueWithPlayers)
    }

    suspend fun getNewestLeague(): League = convertLeague(_getNewestLeague())


    private fun convertLeague(leagueWithPlayers: LeagueWithPlayers): League {
        val league = leagueWithPlayers.league
        league.playersList = leagueWithPlayers.playersList
        league.matchupsList = leagueWithPlayers.matchupsList
        return league
    }

    @Insert
    abstract suspend fun insert(league: League): Long

    @Update
    abstract suspend fun update(league: League)

    @Delete
    abstract suspend fun delete(league: League)

    @Transaction
    @Query("SELECT * FROM leagues_table")
    abstract fun _getAllLeagues(): Flow<List<LeagueWithPlayers>>

    @Transaction
    @Query("SELECT * FROM leagues_table WHERE id = :key")
    abstract suspend fun _get(key: Long): LeagueWithPlayers?

    @Transaction
    @Query("SELECT * FROM leagues_table ORDER BY start_date DESC LIMIT 1")
    abstract suspend fun _getNewestLeague(): LeagueWithPlayers
}