package com.paytv.premiere.smartwatch.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.getRelativeDate(): String {
    val todayCalendar = Calendar.getInstance()
    val targetCalendar = Calendar.getInstance().apply {
        timeInMillis = this@getRelativeDate.time
    }

    val dayDifference =
        targetCalendar.get(Calendar.DAY_OF_MONTH) - todayCalendar.get(Calendar.DAY_OF_MONTH)
    val formattedDate = Date(this@getRelativeDate.time)

    if (dayDifference in -1..1) {
        return weekday(dayDifference)
    }

    return formattedDate.format(PT_BR_SIMPLE_DATE_FORMAT)
}

fun Date.format(pattern: String = DB_DATE_FORMAT): String {
    return getSimpleDateFormatter(pattern).format(this)
}

private fun getSimpleDateFormatter(pattern: String = DB_DATE_FORMAT): SimpleDateFormat {
    return SimpleDateFormat(pattern, Locale("pt", "BR")).also {
        it.timeZone = TimeZone.getDefault()
    }
}

fun weekday(quantity: Int): String =
    when (quantity) {
        0 -> DATE_TODAY
        1 -> DATE_TOMORROW
        else -> DATE_YESTERDAY
    }

fun Date.mapTime() = this.format(DATE_HOUR_MINUTE_FORMAT)

const val DB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
const val PT_BR_SIMPLE_DATE_FORMAT = "dd/MM"
const val DATE_HOUR_MINUTE_FORMAT = "HH:mm"
const val DATE_TODAY = "Hoje"
const val DATE_YESTERDAY = "Ontem"
const val DATE_TOMORROW = "Amanhã"
