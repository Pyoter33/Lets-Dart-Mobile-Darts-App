package com.example.letsdart.fragments.league

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.R
import com.example.letsdart.adapters.LeagueGameClickListener
import com.example.letsdart.adapters.LeagueGamesListAdapter
import com.example.letsdart.databinding.LeagueGamesFragmentBinding
import com.example.letsdart.viewModels.league.LeaguePagerViewModel
import com.example.letsdart.models.league.LeagueMatchup

class LeagueGamesFragment(private val viewModel: LeaguePagerViewModel) : Fragment(), LeagueGameClickListener {

    private lateinit var binding: LeagueGamesFragmentBinding
    private lateinit var adapter: LeagueGamesListAdapter
    private lateinit var application: android.app.Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.league_games_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        application = requireNotNull(this.activity).application
        adapter = LeagueGamesListAdapter(this)
        binding.gamesList.adapter = adapter
        binding.gamesList.layoutManager = LinearLayoutManager(context)
        observeLeague()
        setOnToggleButtonClickListener()
    }

    private fun setOnToggleButtonClickListener(){
        binding.chipTogglePlayed.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked)
                adapter.submitList(viewModel.getUnfinishedMatchups(viewModel.league.value!!.matchupsList))
            else
                adapter.submitList(viewModel.league.value!!.matchupsList)

        }
    }

    private fun observeLeague() {
        viewModel.league.observe(viewLifecycleOwner, {
            adapter.submitList(it!!.matchupsList)
        })
    }

    override fun onPlayClicked(leagueMatchup: LeagueMatchup) {
        findNavController().navigate(
            LeaguePagerFragmentDirections.actionLeaguePagerFragmentToGameFragment(
                viewModel.league.value!!.rules,
                leagueMatchup
            )
        )
    }


}