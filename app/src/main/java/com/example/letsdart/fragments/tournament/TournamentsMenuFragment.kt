package com.example.letsdart.fragments.tournament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.utils.Application
import com.example.letsdart.dialogs.DeleteInterface
import com.example.letsdart.dialogs.DeleteItemDialog
import com.example.letsdart.R
import com.example.letsdart.databinding.TournamentsMenuFragmentBinding
import com.example.letsdart.models.tournament.Tournament
import com.example.letsdart.tournamentCreator.tournamentsMenu.TournamentClickListener
import com.example.letsdart.tournamentCreator.tournamentsMenu.TournamentListAdapter
import com.example.letsdart.viewModels.tournament.TournamentsMenuViewModel
import com.example.letsdart.viewModels.tournament.TournamentsMenuViewModelFactory

class TournamentsMenuFragment : Fragment(), TournamentClickListener, DeleteInterface {

    companion object{
        const val IDENTIFIER = "tournament"
    }

    private lateinit var binding: TournamentsMenuFragmentBinding
    private lateinit var menuViewModel: TournamentsMenuViewModel
    private lateinit var tournamentAdapter: TournamentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tournaments_menu_fragment, container, false)
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
        val tournamentsMenuViewModelFactory = TournamentsMenuViewModelFactory((application as Application).tournamentsRepository)
        menuViewModel = ViewModelProvider(this, tournamentsMenuViewModelFactory).get(TournamentsMenuViewModel::class.java)
        tournamentAdapter = TournamentListAdapter(this)
        binding.tournamentsList.layoutManager = LinearLayoutManager(context)

        observeTournamentList()
        setOnCreateClickListener()
    }

    private fun observeTournamentList() {
        binding.tournamentsList.adapter = tournamentAdapter
        menuViewModel.tournamentList.observe(viewLifecycleOwner, {
            it.let { tournamentAdapter.submitList(it) }
        })
    }


    private fun setOnCreateClickListener(){
        binding.buttonCreateTournament.setOnClickListener {
            findNavController().navigate(TournamentsMenuFragmentDirections.actionTournamentsMenuFragmentToTournamentRulesFragment())
        }
    }

    override fun onDeleteTournamentClicked(tournament: Tournament) {
        val deleteItemDialog = DeleteItemDialog(this, IDENTIFIER, tournament)
        deleteItemDialog.show(childFragmentManager, "TournamentMenuFragment")
    }

    override fun onTournamentClicked(tournamentId: Long) {
     findNavController().navigate(
         TournamentsMenuFragmentDirections.actionTournamentsMenuFragmentToTournamentPagerFragment(
             tournamentId
         )
     )
    }

    override fun <T> delete(item: T) {
        menuViewModel.deleteTournament(item as Tournament)
    }

}