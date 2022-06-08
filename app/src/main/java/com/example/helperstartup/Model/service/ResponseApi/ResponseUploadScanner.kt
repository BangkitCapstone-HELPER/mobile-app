package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUploadScanner(
	val data: Datas? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItems(
	val image: String? = null,
	val protein: String? = null,
	val calorie: String? = null,
	val fat: String? = null,
	val name: String? = null,
	val category: String? = null,
	val carbohydrate: String? = null
) : Parcelable

@Parcelize
data class Datas(
	val code: String? = null,
	val data: List<DataItems?>? = null,
	val message: String? = null
) : Parcelable
