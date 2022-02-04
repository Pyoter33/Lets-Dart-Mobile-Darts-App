package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R
import com.example.letsdart.models.general.SavedPlayer

class RenamePlayerDialog(
    private val playerInterface: PlayerInterface,
    private val player: SavedPlayer
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity).create()
        val inflater = requireActivity().layoutInflater
        val dialogLayout = inflater.inflate(R.layout.rename_player_dialog, null)

        dialog.setTitle(R.string.text_rename_player)
        dialog.setView(dialogLayout)
        dialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(R.string.text_rename)
        ) { _, _ ->
            val name = dialogLayout.findViewById<EditText>(R.id.editTextRename)?.text
            if (!TextUtils.isEmpty(name)) {
                player.playerName = name.toString()
                playerInterface.onDialogUpdate(player)
            }
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.text_cancel)) { dialog, _ ->
            dialog.cancel()
        }
        return dialog
    }


}