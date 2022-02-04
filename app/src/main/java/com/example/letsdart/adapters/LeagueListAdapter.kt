package com.example.letsdart.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.LeagueListItemBinding
import com.example.letsdart.models.league.League
import java.text.SimpleDateFormat


class LeagueListAdapter(private val leagueClickListener: LeagueClickListener) :
    ListAdapter<League, LeagueListAdapter.LeagueViewHolder>(
        LeagueComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, leagueClickListener)
    }


    class LeagueViewHolder(private val binding: LeagueListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(league: League, leagueClickListener: LeagueClickListener) {
            binding.league = league
            binding.textLeagueName.text = league.name
            binding.textDate.text = SimpleDateFormat("dd.MM.yyyy")
                .format(league.date)

            itemView.setOnClickListener {
                leagueClickListener.onLeagueClicked(league.id)

            }

            binding.buttonDelete.setOnClickListener {
                leagueClickListener.onDeleteLeagueClicked(league)
            }

        }

        companion object {
            fun create(parent: ViewGroup): LeagueViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeagueListItemBinding.inflate(layoutInflater, parent, false)
                return LeagueViewHolder(
                    binding
                )
            }
        }
    }
}

class LeagueComparator : DiffUtil.ItemCallback<League>() {
    override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
        return oldItem.name == newItem.name
    }
}


interface LeagueClickListener {
    fun onDeleteLeagueClicked(league: League)
    fun onLeagueClicked(leagueId: Long)

}
