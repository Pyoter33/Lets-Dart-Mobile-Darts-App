package com.example.letsdart.viewModels.game

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.letsdart.models.general.*
import java.util.*


class GameViewModel(
    private val rules: Rules
) : ViewModel() {
    val player1 = Player()
    val player2 = Player()

    private val _playerTurn = MutableLiveData<Int>()
    val playerTurn: LiveData<Int> = _playerTurn

    private val _isDoubleEnabled = MutableLiveData(false)
    val isDoubleEnabled: LiveData<Boolean> = _isDoubleEnabled

    private val _isTripleEnabled = MutableLiveData(false)
    val isTripleEnabled: LiveData<Boolean> = _isTripleEnabled

    private val _deciderRandom = MutableLiveData(0)
    val deciderRandom: LiveData<Int> = _deciderRandom

    private val _isGameEnded = MutableLiveData(false)
    val isGameEnded: LiveData<Boolean> = _isGameEnded

    private val _hintData = MutableLiveData<List<String>?>()
    val hintData: LiveData<List<String>?> = _hintData

    private val playerTurnStack = Stack<Int>()

    private var whoStartsLeg: Int
    private var whoStartsSet: Int

    private lateinit var checkouts: Map<Int, List<String>>

    init {
        _playerTurn.value = 1
        _isDoubleEnabled.value = false
        _isTripleEnabled.value = false
        whoStartsLeg = 1
        whoStartsSet = 1
        createNewGame(player1)
        createNewGame(player2)

    }


    fun onClickNumber(value: Int) {
        when (playerTurn.value) {
            1 -> countScore(value, player1)
            2 -> countScore(value, player2)
        }
        checkHints()
        resetDoubleTriple()
    }

    fun onClickDouble() {
        if (!isDoubleEnabled.value!!)
            _isTripleEnabled.value = false

        _isDoubleEnabled.value = !isDoubleEnabled.value!!
    }

    fun onClickTriple() {
        if (!isTripleEnabled.value!!)
            _isDoubleEnabled.value = false

        _isTripleEnabled.value = !isTripleEnabled.value!!
    }

    fun onClickUndo() {
        resetDoubleTriple()
        when (playerTurn.value) {
            1 -> undo(player1, player2)
            2 -> undo(player2, player1)
        }
        checkHints()
    }

    fun resetGameEnded() {
        _isGameEnded.value = false
    }

    fun createResult(playerNo: Int): Results {
        return if (playerNo == 1)
            createPlayerResult(player1, player2)
        else
            createPlayerResult(player2, player1)

    }

    fun getCheckouts(resources: Resources, fileName: Int, checkout: Int) {
        val checkoutHelper = CheckoutHelper()
        checkouts = checkoutHelper.readCheckout(resources, fileName, checkout)

    }

    private fun resetDoubleTriple() {
        _isTripleEnabled.value = false
        _isDoubleEnabled.value = false
    }

    private fun createNewGame(player: Player) {
        player.currentState = PlayerStateFactory.createPlayerStateNewRound(null, null, rules.startScore)
        player.currentRoundState = player.currentState
        player.roundStateStack.push(player.currentRoundState)
        player.stateStack.push(player.currentState)
        updatePlayerStats(player)
    }

    private fun updatePlayerStats(player: Player) {
        player.mainScore.value = player.currentState.mainScore

        player.roundScore.value = player.currentState.roundScore

        for (i in player.partialScore.indices)
            if (i < player.currentState.partialScore.size)
                player.partialScore[i].value = player.currentState.partialScore[i]
            else
                player.partialScore[i].value = ""

        player.average.value = player.currentState.average
        player.doubles.value = player.currentState.doubles
        player.triples.value = player.currentState.triples
        player.wonLegs.value = player.currentState.wonLegs
        player.wonSets.value = player.currentState.wonSets
    }

    private fun countScore(value: Int, player: Player) {
        val newState = PlayerStateFactory.createPlayerState(value, player.currentState, isDoubleEnabled.value!!, isTripleEnabled.value!!)

        if (newState.mainScore < 0)
            return addOverthrow(player)

        if (newState.mainScore == 0) {
            if (rules.checkout == 1)
                if (newState !is DoublePlayerState)
                    return addOverthrow(player)
            return endLeg(value)
        }

        if (newState.mainScore == 1) {
            if (rules.checkout == 1)
                if (newState !is DoublePlayerState)
                    return addOverthrow(player)
        }

        player.currentState = newState
        player.stateStack.push(player.currentState)
        updatePlayerStats(player)
        if (player.currentState.partialScore.size == 3)
            changeRound()
    }

    private fun checkHints() {
        when (playerTurn.value) {
            1 -> sendHints(player1)
            2 -> sendHints(player2)
        }
    }

    private fun sendHints(player: Player) {
        val checkoutValue = checkouts[player.currentState.mainScore]
        if (checkoutValue.isNullOrEmpty()) {
            _hintData.value = null
            return
        }

        if (3 - player.currentState.partialScore.size < checkoutValue.size) {
            _hintData.value = null
            return
        }

        _hintData.value = checkoutValue
    }

    private fun reverseRoundChange() {
        when (playerTurnStack.pop()) {
            1 -> reverse(player1, 1)
            2 -> reverse(player2, 2)
        }
    }

    private fun reverse(player: Player, changeToTurn: Int) {
        _playerTurn.value = changeToTurn
        if (player.roundStateStack.peek() !is StartGamePlayerState)
            player.roundStateStack.pop()
        player.currentRoundState = player.roundStateStack.peek()
    }

    private fun changeRound() {
        when (playerTurn.value) {
            1 -> addNewRound(player2, 2)
            2 -> addNewRound(player1, 1)
        }
    }

    private fun addNewRound(player: Player, changeToTurn: Int) {
        playerTurnStack.push(playerTurn.value)
        _playerTurn.value = changeToTurn
        val newState = PlayerStateFactory.createPlayerStateNewRound(null, player.currentState)
        player.currentState = newState
        player.currentRoundState = player.currentState
        player.roundStateStack.push(player.currentRoundState)
        player.stateStack.push(player.currentState)
        updatePlayerStats(player)
    }

    private fun addOverthrow(player: Player) {
        val newState = PlayerStateFactory.createPlayerStateOverthrow(player.currentRoundState, player.currentState)
        player.currentState = newState
        player.stateStack.push(player.currentState)
        player.currentRoundState = player.currentState
        player.roundStateStack.push(player.currentRoundState)
        updatePlayerStats(player)
        changeRound()
        checkHints()
    }

    private fun undo(player: Player, playerToRecover: Player) {
        when (player.stateStack.peek()) {
            is StartGamePlayerState -> {//do nothing
            }

            is NewRoundPlayerState -> {
                player.stateStack.pop()
                player.currentState = player.stateStack.peek()
                playerToRecover.stateStack.pop()
                playerToRecover.currentState = playerToRecover.stateStack.peek()
                reverseRoundChange()
            }

            is NewLegPlayerState -> {
                player.stateStack.pop()
                player.currentState = player.stateStack.peek()
                playerToRecover.stateStack.pop()
                playerToRecover.currentState = playerToRecover.stateStack.peek()
                changeTurnNormalLegs()
                reverseRoundChange()
            }

            is NewSetPlayerState -> {
                player.stateStack.pop()
                player.currentState = player.stateStack.peek()
                playerToRecover.stateStack.pop()
                playerToRecover.currentState = playerToRecover.stateStack.peek()
                changeTurnNormalSets()
                reverseRoundChange()
            }

            else -> {
                player.stateStack.pop()
                player.currentState = player.stateStack.peek()
            }
        }
        updatePlayerStats(player)
        updatePlayerStats(playerToRecover)
    }

    private fun endLeg(value: Int) {
        when (playerTurn.value) {
            1 -> addNewLeg(player1, player2, value)
            2 -> addNewLeg(player2, player1, value)
        }
    }

    private fun addNewLeg(playerWinner: Player, playerLoser: Player, value: Int) {
        playerTurnStack.push(playerTurn.value)
        val newStateWinner = PlayerStateFactory.createPlayerStateNewRound(value, playerWinner.currentState, rules.startScore, true, isDoubleEnabled =  isDoubleEnabled.value!!, isTripleEnabled =  isTripleEnabled.value!!)
        if (checkNewSet(newStateWinner)) {
            addNewSet(playerWinner, playerLoser, value)
            return
        }
        val newStateLoser = PlayerStateFactory.createPlayerStateNewRound(value, playerLoser.currentState, rules.startScore, false, isDoubleEnabled =  isDoubleEnabled.value!!, isTripleEnabled =  isTripleEnabled.value!!)

        updateLegs(playerWinner, newStateWinner)
        updateLegs(playerLoser, newStateLoser)
        changeLeg(newStateWinner, newStateLoser)
        updatePlayerStats(playerWinner)
        updatePlayerStats(playerLoser)
    }

    private fun addNewSet(playerWinner: Player, playerLoser: Player, value: Int) {
        val newStateWinner =
            PlayerStateFactory.createPlayerStateNewRound(value, playerWinner.currentState, rules.startScore, newSet = true, wonSet = true, isDoubleEnabled =  isDoubleEnabled.value!!, isTripleEnabled =  isTripleEnabled.value!!)
        updateLegs(playerWinner, newStateWinner)

        val newStateLoser = PlayerStateFactory.createPlayerStateNewRound(value, playerLoser.currentState, rules.startScore, newSet = true, isDoubleEnabled =  isDoubleEnabled.value!!, isTripleEnabled =  isTripleEnabled.value!!)
        updateLegs(playerLoser, newStateLoser)
        changeSet(newStateWinner, newStateLoser)

        if (checkForWin(newStateWinner))
            _isGameEnded.value = true

        updatePlayerStats(playerWinner)
        updatePlayerStats(playerLoser)
    }

    private fun checkForWin(newStateWinner: PlayerState): Boolean {
        when (rules.winCondition) {
            0 -> return newStateWinner.wonSets == rules.numberOfSets / 2 + 1
            1 -> return newStateWinner.wonSets == rules.numberOfSets
        }
        return false
    }

    private fun changeTurnNormalLegs() {
        when (whoStartsLeg) {
            1 -> {
                _playerTurn.value = 2
                whoStartsLeg = 2
            }
            2 -> {
                _playerTurn.value = 1
                whoStartsLeg = 1
            }
        }
    }

    private fun updateLegs(player: Player, newState: PlayerState) {
        player.currentState = newState
        player.currentRoundState = player.currentState
        player.stateStack.push(player.currentState)
        player.roundStateStack.push(player.currentState)
    }

    private fun changeLeg(newStateWinner: PlayerState, newStateLoser: PlayerState) {
        if (rules.deciderType == 0)
            return changeTurnNormalLegs()

        if (checkForDeciderLegs(rules.winCondition, newStateWinner, newStateLoser))
            changeTurnForDeciderLegs()
        else
            changeTurnNormalLegs()
    }

    private fun changeSet(newStateWinner: PlayerState, newStateLoser: PlayerState) {
        if (rules.deciderType == 0) {
            changeTurnNormalSets()
            whoStartsLeg = whoStartsSet
            return
        }
        if (checkForDeciderSets(rules.winCondition, newStateWinner, newStateLoser))
            changeTurnForDeciderSets()
        else
            changeTurnNormalSets()

        whoStartsLeg = whoStartsSet
    }

    private fun checkNewSet(newStateWinner: PlayerState): Boolean {
        when (rules.winCondition) {
            0 -> return newStateWinner.wonLegs == rules.numberOfLegs / 2 + 1
            1 -> return newStateWinner.wonLegs == rules.numberOfLegs
        }
        return false
    }

    private fun changeTurnForDeciderLegs() {
        val rand = Random()
        val turn = rand.nextInt(2) + 1
        _playerTurn.value = turn
        whoStartsLeg = turn
        _deciderRandom.value = 1
    }

    private fun checkForDeciderLegs(winCondition: Int, newStateWinner: PlayerState, newStateLoser: PlayerState): Boolean {
        when (winCondition) {
            0 -> return newStateWinner.wonLegs == rules.numberOfLegs / 2 && newStateLoser.wonLegs == rules.numberOfLegs / 2
            1 -> return newStateWinner.wonLegs == rules.numberOfLegs - 1 && newStateLoser.wonLegs == rules.numberOfLegs - 1
        }
        return false
    }


    private fun checkForDeciderSets(winCondition: Int, newStateWinner: PlayerState, newStateLoser: PlayerState): Boolean {
        when (winCondition) {
            0 -> return newStateWinner.wonSets == rules.numberOfSets / 2 && newStateLoser.wonSets == rules.numberOfSets / 2
            1 -> return newStateWinner.wonSets == rules.numberOfSets - 1 && newStateLoser.wonSets == rules.numberOfSets - 1
        }
        return false
    }

    private fun changeTurnForDeciderSets() {
        val rand = Random()
        val turn = rand.nextInt(2) + 1
        _playerTurn.value = turn
        whoStartsSet = turn
        _deciderRandom.value = 2
    }

    private fun changeTurnNormalSets() {
        when (whoStartsSet) {
            1 -> {
                _playerTurn.value = 2
                whoStartsSet = 2
            }
            2 -> {
                _playerTurn.value = 1
                whoStartsSet = 1
            }
        }
    }


    private fun createPlayerResult(player: Player, otherPlayer: Player): Results {
        val gameWon = player.wonSets.value == rules.numberOfSets
        return Results(
            gameWon, Statistics(
                1,
                if (gameWon) 1 else 0,
                if (gameWon) 0 else 1,
                player.currentState.allWonLegs,
                otherPlayer.currentState.allWonLegs,
                player.currentState.scoreSum.toLong(),
                player.currentState.scoreCount,
                player.currentState.singles,
                player.currentState.doubles,
                player.currentState.triples,
                player.currentState.scoresMap
            )
        )
    }
}