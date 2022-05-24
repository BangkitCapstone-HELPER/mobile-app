package com.example.helperstartup.Model.helper

import android.icu.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

fun formatDate (date : String): String {
    val localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
    return formatter.format(localDateTime).toString()
}

fun formatRupiah(number: Int): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
}
