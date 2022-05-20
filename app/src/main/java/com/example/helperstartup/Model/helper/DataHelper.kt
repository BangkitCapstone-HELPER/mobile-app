package com.example.helperstartup.Model.helper

import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate (date : String): String {
    val localDateTime = LocalDateTime.parse("2022-05-20T09:55:00")
    val formatter = DateTimeFormatter.ofPattern("dd-M-yyyy", Locale.getDefault())
    return formatter.format(localDateTime).toString()
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatRupiah(number: Int): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
}
