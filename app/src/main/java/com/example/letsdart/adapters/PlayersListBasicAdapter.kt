package com.example.letsdart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdart.databinding.BasicListItemBinding
import com.example.letsdart.models.general.PlayerListItem
import java.util.*


class PlayersListBasicAdapter(private val basicClickListener: BasicClickListener) :
    RecyclerView.Adapter<PlayersListBasicAdapter.PlayerViewHolder>(
    ), Filterable {

    var currentList: List<PlayerListItem> = listOf()
    private var data: List<PlayerListItem> = listOf()

    fun submitList(list: List<PlayerListItem>) {
        data = list
        currentList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        if(currentList.isNotEmpty()) {
            val current = currentList[position]
            holder.bind(current, basicClickListener)
        }
    }

    override fun getItemCount(): Int = currentList.size


    class PlayerViewHolder(private val binding: BasicListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerListItem, basicClickListener: BasicClickListener) {
            binding.savedPlayer = player.player
            binding.textView.text = player.player.playerName
            if (player.isChosen)
                binding.imageDart.visibility = View.VISIBLE
            else
                binding.imageDart.visibility = View.INVISIBLE

            binding.linearLayout.setOnClickListener {
                if (!player.isChosen) {
                    basicClickListener.onItemClicked(player, layoutPosition)
                } else {
                    basicClickListener.onItemUnClicked(player, layoutPosition)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                var filteredList = mutableListOf<PlayerListItem>()
                if (charString.isEmpty()) filteredList = data as MutableList<PlayerListItem> else {
                    data
                        .filter {
                            (it.player.playerName!!.toLowerCase(Locale.ROOT).contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }

                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentList = if (results?.values == null) {
                    listOf()
                } else {
                    results.values as List<PlayerListItem>
                }
                notifyDataSetChanged()
            }
        }
    }


}


interface BasicClickListener {
    fun onItemClicked(playerListItem: PlayerListItem, position: Int)
    fun onItemUnClicked(playerListItem: PlayerListItem, position: Int)

}
