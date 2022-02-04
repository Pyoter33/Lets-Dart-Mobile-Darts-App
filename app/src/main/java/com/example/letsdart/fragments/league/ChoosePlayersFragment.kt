package com.example.letsdart.fragments.league

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.utils.Application
import com.example.letsdart.R
import com.example.letsdart.adapters.BasicClickListener
import com.example.letsdart.adapters.PlayersListBasicAdapter
import com.example.letsdart.databinding.ChoosePlayersFragmentBinding
import com.example.letsdart.viewModels.league.ChoosePlayersViewModel
import com.example.letsdart.viewModels.league.ChoosePlayersViewModelFactory


class ChoosePlayersFragment : Fragment(), BasicClickListener {

    private lateinit var viewModel: ChoosePlayersViewModel
    private lateinit var binding: ChoosePlayersFragmentBinding
    private lateinit var basicAdapter: PlayersListBasicAdapter
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
        basicAdapter = PlayersListBasicAdapter(this)
        binding.playersList.adapter = basicAdapter
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
            basicAdapter.submitList(viewModel.createPairs(list))
        })
    }

    private fun setOnCreateClickListener() {
        binding.buttonCreate.setOnClickListener {
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


    override fun onItemClicked(position: Int) {
        val currentElem = viewModel.pairsList[position]
        if (!viewModel.chosenSavedPlayers.contains(currentElem.first)) {
            viewModel.chosenSavedPlayers.add(currentElem.first)
            viewModel.pairsList[position] = Pair(currentElem.first, !currentElem.second)
            basicAdapter.notifyItemChanged(position)
        }
    }

    override fun onItemUnClicked(position: Int) {
        val currentElem = viewModel.pairsList[position]
        viewModel.chosenSavedPlayers.remove(currentElem.first)
        viewModel.pairsList[position] = Pair(currentElem.first, !currentElem.second)
        basicAdapter.notifyItemChanged(position)
    }

}