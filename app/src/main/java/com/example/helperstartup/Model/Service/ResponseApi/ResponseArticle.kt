package com.example.helperstartup.Model.Service.ResponseApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseArticle(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItem(
	val enclosure: Enclosure? = null,
	val link: List<String?>? = null,
	val guid: String? = null,
	val description: Description? = null,
	val title: String? = null,
	val pubDate: String? = null
) : Parcelable

@Parcelize
data class Enclosure(
	val type: String? = null,
	val length: String? = null,
	val _url: String? = null
) : Parcelable

@Parcelize
data class Description(
	val __cdata: String? = null
) : Parcelable
