package com.example.letsdart.fragments.league

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.letsdart.R
import com.example.letsdart.models.general.Rules
import com.example.letsdart.databinding.RulesFragmentBinding
import com.example.letsdart.viewModels.league.RulesViewModel

class RulesFragment : Fragment() {
    private lateinit var viewModel: RulesViewModel

    private lateinit var binding: RulesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.rules_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RulesViewModel::class.java)

        setSpinnerStartPoints()
        setSpinnerCheckout()
        setSpinnerLegs()
        setSpinnerSets()
        setSpinnerWin()
        setSpinnerDecider()
        setOnContinueClickListener()
        setCheckBox()

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

    private fun setCheckBox(){
        binding.checkBoxRematch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.rematch = isChecked
        }
    }

    private fun setOnContinueClickListener(){
        binding.startButton.setOnClickListener {
            val name = binding.editTextTournamentName.text
            if(!TextUtils.isEmpty(name))
                findNavController().navigate(
                    RulesFragmentDirections.actionRulesFragmentToChoosePlayersFragment(
                        name.toString(),
                        Rules(
                            viewModel.startScore,
                            viewModel.numberOfLegs,
                            viewModel.numberOfSets,
                            viewModel.checkout,
                            viewModel.winCondition,
                            viewModel.decider,
                            viewModel.rematch
                        )
                    )
                )
        }
    }
}