package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R

class AbortGameDialog(private val abortGameDialogInterface: AbortGameDialogInterface) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.text_abort_game_dialog_message).setTitle(R.string.text_abort_game_dialog_title)
                .setPositiveButton(R.string.text_dialog_continue) { dialog, _ ->
                    dialog.cancel()
                }
                .setNegativeButton(R.string.text_dialog_abort) { _, _ ->
                    abortGameDialogInterface.abortGame()
                }.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

interface AbortGameDialogInterface{
    fun abortGame()
}