package com.example.letsdart.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.MatchupsListItemBinding
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup


class MatchupListAdapter(private val tournamentMatchupClickListener: TournamentMatchupClickListener) :
    ListAdapter<TournamentMatchup, MatchupListAdapter.MatchupViewHolder>(
        MatchupsComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchupViewHolder {
        return MatchupViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(Holder: MatchupViewHolder, position: Int) {
        val current = getItem(position)
        Holder.bind(current, tournamentMatchupClickListener)
    }


    class MatchupViewHolder(private val binding: MatchupsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentMatchup: TournamentMatchup, tournamentMatchupClickListener: TournamentMatchupClickListener) {
            binding.textPlayerNameFirst.text = tournamentMatchup.firstPlayer.savedPlayer.playerName
            binding.textPointsDiffValueFirst.text = tournamentMatchup.firstPlayer.pointsDiff.toString()
            binding.textLegsRatioValueFirst.text = "${tournamentMatchup.firstPlayer.wonLegs}/${tournamentMatchup.firstPlayer.lostLegs}"
            binding.textMatchupScoreFirst.text = tournamentMatchup.firstPlayer.wins.toString()

            binding.textPlayerNameSecond.text = tournamentMatchup.secondPlayer.savedPlayer.playerName
            binding.textPointsDiffValueSecond.text = tournamentMatchup.secondPlayer.pointsDiff.toString()
            binding.textLegsRatioValueSecond.text = "${tournamentMatchup.secondPlayer.wonLegs}/${tournamentMatchup.secondPlayer.lostLegs}"
            binding.textMatchupScoreSecond.text = tournamentMatchup.secondPlayer.wins.toString()

            if (tournamentMatchup.winnerId == null)
                binding.buttonPlay.setOnClickListener {
                    tournamentMatchupClickListener.onPlayClicked(tournamentMatchup)
                }
            else {
                binding.buttonPlay.visibility = View.INVISIBLE
                if (tournamentMatchup.winnerId == tournamentMatchup.firstPlayer.seriesPlayerId)
                    binding.textPlayerNameSecond.setTextColor(Color.LTGRAY)
                else
                    binding.textPlayerNameFirst.setTextColor(Color.LTGRAY)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MatchupViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MatchupsListItemBinding.inflate(layoutInflater, parent, false)
                return MatchupViewHolder(
                    binding
                )
            }
        }
    }
}

class MatchupsComparator : DiffUtil.ItemCallback<TournamentMatchup>() {
    override fun areItemsTheSame(oldItem: TournamentMatchup, newItem: TournamentMatchup): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: TournamentMatchup, newItem: TournamentMatchup): Boolean {
        return oldItem.firstPlayer == newItem.firstPlayer && oldItem.secondPlayer == newItem.secondPlayer
    }
}

interface TournamentMatchupClickListener {
    fun onPlayClicked(tournamentMatchup: TournamentMatchup)
}


