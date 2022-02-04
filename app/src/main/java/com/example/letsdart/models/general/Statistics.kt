package com.example.letsdart.models.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Statistics(
    var gamesPlayed: Int = 0,
    var wins: Int = 0,
    var defeats: Int = 0,
    var wonLegs: Int = 0,
    var lostLegs: Int = 0,
    var scoreSum: Long = 0,
    var scoreCount: Int = 0, //long?
    var singles: Int = 0,
    var doubles: Int = 0,
    var triples: Int = 0,
    val scoresMap: MutableMap<String, Int> = Player.createFirstScoresMap()
) : Parcelable
