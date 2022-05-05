package com.rbc.rbcmobileapplication.utils

import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CalenderToDate {

    private const val TAG = "CalenderToDate"

    fun getDateFromCalender(calendar: Calendar): String =
        SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(calendar.time)

    fun getDisplayDate(dateString: String): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(dateString)
        } catch (e: ParseException) {
            Log.e(TAG, e.message.toString())
        }
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
        return dateFormat.format(date ?: dateString)
    }

    fun getDateForSorting(date: String): String {
        val dateSplitArray = date.split("-")
        return dateSplitArray[2] + dateSplitArray[1] + dateSplitArray[0]
    }

}