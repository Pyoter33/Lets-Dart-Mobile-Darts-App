package com.example.letsdart.fragments.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.utils.Application
import com.example.letsdart.dialogs.DeleteInterface
import com.example.letsdart.dialogs.DeleteItemDialog
import com.example.letsdart.R
import com.example.letsdart.adapters.LeagueClickListener
import com.example.letsdart.adapters.LeagueListAdapter
import com.example.letsdart.databinding.CreateLeagueFragmentBinding
import com.example.letsdart.viewModels.league.LeaguesMenuViewModel
import com.example.letsdart.viewModels.league.LeaguesMenuViewModelFactory
import com.example.letsdart.models.league.League

class LeaguesMenuFragment : Fragment(), LeagueClickListener, DeleteInterface {

    companion object{
        const val IDENTIFIER = "league"
    }

    private lateinit var binding: CreateLeagueFragmentBinding
    private lateinit var menuViewModel: LeaguesMenuViewModel
    private lateinit var leagueAdapter: LeagueListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_league_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.menuFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        val leaguesMenuViewModelFactory = LeaguesMenuViewModelFactory((application as Application).leaguesRepository)
        menuViewModel = ViewModelProvider(this, leaguesMenuViewModelFactory).get(LeaguesMenuViewModel::class.java)
        leagueAdapter = LeagueListAdapter(this)
        binding.leaguesList.layoutManager = LinearLayoutManager(context)

        observeLeagueList()
        setOnCreateClickListener()
    }

    private fun observeLeagueList() {
        binding.leaguesList.adapter = leagueAdapter
        menuViewModel.leagueList.observe(viewLifecycleOwner, {
            it.let { leagueAdapter.submitList(it) }
        })
    }

    private fun setOnCreateClickListener() {
        binding.buttonCreateLeague.setOnClickListener {
            findNavController().navigate(LeaguesMenuFragmentDirections.actionLeagueMenuFragmentToRulesFragment())
        }
    }

    override fun onDeleteLeagueClicked(league: League) {
        val deleteItemDialog = DeleteItemDialog(this, IDENTIFIER, league)
        deleteItemDialog.show(childFragmentManager, "LeagueMenuFragment")
    }

    override fun onLeagueClicked(leagueId: Long) {
        findNavController().navigate(
            LeaguesMenuFragmentDirections.actionLeagueMenuFragmentToLeaguePagerFragment(
                leagueId
            )
        )
    }

    override fun <T> delete(item: T) {
        menuViewModel.deleteLeague(item as League)
    }

}