package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UploadFileToStorageResponse(

	@field:SerializedName("data")
	val data: FileData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
) : Parcelable

@Parcelize
data class FileData(

	@field:SerializedName("file_code")
	val fileCode: String,

	@field:SerializedName("file_name")
	val fileName: String,

	@field:SerializedName("file_extension")
	val fileExtension: String,

	@field:SerializedName("url")
	val url: String
) : Parcelable
