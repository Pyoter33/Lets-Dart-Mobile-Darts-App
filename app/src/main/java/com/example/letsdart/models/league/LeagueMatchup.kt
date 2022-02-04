package com.example.letsdart.models.league

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.letsdart.models.tournament.SeriesMatchup
import com.example.letsdart.models.series.SeriesPlayer

@Entity(tableName = "matchups_table")
data class LeagueMatchup(
    @PrimaryKey(autoGenerate = true)
    override val matchupId: Long = 0L,
    override var seriesId: Long = 0L,
    @Embedded(prefix = "first")
    override val firstPlayer: SeriesPlayer,
    @Embedded(prefix = "second")
    override val secondPlayer: SeriesPlayer,
    override var winnerId: Long? = null
): SeriesMatchup {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(SeriesPlayer::class.java.classLoader)!!,
        parcel.readParcelable(SeriesPlayer::class.java.classLoader)!!,
        parcel.readValue(Long::class.java.classLoader) as? Long
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(matchupId)
        parcel.writeLong(seriesId)
        parcel.writeParcelable(firstPlayer, flags)
        parcel.writeParcelable(secondPlayer, flags)
        parcel.writeValue(winnerId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeagueMatchup> {
        override fun createFromParcel(parcel: Parcel): LeagueMatchup {
            return LeagueMatchup(parcel)
        }

        override fun newArray(size: Int): Array<LeagueMatchup?> {
            return arrayOfNulls(size)
        }
    }
}
