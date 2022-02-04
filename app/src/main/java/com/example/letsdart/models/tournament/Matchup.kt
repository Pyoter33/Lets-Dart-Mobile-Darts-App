package com.example.letsdart.models.tournament

import android.os.Parcelable
import com.example.letsdart.models.series.SeriesPlayer


interface Matchup:  Parcelable{
    val firstPlayer: SeriesPlayer
    val secondPlayer: SeriesPlayer
}


interface SeriesMatchup : Matchup {
    val matchupId: Long
    var seriesId: Long
    override val firstPlayer: SeriesPlayer
    override val secondPlayer: SeriesPlayer
    var winnerId: Long?
}