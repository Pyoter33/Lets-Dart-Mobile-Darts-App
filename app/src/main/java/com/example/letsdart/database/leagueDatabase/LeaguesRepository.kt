package com.example.letsdart.database.leagueDatabase

import androidx.annotation.WorkerThread
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.SeriesPlayersDatabaseDao
import com.example.letsdart.models.league.League
import com.example.letsdart.models.league.LeagueMatchup
import kotlinx.coroutines.flow.Flow

class LeaguesRepository(private val leaguesDatabaseDao: LeaguesDatabaseDao, private val seriesPlayersDatabaseDao: SeriesPlayersDatabaseDao, private val matchupsDatabaseDao: LeagueMatchupsDatabaseDao) {
    val leaguesList: Flow<List<League>> = leaguesDatabaseDao.getAllLeagues()

    @WorkerThread
    suspend fun insert(league: League): Long{
      return leaguesDatabaseDao.insert(league)
    }

    @WorkerThread
    suspend fun get(key:Long): League? {
      return leaguesDatabaseDao.get(key)
    }

    @WorkerThread
    suspend fun update(league: League){
        leaguesDatabaseDao.update(league)
    }

    @WorkerThread
    suspend fun delete(league: League){
        leaguesDatabaseDao.delete(league)
    }

    @WorkerThread
    suspend fun getNewestLeague(): League {
        return leaguesDatabaseDao.getNewestLeague()
    }

    @WorkerThread
    suspend fun insert(id: Long, player: SeriesPlayer): Long {
        return seriesPlayersDatabaseDao.insertWithId(id, player)
    }

    @WorkerThread
    suspend fun update(player: SeriesPlayer){
        seriesPlayersDatabaseDao.update(player)
    }

    @WorkerThread
    suspend fun delete(player: SeriesPlayer){
        seriesPlayersDatabaseDao.delete(player)
    }

    @JvmName("insertMatchup")
    @WorkerThread
    suspend fun insert(id: Long, leagueMatchups: List<LeagueMatchup>) {
        matchupsDatabaseDao.insertWithIds(id, leagueMatchups)
    }

    @WorkerThread
    suspend fun update(leagueMatchup: LeagueMatchup){
        matchupsDatabaseDao.update(leagueMatchup)
    }

    @WorkerThread
    suspend fun delete(leagueMatchup: LeagueMatchup){
        matchupsDatabaseDao.delete(leagueMatchup)
    }

}