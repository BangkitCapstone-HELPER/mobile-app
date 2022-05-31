package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterResponse(

	@field:SerializedName("data")
	val data: DataRegister? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataRegister(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isAdmin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
