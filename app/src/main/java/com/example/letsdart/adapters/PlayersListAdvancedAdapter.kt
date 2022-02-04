package com.example.letsdart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.databinding.AdvancedListItemBinding
import com.example.letsdart.utils.SavedPlayersComparator


class PlayersListAdvancedAdapter(private val playerListInterface: PlayerListInterface) :
    ListAdapter<SavedPlayer, PlayersListAdvancedAdapter.PlayerViewHolder>(
        SavedPlayersComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, playerListInterface)
    }


    class PlayerViewHolder(private val binding: AdvancedListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: SavedPlayer, playerListInterface: PlayerListInterface) {
            binding.savedPlayer = player
            binding.textView.text = player.playerName

            binding.buttonDelete.setOnClickListener {
                playerListInterface.onDeleteClicked(player)
            }

            binding.buttonRename.setOnClickListener {
                playerListInterface.onRenameClicked(player)
            }

        }

        companion object {
            fun create(parent: ViewGroup): PlayerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdvancedListItemBinding.inflate(layoutInflater, parent, false)
                return PlayerViewHolder(
                    binding
                )
            }
        }
    }
}

interface PlayerListInterface {
    fun onRenameClicked(player: SavedPlayer)
    fun onDeleteClicked(player: SavedPlayer)
}
