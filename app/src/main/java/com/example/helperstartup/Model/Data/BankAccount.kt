package com.example.helperstartup.Model.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankAccount(
    val id: Int,
    val imageUrl: String?,
    val bankName: String,
    val bankNumber: String,
    val bankNameAccount : String
) : Parcelable

