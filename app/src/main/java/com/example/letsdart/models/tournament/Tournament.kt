package com.example.letsdart.models.tournament

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.series.Series
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup

@Entity(tableName = "tournaments_table")
data class Tournament(
    @ColumnInfo(name = "name")
    override var name: String,

    @ColumnInfo(name = "rules")
    override var rules: Rules = Rules(0,0,0,0,0,0, false),

    @ColumnInfo(name = "start_date")
    override var date: Long = System.currentTimeMillis(),

    var maxLevel: Int = 0,

    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L
): Series {
    @Ignore
    override var playersList: List<SeriesPlayer> = listOf()
    @Ignore
    var matchups: MutableMap<Int,List<TournamentMatchup>> = mutableMapOf()
}

