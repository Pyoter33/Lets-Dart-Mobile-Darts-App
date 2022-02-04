package com.example.letsdart.models.general

interface PlayerState {
    val mainScore: Int
    val roundScore: Int
    val partialScore: List<String>
    val scoreSum: Int
    val scoreCount: Int
    val average: Float
    val wonLegs: Int
    val wonSets: Int
    val singles: Int
    val doubles: Int
    val triples: Int
    val allWonLegs: Int
    val scoresMap: MutableMap<String, Int>
}

data class DefaultPlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState

data class NewRoundPlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>

) : PlayerState

data class NewLegPlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState

data class NewSetPlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState

data class StartGamePlayerState(
    override val mainScore: Int,
    override val roundScore: Int = -1,
    override val partialScore: List<String> = listOf(),
    override val scoreSum: Int = 0,
    override val scoreCount: Int = 0,
    override val average: Float = -1f,
    override val wonLegs: Int = 0,
    override val wonSets: Int = 0,
    override val singles: Int = 0,
    override val doubles: Int = 0,
    override val triples: Int = 0,
    override val allWonLegs: Int = 0,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState


data class DoublePlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState

data class TriplePlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState

data class OverthrowPlayerState(
    override val mainScore: Int,
    override val roundScore: Int,
    override val partialScore: List<String>,
    override val scoreSum: Int,
    override val scoreCount: Int,
    override val average: Float,
    override val wonLegs: Int,
    override val wonSets: Int,
    override val singles: Int,
    override val doubles: Int,
    override val triples: Int,
    override val allWonLegs: Int,
    override val scoresMap: MutableMap<String, Int>
) : PlayerState