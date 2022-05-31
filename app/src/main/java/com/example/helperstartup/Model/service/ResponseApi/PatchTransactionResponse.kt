package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PatchTransactionResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
) : Parcelable
