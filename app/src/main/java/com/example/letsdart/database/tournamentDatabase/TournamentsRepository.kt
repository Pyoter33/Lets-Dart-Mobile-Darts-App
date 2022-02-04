package com.example.letsdart.database.tournamentDatabase

import androidx.annotation.WorkerThread
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.SeriesPlayersDatabaseDao
import com.example.letsdart.models.tournament.Tournament
import kotlinx.coroutines.flow.Flow

class TournamentsRepository(private val tournamentsDatabaseDao: TournamentsDatabaseDao, private val seriesPlayersDatabaseDao: SeriesPlayersDatabaseDao, private val matchupsDatabaseDao: TournamentMatchupsDatabaseDao) {

    val tournamentsList: Flow<List<Tournament>> = tournamentsDatabaseDao.getAllTournaments()

    @WorkerThread
    suspend fun insert(tournament: Tournament): Long{
        return tournamentsDatabaseDao.insert(tournament)
    }

    @WorkerThread
    suspend fun get(key:Long): Tournament? {
        return tournamentsDatabaseDao.get(key)
    }

    @WorkerThread
    suspend fun update(tournament: Tournament){
        tournamentsDatabaseDao.update(tournament)
    }

    @WorkerThread
    suspend fun delete(tournament: Tournament){
        tournamentsDatabaseDao.delete(tournament)
    }

    @WorkerThread
    suspend fun getNewestTournament(): Tournament {
        return tournamentsDatabaseDao.getNewestTournament()
    }


    @WorkerThread
    suspend fun insert(id: Long, player: SeriesPlayer): Long {
        return seriesPlayersDatabaseDao.insertWithId(id, player)
    }

    @WorkerThread
    suspend fun insert(id: Long, players: List<SeriesPlayer>) {
        seriesPlayersDatabaseDao.insertWithIds(id, players)
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
    suspend fun insert(id: Long, tournamentMatchups: List<TournamentMatchup>) {
        matchupsDatabaseDao.insertWithIds(id, tournamentMatchups)
    }

    @WorkerThread
    suspend fun update(tournamentMatchup: TournamentMatchup){
        matchupsDatabaseDao.update(tournamentMatchup)
    }

    @WorkerThread
    suspend fun delete(tournamentMatchup: TournamentMatchup){
        matchupsDatabaseDao.delete(tournamentMatchup)
    }


}