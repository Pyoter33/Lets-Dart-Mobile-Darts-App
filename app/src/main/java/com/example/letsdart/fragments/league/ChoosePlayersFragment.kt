package com.example.letsdart.fragments.league

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.utils.Application
import com.example.letsdart.R
import com.example.letsdart.adapters.BasicClickListener
import com.example.letsdart.adapters.PlayersListBasicAdapter
import com.example.letsdart.databinding.ChoosePlayersFragmentBinding
import com.example.letsdart.models.general.PlayerListItem
import com.example.letsdart.viewmodels.league.ChoosePlayersViewModel
import com.example.letsdart.viewmodels.league.ChoosePlayersViewModelFactory


class ChoosePlayersFragment : Fragment(), BasicClickListener, SearchView.OnQueryTextListener {

    private lateinit var viewModel: ChoosePlayersViewModel
    private lateinit var binding: ChoosePlayersFragmentBinding
    private lateinit var adapter: PlayersListBasicAdapter
    private lateinit var args: ChoosePlayersFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.choose_players_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        adapter = PlayersListBasicAdapter(this)
        binding.playersList.adapter = adapter
        binding.playersList.layoutManager = LinearLayoutManager(context)
        args = ChoosePlayersFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = ChoosePlayersViewModelFactory(
            args.name,
            args.rules,
            (application as Application).leaguesRepository,
            application.playersRepository
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ChoosePlayersViewModel::class.java)

        observePlayersList()
        observeCreatedLeague()
        setOnCreateClickListener()
        setOnFilterPlayersListener()
    }

    private fun observeCreatedLeague(){
        viewModel.createLeagueResult.observe(viewLifecycleOwner, {
                if(it)
                    findNavController().navigate(
                        ChoosePlayersFragmentDirections.actionChoosePlayersFragmentToLeaguePagerFragment(
                            -1L
                        )
                    )
        })
    }

    private fun observePlayersList() {
        viewModel.playersList.observe(viewLifecycleOwner, { list ->
            adapter.submitList(viewModel.createPairs(list))
        })
    }

    private fun setOnFilterPlayersListener() {
        binding.searchViewFilter.setOnQueryTextListener(this)
    }

    private fun setOnCreateClickListener() {
        binding.buttonContinue.setOnClickListener {
            if(viewModel.chosenSavedPlayers.size < 3) {
                Toast.makeText(requireContext(), "Choose at least 3 players to start a league!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.chosenSavedPlayers.size > 16) {
                Toast.makeText(requireContext(), "You can choose maximum of 16 players for a league!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.createLeague()
        }
    }


    override fun onItemClicked(playerListItem: PlayerListItem, position: Int) {
        if (!viewModel.chosenSavedPlayers.contains(playerListItem.player)) {
            viewModel.chosenSavedPlayers.add(playerListItem.player)
            playerListItem.isChosen = true
            adapter.notifyItemChanged(position)
        }
    }

    override fun onItemUnClicked(playerListItem: PlayerListItem, position: Int) {
        viewModel.chosenSavedPlayers.remove(playerListItem.player)
        playerListItem.isChosen = false
        adapter.notifyItemChanged(position)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

}