package com.example.letsdart.models.league

import androidx.room.Embedded
import androidx.room.Relation
import com.example.letsdart.models.series.SeriesPlayer

data class LeagueWithPlayers(
    @Embedded
    val league: League,

    @Relation(parentColumn = "id", entityColumn = "seriesId", entity = SeriesPlayer::class)
    val playersList: List<SeriesPlayer>,

    @Relation(parentColumn = "id", entityColumn = "seriesId", entity = LeagueMatchup::class)
    val matchupsList: List<LeagueMatchup>
)