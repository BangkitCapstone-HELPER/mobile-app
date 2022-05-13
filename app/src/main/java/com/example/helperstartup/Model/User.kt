package com.example.helperstartup.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var email: String,
    var token: String,
    var isLogin: Boolean = false,
    var image: String,
    var phoneNumber : String
) : Parcelable
