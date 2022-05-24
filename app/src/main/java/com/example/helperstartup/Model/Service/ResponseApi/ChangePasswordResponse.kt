package com.example.helperstartup.Model.Service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("isAdmin")
	val isAdmin: Boolean,

	@field:SerializedName("email")
	val email: String
) : Parcelable
