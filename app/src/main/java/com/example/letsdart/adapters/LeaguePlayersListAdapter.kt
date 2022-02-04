package com.example.letsdart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.R
import com.example.letsdart.databinding.LeaguePlayersListItemBinding
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.utils.SeriesPlayersComparator


class LeaguePlayersListAdapter:
    ListAdapter<SeriesPlayer, LeaguePlayersListAdapter.PlayerViewHolder>(
        SeriesPlayersComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, position + 1)
    }


    class PlayerViewHolder(private val binding: LeaguePlayersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: SeriesPlayer, position: Int) {
            when (position) {
                1 -> binding.itemLayout.background =
                    getDrawable(itemView.context, R.drawable.league_player_first_position)
                2 -> binding.itemLayout.background =
                    getDrawable(itemView.context, R.drawable.league_player_second_position)
                3 -> binding.itemLayout.background =
                    getDrawable(itemView.context, R.drawable.league_player_third_position)
            }

            binding.textPlayerName.text = player.savedPlayer.playerName
            binding.textPlayerPosition.text = position.toString()
            binding.textDefeatsValue.text = player.defeats.toString()
            binding.textWinsValue.text = player.wins.toString()
            binding.textLegsRatioValue.text = "${player.wonLegs}/${player.lostLegs}"

            binding.textPointsDiffValue.text = player.pointsDiff.toString()
        }

        companion object {
            fun create(parent: ViewGroup): PlayerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeaguePlayersListItemBinding.inflate(layoutInflater, parent, false)
                return PlayerViewHolder(
                    binding
                )
            }
        }
    }
}



