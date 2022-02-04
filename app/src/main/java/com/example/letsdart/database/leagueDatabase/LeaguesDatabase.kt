package com.example.letsdart.database.leagueDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.letsdart.utils.Converters
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.SeriesPlayersDatabaseDao
import com.example.letsdart.models.league.League
import com.example.letsdart.models.league.LeagueMatchup


@Database(entities = [League::class, SeriesPlayer::class, LeagueMatchup::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LeaguesDatabase : RoomDatabase() {

    abstract val leaguesDatabaseDao: LeaguesDatabaseDao
    abstract val seriesPlayersDatabaseDao: SeriesPlayersDatabaseDao
    abstract val matchupsDatabaseDao: LeagueMatchupsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: LeaguesDatabase? = null

        fun getInstance(context: Context): LeaguesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LeaguesDatabase::class.java,
                        "leagues_table"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance

                }


                return instance
            }

        }
    }
}