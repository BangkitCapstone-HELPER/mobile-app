package com.example.helperstartup.Model.Service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUploadScanner(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItems?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataItems(

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("calorie")
	val calorie: String? = null,

	@field:SerializedName("fat")
	val fat: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: String? = null
) : Parcelable
