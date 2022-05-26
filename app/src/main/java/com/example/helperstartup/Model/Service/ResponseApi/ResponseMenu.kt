package com.example.helperstartup.Model.Service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseMenu(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Int? = null
) : Parcelable

@Parcelize
data class DayMenusItem(
	val image: String? = null,
	val items: List<String?>? = null,
	val day: String? = null
) : Parcelable

@Parcelize
data class DataItem(
	val price: Int? = null,
	val description: String? = null,
	@SerializedName("day_menus")
	val dayMenus: List<DayMenusItem?>? = null,
	val id: Int? = null,
	val title: String? = null
) : Parcelable
