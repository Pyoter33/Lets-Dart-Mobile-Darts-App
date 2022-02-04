package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R

class ExitAppDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.text_exit_game_dialog_message).setTitle(R.string.text_exit_game_dialog_title)
                .setPositiveButton(R.string.text_dialog_exit) { _, _ ->
                    activity?.finishAndRemoveTask()
                }
                .setNegativeButton(R.string.text_cancel) { dialog, _ ->
                    dialog.cancel()
                }.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
