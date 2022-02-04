package com.example.letsdart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.*
import com.example.letsdart.adapters.BasicClickListener
import com.example.letsdart.adapters.PlayersListBasicAdapter
import com.example.letsdart.databinding.PrepareGameFragmentBinding
import com.example.letsdart.models.general.QuickMatchup
import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.viewModels.game.PrepareGameViewModel
import com.example.letsdart.viewModels.game.PrepareGameViewModelFactory
import com.example.letsdart.utils.Application


class PrepareGameFragment : Fragment(), BasicClickListener {

    private lateinit var viewModel: PrepareGameViewModel
    private lateinit var binding: PrepareGameFragmentBinding
    private lateinit var basicAdapter: PlayersListBasicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.prepare_game_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        basicAdapter = PlayersListBasicAdapter(this)
        binding.playerList.adapter = basicAdapter
        binding.playerList.layoutManager = LinearLayoutManager(context)
        val viewModelFactory = PrepareGameViewModelFactory((application as Application).playersRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PrepareGameViewModel::class.java)
        binding.prepareGameViewModel = viewModel
        viewModel.chosenPlayersList.clear()

        setSpinnerStartPoints()
        setSpinnerSets()
        setSpinnerLegs()
        setSpinnerWin()
        setSpinnerCheckout()
        setSpinnerDecider()
        setOnStartClickListener()
        observePlayerList()
    }

    private fun observePlayerList() {
        viewModel.playersList.observe(viewLifecycleOwner, { list ->
            for (elem in list)
                viewModel.pairsList.add(Pair(elem, false))
            basicAdapter.submitList(viewModel.pairsList)
        })
    }


    private fun setSpinnerStartPoints() {
        binding.spinnerStartPoints.setItems(viewModel.possibleStartPoints)
        binding.spinnerStartPoints.setOnItemSelectedListener { _, _, _, item ->
            viewModel.startScore = item as Int
        }

    }

    private fun setSpinnerSets() {
        binding.spinnerSets.setItems(viewModel.possibleSetsOrLegs)
        binding.spinnerSets.setOnItemSelectedListener { _, _, _, item ->
            viewModel.numberOfSets = item.toString().toInt()
        }

    }

    private fun setSpinnerLegs() {
        binding.spinnerLegs.setItems(viewModel.possibleSetsOrLegs)
        binding.spinnerLegs.setOnItemSelectedListener { _, _, _, item ->
            viewModel.numberOfLegs = item.toString().toInt()
        }

    }


    private fun setSpinnerCheckout() {
        binding.spinnerCheckout.setItems(viewModel.possibleCheckouts)
        binding.spinnerCheckout.setOnItemSelectedListener { _, position, _, _ ->
            viewModel.checkout = position
        }

    }

    private fun setSpinnerWin() {
        binding.spinnerWin.setItems(viewModel.possibleWinConditions)
        binding.spinnerWin.setOnItemSelectedListener { _, position, _, _ ->
            viewModel.winCondition = position
        }

    }

    private fun setSpinnerDecider() {
        binding.spinnerDecider.setItems(viewModel.possibleDeciderTypes)
        binding.spinnerDecider.setOnItemSelectedListener { _, position, _, _ ->
            viewModel.decider = position
        }

    }

    private fun setOnStartClickListener() {
        binding.startButton.setOnClickListener {
            if (viewModel.checkNames()) {
                findNavController().navigate(PrepareGameFragmentDirections.actionPrepareGameFragmentToGameFragment(
                        Rules(
                            viewModel.startScore,
                            viewModel.numberOfLegs,
                            viewModel.numberOfSets,
                            viewModel.checkout,
                            viewModel.winCondition,
                            viewModel.decider,
                            false
                        ),
                        QuickMatchup(
                            SeriesPlayer(savedPlayer = viewModel.chosenPlayersList[0]),
                            SeriesPlayer(savedPlayer = viewModel.chosenPlayersList[1])
                        )
                    )
                )
                viewModel.chosenPlayersList.clear()
            } else
                Toast.makeText(context, "Choose two players!", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onItemClicked(position: Int) {
        val currentElem = viewModel.pairsList[position]
        if (viewModel.chosenPlayersList.size < 2) {
            if (viewModel.chosenPlayersList.size == 1 && viewModel.chosenPlayersList[0].playerId == currentElem.first.playerId)
                return

            viewModel.pairsList[position] = Pair(currentElem.first, true)
            basicAdapter.notifyItemChanged(position)
            viewModel.addPlayerToList(currentElem.first)
        }
    }

    override fun onItemUnClicked(position: Int) {
        val currentElem = viewModel.pairsList[position]
        viewModel.pairsList[position] = Pair(currentElem.first, false)
        basicAdapter.notifyItemChanged(position)
        viewModel.removePlayerFromList(currentElem.first)
    }

}