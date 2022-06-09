package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUploadScanner2(
	val code: String? = null,
	val data: List<DataItema?>? = null,
	val message: String? = null
) : Parcelable

@Parcelize
data class DataItema(
	val image: String? = null,
	val protein: String? = null,
	val calorie: String? = null,
	val fat: String? = null,
	val name: String? = null,
	val category: String? = null,
	val carbohydrate: String? = null
) : Parcelable
