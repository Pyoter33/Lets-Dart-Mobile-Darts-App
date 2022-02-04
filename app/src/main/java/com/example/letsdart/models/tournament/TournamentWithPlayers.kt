package com.example.letsdart.models.tournament

import androidx.room.Embedded
import androidx.room.Relation
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup

data class TournamentWithPlayers(
    @Embedded
    val tournament: Tournament,

    @Relation(parentColumn = "id", entityColumn = "seriesId", entity = SeriesPlayer::class)
    val playersList: List<SeriesPlayer>,

    @Relation(parentColumn = "id", entityColumn = "seriesId", entity = TournamentMatchup::class)
    val tournamentMatchups: List<TournamentMatchup>
)