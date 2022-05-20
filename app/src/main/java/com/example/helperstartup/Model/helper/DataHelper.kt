package com.example.helperstartup.Model.helper

import android.icu.text.NumberFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatDate (date : String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd’T’HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.parse(date).toString()
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatRupiah(number: Integer): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
}
