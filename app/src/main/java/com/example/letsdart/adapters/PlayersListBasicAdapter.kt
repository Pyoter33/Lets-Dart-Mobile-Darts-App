package com.example.letsdart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.databinding.BasicListItemBinding


class PlayersListBasicAdapter(private val basicClickListener: BasicClickListener) :
    ListAdapter<Pair<SavedPlayer, Boolean>, PlayersListBasicAdapter.PlayerViewHolder>(
        PlayersComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, basicClickListener)
    }


    class PlayerViewHolder(private val binding: BasicListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Pair<SavedPlayer, Boolean>, basicClickListener: BasicClickListener) {
            binding.savedPlayer = player.first
            binding.textView.text = player.first.playerName
            if (player.second)
                binding.imageDart.visibility = View.VISIBLE
            else
                binding.imageDart.visibility = View.INVISIBLE

            binding.linearLayout.setOnClickListener {
                if (!player.second) {
                        basicClickListener.onItemClicked(layoutPosition)
                } else {
                    basicClickListener.onItemUnClicked(layoutPosition)
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): PlayerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BasicListItemBinding.inflate(layoutInflater, parent, false)
                return PlayerViewHolder(
                    binding
                )
            }
        }
    }
}

class PlayersComparator : DiffUtil.ItemCallback<Pair<SavedPlayer, Boolean>>() {
    override fun areItemsTheSame(oldItem: Pair<SavedPlayer, Boolean>, newItem: Pair<SavedPlayer, Boolean>): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Pair<SavedPlayer, Boolean>, newItem: Pair<SavedPlayer, Boolean>): Boolean {
        return oldItem.first.playerId == newItem.first.playerId
    }
}


interface BasicClickListener {
    fun onItemClicked(position: Int)
    fun onItemUnClicked(position: Int)

}
