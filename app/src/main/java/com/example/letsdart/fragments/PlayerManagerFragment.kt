package com.example.letsdart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdart.*
import com.example.letsdart.adapters.PlayerListInterface
import com.example.letsdart.adapters.PlayersListAdvancedAdapter
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.databinding.PlayerManagerFragmentBinding
import com.example.letsdart.dialogs.*
import com.example.letsdart.viewModels.players.PlayerManagerViewModel
import com.example.letsdart.viewModels.players.PlayerManagerViewModelFactory
import com.example.letsdart.utils.Application

class PlayerManagerFragment : Fragment(), PlayerInterface, DeleteInterface, PlayerListInterface {

    companion object{
        const val IDENTIFIER = "player"
    }

    private lateinit var viewModel: PlayerManagerViewModel
    private lateinit var binding: PlayerManagerFragmentBinding
    private lateinit var advancedAdapter: PlayersListAdvancedAdapter
    private lateinit var createPlayerDialog: CreatePlayerDialog
    private lateinit var renameDialog: RenamePlayerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.player_manager_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        advancedAdapter = PlayersListAdvancedAdapter(this)
        binding.savedPlayerList.adapter = advancedAdapter
        binding.savedPlayerList.layoutManager = LinearLayoutManager(context)
        val viewModelFactory = PlayerManagerViewModelFactory((application as Application).playersRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerManagerViewModel::class.java)
        createPlayerDialog = CreatePlayerDialog(this)

        observePlayerList()
        setOnCreateNewPlayerClickListener()
    }

    private fun observePlayerList() {
        viewModel.playersList.observe(viewLifecycleOwner, {
            it.let { advancedAdapter.submitList(it) }
        })
    }

    private fun setOnCreateNewPlayerClickListener() {
        binding.buttonCreate.setOnClickListener {
            createPlayerDialog.show(childFragmentManager, "CreatePlayerDialog")
        }
    }


    override fun onRenameClicked(player: SavedPlayer) {
        renameDialog = RenamePlayerDialog(this, player)
        renameDialog.show(childFragmentManager, "PlayerManagerFragment")
    }

    override fun onDeleteClicked(player: SavedPlayer) {
        val deleteItemDialog = DeleteItemDialog(this, IDENTIFIER, player)
        deleteItemDialog.show(childFragmentManager, "PlayerManagerFragment")
    }

    override fun onDialogCreate(player: SavedPlayer) {
        viewModel.insertPlayer(player)
    }

    override fun onDialogUpdate(player: SavedPlayer) {
        viewModel.updatePlayer(player)
    }

    override fun <T> delete(item: T) {
        viewModel.deletePlayer(item as SavedPlayer)
    }

}