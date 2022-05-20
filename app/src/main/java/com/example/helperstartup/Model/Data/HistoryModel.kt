package com.example.helperstartup.Model.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel(
    val imageUrl: String?,
    val title: String?,
    val status: String?,
    val description : String?,
    val date: String?,
    val price: Integer?
) : Parcelable