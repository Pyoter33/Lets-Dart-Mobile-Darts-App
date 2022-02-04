package com.example.letsdart.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.LeagueGamesFinishedListItemBinding
import com.example.letsdart.databinding.LeagueGamesListItemBinding
import com.example.letsdart.models.league.LeagueMatchup


class LeagueGamesListAdapter(private val leagueGameClickListener: LeagueGameClickListener) :
    ListAdapter<LeagueMatchup, RecyclerView.ViewHolder>(
        GameComparator()
    ) {

    companion object {
        const val VIEW_TYPE_ACTIVE = 0
        const val VIEW_TYPE_FINISHED = 1
    }

    override fun getItemViewType(position: Int): Int {
        val current = getItem(position)
        return when (current.winnerId) {
            null -> VIEW_TYPE_ACTIVE
            else -> VIEW_TYPE_FINISHED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ACTIVE -> GameViewHolder.create(
                parent
            )
            else -> FinishedGameViewHolder.create(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = getItem(position)
        when (holder.itemViewType) {
            VIEW_TYPE_ACTIVE -> (holder as GameViewHolder).bind(current, leagueGameClickListener)
            else -> (holder as FinishedGameViewHolder).bind(current)
        }

    }


    class GameViewHolder(private val binding: LeagueGamesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(leagueMatchup: LeagueMatchup, leagueGameClickListener: LeagueGameClickListener) {
            binding.textFirstPlayer.text = leagueMatchup.firstPlayer.savedPlayer.playerName
            binding.textSecondPlayer.text = leagueMatchup.secondPlayer.savedPlayer.playerName

            binding.buttonPlay.setOnClickListener { leagueGameClickListener.onPlayClicked(leagueMatchup) }

        }

        companion object {
            fun create(parent: ViewGroup): GameViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeagueGamesListItemBinding.inflate(layoutInflater, parent, false)
                return GameViewHolder(
                    binding
                )
            }
        }
    }


    class FinishedGameViewHolder(private val binding: LeagueGamesFinishedListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(leagueMatchup: LeagueMatchup) {
            binding.textFirstPlayer.text = leagueMatchup.firstPlayer.savedPlayer.playerName
            binding.textSecondPlayer.text = leagueMatchup.secondPlayer.savedPlayer.playerName


            if (leagueMatchup.winnerId == leagueMatchup.firstPlayer.seriesPlayerId) {
                binding.textSecondPlayer.setTextColor(Color.LTGRAY)
                binding.textFirstPlayer.setTextColor(Color.BLACK)
            }
            else {
                binding.textFirstPlayer.setTextColor(Color.LTGRAY)
                binding.textSecondPlayer.setTextColor(Color.BLACK)
            }
        }

    companion object {
        fun create(parent: ViewGroup): FinishedGameViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LeagueGamesFinishedListItemBinding.inflate(layoutInflater, parent, false)
            return FinishedGameViewHolder(
                binding
            )
        }
    }
}
}

class GameComparator : DiffUtil.ItemCallback<LeagueMatchup>() {
    override fun areItemsTheSame(oldItem: LeagueMatchup, newItem: LeagueMatchup): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: LeagueMatchup, newItem: LeagueMatchup): Boolean {
        return oldItem.firstPlayer == newItem.firstPlayer
    }
}

interface LeagueGameClickListener {
    fun onPlayClicked(leagueMatchup: LeagueMatchup)
}