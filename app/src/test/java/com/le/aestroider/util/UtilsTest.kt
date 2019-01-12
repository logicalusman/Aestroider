package com.le.aestroider.util

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest {

    val datePattern = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    @Test
    fun getCurrentDate_returns_current_date_yyyy_MM_dd_format() {
        val currentDate = datePattern.format(Date())
        Assert.assertEquals(currentDate, Utils.getCurrentDate())
    }

    @Test
    fun getDate7DaysAfterCurrentDate_returns_7_days_after_date_yyyy_MM_dd_format() {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.DATE, 7)
        Assert.assertEquals(datePattern.format(c.time), Utils.getDate7DaysAfterCurrentDate())
    }
}