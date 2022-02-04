package com.example.letsdart.fragments.league

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.R
import com.example.letsdart.adapters.LeaguePlayersListAdapter
import com.example.letsdart.databinding.LeagueStandingsFragmentBinding
import com.example.letsdart.utils.LeaguePlayersComparator
import com.example.letsdart.viewModels.league.LeaguePagerViewModel


class LeagueStandingsFragment(private val viewModel: LeaguePagerViewModel) : Fragment() {

    private lateinit var binding: LeagueStandingsFragmentBinding
    private lateinit var adapter: LeaguePlayersListAdapter
    private lateinit var application: android.app.Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.league_standings_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        application = requireNotNull(this.activity).application
        adapter = LeaguePlayersListAdapter()
        binding.leaguePlayersList.adapter = adapter
        binding.leaguePlayersList.layoutManager = LinearLayoutManager(this.context)

        observeLeague()
    }

    private fun observeLeague() {
        viewModel.league.observe(viewLifecycleOwner, {
            if(it != null) {
                val list = it.playersList.sortedWith(LeaguePlayersComparator)
                adapter.submitList(list)
            }
        })
    }

}