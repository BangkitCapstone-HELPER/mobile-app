package com.example.helperstartup.Model.service.ResponseApi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionResponse(
    val data: List<TransactionResponseItem>? = null,
    val message: String? = null,
    val status: Int? = null
) : Parcelable

@Parcelize
data class TransactionResponseItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("menu_id")
    val menuId: Int,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("amount")
    val amount: Int,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("remaining")
    val remaining: Int,

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("upload")
    val upload: String? = null,

    @field:SerializedName("menu")
    val menu: Menu,

    @field:SerializedName("is_morning")
    val isMorning: Boolean? = null,

    @field:SerializedName("is_afternoon")
    val isAfternoon: Boolean? = null,

    @field:SerializedName("is_noon")
    val isNoon: Boolean? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    ) : Parcelable


@Parcelize
data class Menu(

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("day_menus")
    val dayMenus: List<DayMenusItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String
) : Parcelable
