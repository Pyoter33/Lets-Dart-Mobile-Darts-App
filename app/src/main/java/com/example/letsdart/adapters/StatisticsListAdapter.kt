package com.example.letsdart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.StatisticsListItemBinding
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.utils.SavedPlayersComparator

class StatisticsListAdapter(private val statisticsClickListener: StatisticsClickListener) :
    ListAdapter<SavedPlayer, StatisticsListAdapter.StatisticsListViewHolder>(
        SavedPlayersComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsListViewHolder {
        return StatisticsListViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: StatisticsListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, statisticsClickListener)
    }


    class StatisticsListViewHolder(private val binding: StatisticsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: SavedPlayer, statisticsClickListener: StatisticsClickListener) {
            binding.textView.text = player.playerName

            binding.linearLayout.setOnClickListener {
                statisticsClickListener.onItemClicked(player)
            }
        }

        companion object {
            fun create(parent: ViewGroup): StatisticsListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StatisticsListItemBinding.inflate(layoutInflater, parent, false)
                return StatisticsListViewHolder(
                    binding
                )
            }
        }
    }
}


interface StatisticsClickListener {
    fun onItemClicked(player: SavedPlayer)
}
