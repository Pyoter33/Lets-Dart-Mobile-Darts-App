package com.example.letsdart.utils

import android.app.Application
import com.example.letsdart.database.leagueDatabase.LeaguesDatabase
import com.example.letsdart.database.leagueDatabase.LeaguesRepository
import com.example.letsdart.database.playersDatabase.SavedPlayersDatabase
import com.example.letsdart.database.playersDatabase.PlayersRepository
import com.example.letsdart.database.tournamentDatabase.TournamentsDatabase
import com.example.letsdart.database.tournamentDatabase.TournamentsRepository

class Application : Application() {

    private val playerDatabase by lazy { SavedPlayersDatabase.getInstance(this) }
    val playersRepository by lazy { PlayersRepository(playerDatabase.savedPlayersDatabaseDao) }

    private val leaguesDatabase by lazy { LeaguesDatabase.getInstance(this) }
    val leaguesRepository by lazy { LeaguesRepository(leaguesDatabase.leaguesDatabaseDao, leaguesDatabase.seriesPlayersDatabaseDao, leaguesDatabase.matchupsDatabaseDao) }

    private val tournamentsDatabase by lazy { TournamentsDatabase.getInstance(this) }
    val tournamentsRepository by lazy { TournamentsRepository(tournamentsDatabase.tournamentsDatabaseDao, tournamentsDatabase.seriesPlayerDatabaseDao, tournamentsDatabase.matchupsDatabaseDao) }

}