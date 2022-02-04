package com.example.letsdart.models.general

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "saved_players_table")
data class SavedPlayer(

    @ColumnInfo(name = "name")
    var playerName: String?,

    @ColumnInfo(name = "statistics")
    val statistics: Statistics = Statistics(),

    @PrimaryKey(autoGenerate = true)
    var playerId: Long = 0L,

): Parcelable
