package com.example.letsdart.dialogs

import com.example.letsdart.models.general.SavedPlayer

interface PlayerInterface {
    fun onDialogCreate(player: SavedPlayer)
    fun onDialogUpdate(player: SavedPlayer)
}

interface DeleteInterface{
    fun <T> delete(item: T)
}