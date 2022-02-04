package com.example.letsdart.fragments.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.letsdart.R
import com.example.letsdart.adapters.LeaguePagerAdapter
import com.example.letsdart.databinding.LeaguePagerFragmentBinding
import com.example.letsdart.utils.Application
import com.example.letsdart.viewModels.league.LeaguePagerViewModel
import com.example.letsdart.viewModels.league.LeaguePagerViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LeaguePagerFragment : Fragment() {

    private lateinit var application: android.app.Application
    private lateinit var viewModel: LeaguePagerViewModel
    private lateinit var args: LeaguePagerFragmentArgs
    private lateinit var binding: LeaguePagerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.league_pager_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.viewPager.currentItem == 1)
                    binding.viewPager.setCurrentItem(0, true)
                else
                    findNavController().navigate(R.id.leagueMenuFragment)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        application = requireNotNull(this.activity).application
        val viewModelFactory = LeaguePagerViewModelFactory((application as Application).leaguesRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LeaguePagerViewModel::class.java)
        val fragmentList = listOf(LeagueStandingsFragment(viewModel), LeagueGamesFragment(viewModel))
        val adapter = LeaguePagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        args = LeaguePagerFragmentArgs.fromBundle(requireArguments())

        observeLeague()
        onEndLeagueClickListener()

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, view.findViewById(R.id.viewPager)) { tab, position ->
            when (position) {
                0 -> tab.text = application.resources.getText(R.string.text_standings)
                1 -> tab.text = application.resources.getText(R.string.text_games)
            }
        }.attach()
    }

    private fun observeLeague() {
        viewModel.league.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.textLeagueName.text = it.name
                binding.textStartScoreValue.text = it.rules.startScore.toString()
                binding.textSetsValue.text = it.rules.numberOfSets.toString()
                binding.textLegsValue.text = it.rules.numberOfLegs.toString()

                if (it.rules.checkout == 0)
                    binding.textCheckoutValue.text = application.getText(R.string.text_singleO)
                else
                    binding.textCheckoutValue.text = application.getText(R.string.text_doubleO)

                if (it.rules.winCondition == 0)
                    binding.textWinConditionValue.text = application.getText(R.string.text_best_of)
                else
                    binding.textWinConditionValue.text = application.getText(R.string.text_first_to)

                if (it.matchupsList.isEmpty())
                    viewModel.initializeSavedLeague(it.id)

                if (viewModel.getUnfinishedMatchups(it.matchupsList).isEmpty())
                    binding.layoutEndLeague.visibility = View.VISIBLE
                else
                    binding.layoutEndLeague.visibility = View.GONE

                return@observe
            }
            if (args.leagueId == -1L)
                viewModel.initializeNewestLeague()
            else
                viewModel.initializeSavedLeague(args.leagueId)
        })
    }

    private fun onEndLeagueClickListener() {
        binding.buttonEndLeague.setOnClickListener {
            findNavController().popBackStack(R.id.menuFragment, false)
            viewModel.deleteFinishedLeague()
        }
    }
}