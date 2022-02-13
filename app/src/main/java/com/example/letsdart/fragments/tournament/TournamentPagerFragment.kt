package com.example.letsdart.fragments.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.letsdart.utils.Application
import com.example.letsdart.R
import com.example.letsdart.adapters.TournamentPagerAdapter
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup
import com.example.letsdart.databinding.TournamentPagerFragmentBinding
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.viewmodels.tournament.TournamentPagerViewModel
import com.example.letsdart.viewmodels.tournament.TournamentPagerViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TournamentPagerFragment : Fragment(), MatchupsFragmentInterface {

    private lateinit var viewModel: TournamentPagerViewModel
    private lateinit var binding: TournamentPagerFragmentBinding
    private lateinit var application: android.app.Application
    private lateinit var args: TournamentPagerFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tournament_pager_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.tournamentsMenuFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        application = requireNotNull(this.activity).application
        val viewModelFactory = TournamentPagerViewModelFactory((application as Application).tournamentsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TournamentPagerViewModel::class.java)
        args = TournamentPagerFragmentArgs.fromBundle(requireArguments())
        observeTournament()
        observeIfFinished()
        setOnEndTournamentClickListener()
    }

    private fun attachTabs(tournament: Tournament) {
        val tabLayout = view?.findViewById<TabLayout>(R.id.tabLayout)

        view?.let {
            if (tabLayout != null) {
                tabLayout.tabMode = TabLayout.MODE_AUTO
                TabLayoutMediator(tabLayout, it.findViewById(R.id.viewPager)) { tab, position ->
                        tab.text = getCurrentRound(position, tournament)
                }.attach()
            }
        }
    }

    private fun getCurrentRound(position: Int, tournament: Tournament): String {
        return when(tournament.matchups[position]!!.size){
            1 -> getString(R.string.text_final)
            2 ->  getString(R.string.text_semifinal)
            4 ->  getString(R.string.text_quarterfinal)
            else -> getString(R.string.text_round, position + 1)
        }

    }

    private fun observeTournament() {
        viewModel.tournament.observe(viewLifecycleOwner, {
            if(it != null) {
                binding.textTournamentName.text = it.name
                binding.textStartScoreValue.text = it.rules.startScore.toString()
                binding.textSetsValue.text = it.rules.numberOfSets.toString()
                binding.textLegsValue.text = it.rules.numberOfLegs.toString()

                when (it.rules.checkout) {
                    0 -> binding.textCheckoutValue.text = application.getText(R.string.text_singleO)
                    1 -> binding.textCheckoutValue.text = application.getText(R.string.text_doubleO)
                }

                when (it.rules.winCondition) {
                    0 -> binding.textWinConditionValue.text = application.getText(R.string.text_best_of)
                    1 -> binding.textWinConditionValue.text = application.getText(R.string.text_first_to)
                }

                val fragmentList = mutableListOf<Fragment>()
                for (elem in viewModel.tournament.value?.matchups!!)
                    fragmentList.add(TournamentMatchupsFragment(this, elem.value))
                val adapter = TournamentPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

                binding.viewPager.adapter = adapter
                attachTabs(it)

                if(it.matchups[it.maxLevel]!!.isEmpty())
                    viewModel.initializeSavedTournament(it.id)
                return@observe
            }
                if (args.tournamentID == -1L)
                    viewModel.initializeNewestTournament()
                else
                    viewModel.initializeSavedTournament(args.tournamentID)


        })
    }

    private fun observeIfFinished() {
        viewModel.finished.observe(viewLifecycleOwner, {
            if (it)
                binding.layoutEndTournament.visibility = View.VISIBLE
        })
    }

    private fun setOnEndTournamentClickListener() {
        binding.buttonEndTournament.setOnClickListener {
            findNavController().popBackStack(R.id.menuFragment, false)
            viewModel.deleteFinishedTournament()
        }
    }

    override fun onPlayClicked(tournamentMatchup: TournamentMatchup) {
        findNavController().navigate(
            TournamentPagerFragmentDirections.actionTournamentPagerFragmentToGameFragment(
                viewModel.tournament.value!!.rules,
                tournamentMatchup
            )
        )
    }

}

interface MatchupsFragmentInterface{
    fun onPlayClicked(tournamentMatchup: TournamentMatchup)
}