package com.example.letsdart.models.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results(val wonGame: Boolean,  val statistics: Statistics): Parcelable
