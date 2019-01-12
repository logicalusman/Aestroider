package com.le.aestroider.util

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

    /**
     * Returns current date in yyyy-MM-dd format
     */
    fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }

    /**
     * Return date 7 days after current date in yyyy-MM-dd format
     */
    fun getDate7DaysAfterCurrentDate(): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 7)
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.time)
    }
}