package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R

class EndGameDialog(private val endGameDialogInterface: EndGameDialogInterface) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.text_end_game_dialog_message).setTitle(R.string.text_end_game_dialog_title)
                .setPositiveButton(R.string.text_dialog_continue) { _, _ ->
                   endGameDialogInterface.endGame()
                }
                .setNegativeButton(R.string.text_dialog_undo) { _, _ ->
                    endGameDialogInterface.undo()
                }.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

interface EndGameDialogInterface{
    fun endGame()
    fun undo()
}