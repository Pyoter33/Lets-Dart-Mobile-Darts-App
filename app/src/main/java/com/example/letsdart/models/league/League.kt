package com.example.letsdart.models.league

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.series.Series
import com.example.letsdart.models.series.SeriesPlayer


@Entity(tableName = "leagues_table")
data class League  (

    @ColumnInfo(name = "name")
    override var name: String,

    @ColumnInfo(name = "rules")
    override var rules: Rules = Rules(0,0,0,0,0,0, false), //change default rules

    @ColumnInfo(name = "start_date")
    override var date: Long = System.currentTimeMillis(),

    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L,
): Series
{
    @Ignore var playersList: List<SeriesPlayer> = listOf()
    @Ignore var matchupsList: List<LeagueMatchup> = listOf()

}