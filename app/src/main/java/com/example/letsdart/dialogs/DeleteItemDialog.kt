package com.example.letsdart.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.letsdart.R


class DeleteItemDialog<T> (private val itemDeleter: DeleteInterface,
                           private val message: String, private val item: T)
: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle(getString(R.string.text_delete_dialog_title, message)).
                setMessage(getString(R.string.text_delete_dialog, message)).
                 setPositiveButton(R.string.text_delete) { _, _ ->
                        itemDeleter.delete(item)
                }
                .setNegativeButton(R.string.text_cancel) { dialog, _ ->
                    dialog.cancel()
                }.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

