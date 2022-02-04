package com.example.letsdart.models.general

import androidx.lifecycle.MutableLiveData
import java.util.*

class Player {
    companion object {
        val POSSIBLE_SCORES = listOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "25",
            "D1",
            "D2",
            "D3",
            "D4",
            "D5",
            "D6",
            "D7",
            "D8",
            "D9",
            "D10",
            "D11",
            "D12",
            "D13",
            "D14",
            "D15",
            "D16",
            "D17",
            "D18",
            "D19",
            "D20",
            "D25",
            "T1",
            "T2",
            "T3",
            "T4",
            "T5",
            "T6",
            "T7",
            "T8",
            "T9",
            "T10",
            "T11",
            "T12",
            "T13",
            "T14",
            "T15",
            "T16",
            "T17",
            "T18",
            "T19",
            "T20",
            "OT"
        )

        fun createFirstScoresMap(): MutableMap<String, Int> {
            val scoresMap = mutableMapOf<String, Int>()
            for (elem in POSSIBLE_SCORES)
                scoresMap[elem] = 0
            return scoresMap
        }
    }

    val mainScore = MutableLiveData<Int>()
    val roundScore = MutableLiveData<Int>()
    val triples = MutableLiveData<Int>()
    val doubles = MutableLiveData<Int>()
    val wonSets = MutableLiveData<Int>()
    val wonLegs = MutableLiveData<Int>()
    val average = MutableLiveData<Float>()
    val partialScore: MutableList<MutableLiveData<String>> =
        mutableListOf(MutableLiveData<String>(""), MutableLiveData<String>(""), MutableLiveData<String>(""))

    val stateStack = Stack<PlayerState>()
    val roundStateStack = Stack<PlayerState>()

    lateinit var currentState: PlayerState
    lateinit var currentRoundState: PlayerState

}