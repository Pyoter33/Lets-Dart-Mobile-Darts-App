package com.example.letsdart.models.general

import android.os.Parcel
import android.os.Parcelable
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.models.tournament.Matchup

data class QuickMatchup (override val firstPlayer: SeriesPlayer, override val secondPlayer: SeriesPlayer): Matchup {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(SeriesPlayer::class.java.classLoader)!!,
        parcel.readParcelable(SeriesPlayer::class.java.classLoader)!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(firstPlayer, flags)
        parcel.writeParcelable(secondPlayer, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuickMatchup> {
        override fun createFromParcel(parcel: Parcel): QuickMatchup {
            return QuickMatchup(parcel)
        }

        override fun newArray(size: Int): Array<QuickMatchup?> {
            return arrayOfNulls(size)
        }
    }
}