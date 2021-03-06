package com.le.aestroider.util

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

/**
 * Handy methods.
 *
 * @author Usman
 */
object Utils {

    /**
     * Returns comparator for comparing dates in ascending order. The date format must be YYYY-MM-dd.
     */
    fun dateComparator(): Comparator<String> {
        return Comparator { o1, o2 ->
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val d1 = simpleDateFormat.parse(o1)
            val d2 = simpleDateFormat.parse(o2)
            return@Comparator d1.compareTo(d2)
        }
    }

    fun toDateFormatIn_dd_MM_yyyy(dateInFormat_yyyy_MM_dd: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = dateFormat.parse(dateInFormat_yyyy_MM_dd)
        return SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(date)
    }

    /**
     * Returns current date in yyyy-MM-dd format
     */
    fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }

    /**
     * Adds given num of days to the given date. Note that the given and returned days are in
     * yyyy-MM-dd format
     */
    fun addDaysToDate(date: String, numDaysAfter: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val d = sdf.parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = d
        calendar.add(Calendar.DATE, numDaysAfter)
        return sdf.format(calendar.time)
    }

    /**
     * Gets parameter value against the given parameter name and the given uri
     */
    fun getQueryParameter(uri: String, parameter: String): String {
        val uriObj = Uri.parse(uri)
        return uriObj.getQueryParameter(parameter)
    }
}