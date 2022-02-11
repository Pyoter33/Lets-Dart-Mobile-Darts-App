package com.example.letsdart.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.letsdart.R
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup
import com.example.letsdart.databinding.GameFragmentBinding
import com.example.letsdart.dialogs.AbortGameDialog
import com.example.letsdart.dialogs.AbortGameDialogInterface
import com.example.letsdart.dialogs.EndGameDialog
import com.example.letsdart.dialogs.EndGameDialogInterface
import com.example.letsdart.models.general.Player
import com.example.letsdart.models.general.QuickMatchup
import com.example.letsdart.models.league.LeagueMatchup
import com.example.letsdart.viewModels.game.GameViewModel
import com.example.letsdart.viewModels.game.GameViewModelFactory

class GameFragment : Fragment(), AbortGameDialogInterface, EndGameDialogInterface {
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding
    private lateinit var args: GameFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = GameFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = GameViewModelFactory(args.rules)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.playerName1.text = args.matchup.firstPlayer.savedPlayer.playerName
        binding.playerName2.text = args.matchup.secondPlayer.savedPlayer.playerName

        viewModel.getCheckouts(resources, R.raw.checkouts, args.rules.checkout)
        observeRoundScore(viewModel.player1, binding.roundScore1)
        observeRoundScore(viewModel.player2, binding.roundScore2)
        observeAverage(viewModel.player1, binding.textValueAverage1)
        observeAverage(viewModel.player2, binding.textValueAverage2)

        observeDouble()
        observeTriple()

        observeTurn()
        observeDecider()

        observeHint()
        observeGameEnded()
        setOnLightbulbClickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val abortGameDialog = AbortGameDialog(this@GameFragment)
                abortGameDialog.show(childFragmentManager, "AbortGameDialog")

            }
        })

    }

    private fun observeGameEnded() {
        viewModel.isGameEnded.observe(viewLifecycleOwner, {
            if (it)
                showEndGameDialog()
        })

    }

    private fun setOnLightbulbClickListener() {
        binding.imageButtonLightbulb.setOnClickListener {
            if (binding.layoutHint.visibility == View.INVISIBLE) {
                binding.layoutHint.visibility = View.VISIBLE
                return@setOnClickListener
            }
            binding.layoutHint.visibility = View.INVISIBLE
        }
    }

    private fun observeHint() {
        viewModel.hintData.observe(viewLifecycleOwner, { list ->
            binding.layoutHint.visibility = View.INVISIBLE
            binding.layoutHint.postDelayed({
                if (list == null) {
                    binding.imageButtonLightbulb.setImageResource(R.drawable.lightbulb_off)
                    binding.textHint.text = getString(R.string.text_no_checkouts)
                    binding.layoutHint.setBackgroundResource(R.drawable.hint_background_no_checkouts)
                } else {
                    var checkout = ""
                    for (elem in list)
                        checkout += "$elem "

                    binding.imageButtonLightbulb.setImageResource(R.drawable.lightbulb_on)
                    binding.textHint.text = getString(R.string.text_checkout, checkout)
                    binding.layoutHint.setBackgroundResource(R.drawable.hint_background_checkouts)
                }
            }, 300)
        })

    }

    private fun observeRoundScore(player: Player, textView: TextView) {
        player.roundScore.observe(viewLifecycleOwner, {
            if (it == -1)
                textView.text = ""
            else
                textView.text = "$it"
        })

    }

    private fun observeAverage(player: Player, textView: TextView) {
        player.average.observe(viewLifecycleOwner, {
            if (it == -1f)
                textView.text = " "
            else
                textView.text = it.toString()
        })

    }

    private fun observeDouble() {
        viewModel.isDoubleEnabled.observe(viewLifecycleOwner, {
            if (it) {
                binding.buttonDouble.backgroundColor = Color.rgb(168, 113, 0)
                binding.buttonNo0.isEnabled = false
            } else {
                binding.buttonDouble.backgroundColor = Color.rgb(218, 167, 0)
                binding.buttonNo0.isEnabled = true
            }
        })

    }


    private fun observeTriple() {
        viewModel.isTripleEnabled.observe(viewLifecycleOwner, {
            if (it) {
                binding.buttonTriple.backgroundColor = Color.rgb(159, 38, 0)
                binding.buttonNo25.isEnabled = false
                binding.buttonNo0.isEnabled = false
            } else {
                binding.buttonTriple.backgroundColor = Color.rgb(243, 66, 11)
                binding.buttonNo25.isEnabled = true
                binding.buttonNo0.isEnabled = true
            }
        })

    }

    private fun observeTurn() {
        viewModel.playerTurn.observe(viewLifecycleOwner, {
            if (it == 1) {
                binding.player1Layout.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.green_back)
                binding.player2Layout.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.grey_back)
            } else {
                binding.player2Layout.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.green_back)
                binding.player1Layout.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.grey_back)
            }
        })
    }

    private fun observeDecider() {
        viewModel.deciderRandom.observe(viewLifecycleOwner, {
            if (it != 0)
                Toast.makeText(context, "Starter of deciding leg chosen!", Toast.LENGTH_SHORT).show()
        })

    }

    private fun showEndGameDialog() {
        val dialog = EndGameDialog(this)
        dialog.isCancelable = false

        dialog.show(childFragmentManager, "EndSetDialog")

    }

    override fun endGame() {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToFinishedLeagueGameFragment(
                viewModel.createResult(1),
                viewModel.createResult(2),
                args.matchup
            )
        )
    }

    override fun undo() {
        viewModel.resetGameEnded()
        viewModel.onClickUndo()
    }

    override fun abortGame() {
        when (args.matchup) {
            is TournamentMatchup -> {
                findNavController().popBackStack(R.id.tournamentPagerFragment, false)
            }
            is LeagueMatchup -> {
                findNavController().popBackStack(R.id.leaguePagerFragment, false)
            }
            is QuickMatchup -> {
                findNavController().popBackStack(R.id.menuFragment, false)
            }
        }
    }
}

