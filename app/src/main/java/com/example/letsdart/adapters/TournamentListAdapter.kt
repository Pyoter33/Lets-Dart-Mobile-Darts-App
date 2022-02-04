package com.example.letsdart.tournamentCreator.tournamentsMenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.LeagueListItemBinding
import com.example.letsdart.models.tournament.Tournament
import java.text.SimpleDateFormat


class TournamentListAdapter(private val tournamentClickListener: TournamentClickListener) :
    ListAdapter<Tournament, TournamentListAdapter.TournamentViewHolder>(
        TournamentComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        return TournamentViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, tournamentClickListener)
    }


    class TournamentViewHolder(private val binding: LeagueListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(tournament: Tournament, tournamentClickListener: TournamentClickListener) {
            binding.tournament = tournament
            binding.textLeagueName.text = tournament.name
            binding.textDate.text = SimpleDateFormat("dd.MM.yyyy")
                .format(tournament.date)

            itemView.setOnClickListener {
                tournamentClickListener.onTournamentClicked(tournament.id)
            }

            binding.buttonDelete.setOnClickListener {
                tournamentClickListener.onDeleteTournamentClicked(tournament)
            }

        }

        companion object {
            fun create(parent: ViewGroup): TournamentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeagueListItemBinding.inflate(layoutInflater, parent, false)
                return TournamentViewHolder(
                    binding
                )
            }
        }
    }
}

class TournamentComparator : DiffUtil.ItemCallback<Tournament>() {
    override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem.name == newItem.name
    }
}


interface TournamentClickListener {
    fun onDeleteTournamentClicked(tournament: Tournament)
    fun onTournamentClicked(tournamentId: Long)

}
