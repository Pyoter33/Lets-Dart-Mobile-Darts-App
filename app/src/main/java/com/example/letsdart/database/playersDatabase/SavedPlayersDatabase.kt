package com.example.letsdart.database.playersDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.utils.Converters
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Database(entities = [SavedPlayer::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SavedPlayersDatabase : RoomDatabase() {

    abstract val savedPlayersDatabaseDao: SavedPlayersDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SavedPlayersDatabase? = null

        fun getInstance(context: Context): SavedPlayersDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavedPlayersDatabase::class.java,
                        "saved_players_table"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance

                }


                return instance
            }

        }
    }
}