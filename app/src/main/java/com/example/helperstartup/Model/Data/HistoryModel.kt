package com.example.helperstartup.Model.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel(
    val id : Int,
    val imageUrl: String?,
    val title: String?,
    val status: String?,
    val description: String?,
    val expiredTime : String?,
    val date: String?,
    val price: Int
) : Parcelable