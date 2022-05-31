package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostTransactionResponse(

	@field:SerializedName("data")
	val data: DataTransaction,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
) : Parcelable


@Parcelize
data class DataTransaction(

	@field:SerializedName("is_afternoon")
	val isAfternoon: Boolean,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("lng")
	val lng: Int,

	@field:SerializedName("upload")
	val upload: String,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("menu")
	val menu: Menu,

	@field:SerializedName("remaining")
	val remaining: Int,

	@field:SerializedName("is_noon")
	val isNoon: Boolean,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("is_morning")
	val isMorning: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("menu_id")
	val menuId: Int,

	@field:SerializedName("lat")
	val lat: Int,

	@field:SerializedName("status")
	val status: String
) : Parcelable
