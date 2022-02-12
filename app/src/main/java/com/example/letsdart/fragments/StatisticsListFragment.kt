package com.example.letsdart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.R
import com.example.letsdart.adapters.StatisticsClickListener
import com.example.letsdart.adapters.StatisticsListAdapter
import com.example.letsdart.databinding.PlayerManagerFragmentBinding
import com.example.letsdart.databinding.StatisticsListFragmentBinding
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.utils.Application
import com.example.letsdart.viewModels.players.PlayerManagerViewModel
import com.example.letsdart.viewModels.players.PlayerManagerViewModelFactory
import com.example.letsdart.viewModels.players.StatisticsListViewModel
import com.example.letsdart.viewModels.players.StatisticsListViewModelFactory

class StatisticsListFragment :Fragment(), StatisticsClickListener {
    private lateinit var viewModel: StatisticsListViewModel
    private lateinit var binding: StatisticsListFragmentBinding
    private lateinit var statisticsAdapter: StatisticsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.statistics_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        statisticsAdapter = StatisticsListAdapter(this)
        binding.savedPlayerList.adapter = statisticsAdapter
        binding.savedPlayerList.layoutManager = LinearLayoutManager(context)
        val viewModelFactory = StatisticsListViewModelFactory((application as Application).playersRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StatisticsListViewModel::class.java)

        observePlayerList()
    }

    private fun observePlayerList() {
        viewModel.playersList.observe(viewLifecycleOwner, {
            it.let { statisticsAdapter.submitList(it) }
        })
    }

    override fun onItemClicked(player: SavedPlayer) {
        findNavController().navigate(StatisticsListFragmentDirections.actionStatisticsListFragmentToStatisticsFragment(player))
    }

}