package com.example.letsdart.models.series

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.letsdart.models.general.SavedPlayer
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "series_players_table") //vars or vals?
data class SeriesPlayer (
    @PrimaryKey(autoGenerate = true)
    val seriesPlayerId: Long = 0L,

    @Embedded
    val savedPlayer: SavedPlayer,

    @ColumnInfo(name = "seriesId")
    var seriesId: Long = 0L,

    @ColumnInfo(name = "wins")
    var wins: Int = 0,

    @ColumnInfo(name = "defeats")
    var defeats: Int = 0,

    @ColumnInfo(name = "wonLegs")
    var wonLegs: Int = 0,

    @ColumnInfo(name = "lostLegs")
    var lostLegs: Int = 0,

    @ColumnInfo(name = "pointsDiff")
    var pointsDiff: Int = 0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(SavedPlayer::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(seriesPlayerId)
        parcel.writeParcelable(savedPlayer, flags)
        parcel.writeLong(seriesId)
        parcel.writeInt(wins)
        parcel.writeInt(defeats)
        parcel.writeInt(wonLegs)
        parcel.writeInt(lostLegs)
        parcel.writeInt(pointsDiff)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SeriesPlayer> {
        override fun createFromParcel(parcel: Parcel): SeriesPlayer {
            return SeriesPlayer(parcel)
        }

        override fun newArray(size: Int): Array<SeriesPlayer?> {
            return arrayOfNulls(size)
        }
    }
}
