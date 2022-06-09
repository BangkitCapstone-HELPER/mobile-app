package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseArticle2(
	val data: List<DataItem3?>? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItem3(
	val enclosure: Enclosure2? = null,
	val link: List<String?>? = null,
	val guid: String? = null,
	val description: Description2? = null,
	val title: String? = null,
	val pubDate: String? = null
) : Parcelable

@Parcelize
data class Enclosure2(
	val type: String? = null,
	val length: String? = null,
	val _url: String? = null
) : Parcelable

@Parcelize
data class Description2(
	val __cdata: String? = null
) : Parcelable
