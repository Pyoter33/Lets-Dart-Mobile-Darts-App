package com.example.letsdart.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.models.series.SeriesPlayer

class LeaguePlayersComparator {

    companion object : Comparator<SeriesPlayer> {
        override fun compare(o1: SeriesPlayer, o2: SeriesPlayer): Int =
            when {
                o1.wins != o2.wins -> o2.wins - o1.wins
                o1.wonLegs != o2.wonLegs -> o2.wonLegs - o1.wonLegs
                else -> o2.pointsDiff - o1.pointsDiff
            }
    }
}

class SavedPlayersComparator : DiffUtil.ItemCallback<SavedPlayer>() {
    override fun areItemsTheSame(oldItem: SavedPlayer, newItem: SavedPlayer): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: SavedPlayer, newItem: SavedPlayer): Boolean {
        return oldItem.playerName == newItem.playerName
    }
}


class SeriesPlayersComparator : DiffUtil.ItemCallback<SeriesPlayer>() {
    override fun areItemsTheSame(oldItem: SeriesPlayer, newItem: SeriesPlayer): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: SeriesPlayer, newItem: SeriesPlayer): Boolean {
        return oldItem.savedPlayer == newItem.savedPlayer
    }
}