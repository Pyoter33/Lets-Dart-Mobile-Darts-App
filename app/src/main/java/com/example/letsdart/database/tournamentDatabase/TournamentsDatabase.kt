package com.example.letsdart.database.tournamentDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.letsdart.utils.Converters
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.SeriesPlayersDatabaseDao
import com.example.letsdart.models.tournament.Tournament

@Database(entities = [Tournament::class, SeriesPlayer::class, TournamentMatchup::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TournamentsDatabase : RoomDatabase() {
    abstract val tournamentsDatabaseDao: TournamentsDatabaseDao
    abstract val seriesPlayerDatabaseDao: SeriesPlayersDatabaseDao
    abstract val matchupsDatabaseDao: TournamentMatchupsDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: TournamentsDatabase? = null

        fun getInstance(context: Context): TournamentsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TournamentsDatabase::class.java,
                        "tournaments_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }

        }
    }
}