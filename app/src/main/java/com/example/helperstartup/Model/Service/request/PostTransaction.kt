package com.example.helperstartup.Model.Service.request

data class PostTransaction(
    val menu_id : Int?,
    val count : Int?,
    val amount : Int?,
    val address : String?,
    val is_morning : Boolean,
    val is_afternoon : Boolean,
    val is_noon : Boolean,
    val lat : Double?,
    val lng : Double?,
)
