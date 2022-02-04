package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R
import com.example.letsdart.models.general.SavedPlayer

class CreatePlayerDialog(private val playerInterface: PlayerInterface) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity).create()
        val inflater = requireActivity().layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_player_dialog, null)

        dialog.setTitle(R.string.text_create_new_player)
        dialog.setView(dialogLayout)
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.text_create)
        ) { _, _ ->
            val name = dialogLayout.findViewById<EditText>(R.id.editTextRename)?.text
            if (!TextUtils.isEmpty(name))
                playerInterface.onDialogCreate(SavedPlayer(name.toString()))

        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.text_cancel)) { dialog, _ ->
            dialog.cancel()
        }
        return dialog
    }


}