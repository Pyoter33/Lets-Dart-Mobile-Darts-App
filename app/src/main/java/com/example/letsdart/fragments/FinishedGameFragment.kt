package com.example.letsdart.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.letsdart.utils.Application
import com.example.letsdart.models.general.QuickMatchup
import com.example.letsdart.R
import com.example.letsdart.databinding.FinishedGameFragmentBinding
import com.example.letsdart.viewModels.game.FinishedGameViewModel
import com.example.letsdart.viewModels.game.FinishedGameViewModelFactory
import com.example.letsdart.models.league.LeagueMatchup
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup

class FinishedGameFragment : Fragment() {

    private lateinit var binding: FinishedGameFragmentBinding
    private lateinit var viewModel: FinishedGameViewModel
    private lateinit var args: FinishedGameFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.finished_game_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        args = FinishedGameFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = FinishedGameViewModelFactory(
            args.firstPlayerResults,
            args.secondPlayerResults,
            args.matchup,
            (application as Application).leaguesRepository,
            application.tournamentsRepository,
            application.playersRepository
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FinishedGameViewModel::class.java)

        if (args.firstPlayerResults.wonGame)
            binding.textWon.text = getString(R.string.text_won, args.matchup.firstPlayer.savedPlayer.playerName)
        else
            binding.textWon.text = getString(R.string.text_won, args.matchup.secondPlayer.savedPlayer.playerName)
        setOnFirstPlayerStatsClickListener()
        setOnSecondPlayerStatsClickListener()
        setOnContinueClickListener()
        observeGameEvaluation()
        observeStatsUpdate()
    }

    private fun setOnFirstPlayerStatsClickListener() {
        binding.textFirstPlayerStats.text = getString(R.string.text_stats, args.matchup.firstPlayer.savedPlayer.playerName)
        binding.buttonFirstPlayerStats.setOnClickListener {
            findNavController().navigate(
                FinishedGameFragmentDirections.actionFinishedLeagueGameFragmentToStatisticsFragment(
                    args.matchup.firstPlayer.savedPlayer,
                    args.firstPlayerResults.statistics
                )
            )
        }
    }

    private fun setOnSecondPlayerStatsClickListener() {
        binding.textSecondPlayerStats.text = getString(R.string.text_stats, args.matchup.secondPlayer.savedPlayer.playerName)
        binding.buttonSecondPlayerStats.setOnClickListener {
            findNavController().navigate(
                FinishedGameFragmentDirections.actionFinishedLeagueGameFragmentToStatisticsFragment(
                    args.matchup.secondPlayer.savedPlayer,
                    args.secondPlayerResults.statistics
                )
            )
        }
    }

    private fun observeStatsUpdate(){
        viewModel.areStatsUpdated.observe(viewLifecycleOwner, {
            if (it)
                viewModel.evaluateGame()
        })

    }

    private fun observeGameEvaluation() {
        viewModel.isGameEvaluated.observe(viewLifecycleOwner,{
            if (it) {
                when(val matchup = args.matchup) {
                    is TournamentMatchup -> {
                        findNavController().navigate(
                            FinishedGameFragmentDirections.actionFinishedLeagueGameFragmentToTournamentPagerFragment(
                                matchup.seriesId
                            )
                        )
                    }
                    is LeagueMatchup -> {
                        findNavController().navigate(
                           FinishedGameFragmentDirections.actionFinishedLeagueGameFragmentToLeaguePagerFragment(
                                matchup.seriesId
                            )
                        )
                    }
                    is QuickMatchup ->{
                        findNavController().navigate(FinishedGameFragmentDirections.actionFinishedLeagueGameFragmentToMenuFragment())
                    }
                }
            }
        })
    }

    private fun setOnContinueClickListener() {
        binding.buttonContinue.setOnClickListener {
            viewModel.updateStats()
        }
    }

}