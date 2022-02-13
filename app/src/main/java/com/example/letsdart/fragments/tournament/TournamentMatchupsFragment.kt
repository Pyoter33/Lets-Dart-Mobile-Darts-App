package com.example.letsdart.fragments.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.R
import com.example.letsdart.adapters.MatchupListAdapter
import com.example.letsdart.adapters.TournamentMatchupClickListener
import com.example.letsdart.databinding.TournamentMatchupsFragmentBinding
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup


class TournamentMatchupsFragment(
    private val matchupsFragmentInterface: MatchupsFragmentInterface,
    private val matchupsList: List<TournamentMatchup>
) : Fragment(), TournamentMatchupClickListener {

    private lateinit var binding: TournamentMatchupsFragmentBinding

    private lateinit var adapter: MatchupListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.tournament_matchups_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MatchupListAdapter(this)
        binding.matchupsList.adapter = adapter
        binding.matchupsList.layoutManager = LinearLayoutManager(this.context)
        adapter.submitList(matchupsList)
    }

    override fun onPlayClicked(tournamentMatchup: TournamentMatchup) {
            matchupsFragmentInterface.onPlayClicked(tournamentMatchup)
    }

}