package com.example.letsdart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.letsdart.R
import com.example.letsdart.databinding.MenuFragmentBinding
import com.example.letsdart.dialogs.ExitAppDialog

class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.menu_fragment, container, false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val exitGameDialog = ExitAppDialog()
                exitGameDialog.show(childFragmentManager,"ExitGameDialog")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnPlayNowClickListener()
        setOnPlayersClickListener()
        setOnLeaguesClickListener()
        setOnTournamentsClickListener()
        setOnStatisticsClickListener()
    }

    private fun setOnPlayNowClickListener(){
        binding.buttonPlayNow.setOnClickListener {
            it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToPrepareGameFragment())
        }
    }

    private fun setOnPlayersClickListener(){
        binding.buttonPlayers.setOnClickListener {
            it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToPlayerManagerFragment())

        }
    }


    private fun setOnLeaguesClickListener(){
        binding.buttonLeagues.setOnClickListener {
            it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToLeagueMenuFragment())
        }
    }

    private fun setOnTournamentsClickListener(){
        binding.buttonTournaments.setOnClickListener {
            it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTournamentsMenuFragment())
        }
    }

    private fun setOnStatisticsClickListener(){
        binding.buttonStatistics.setOnClickListener {
            it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToStatisticsListFragment())
        }
    }
}