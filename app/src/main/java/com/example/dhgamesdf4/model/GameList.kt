package com.example.dhgamesdf4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameList (
    val gameName: String,
    val gameCreateAt: String,
    val gameImage: String
) : Parcelable