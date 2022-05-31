package com.example.helperstartup.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleModel(
    var title: String?,
    val url: String?,
    var photo: String?
) : Parcelable