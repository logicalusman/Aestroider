package com.le.aestroider.util

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsTest {

    val datePattern = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    @Test
    fun dateComparator_sorts_date_list_in_ascending_order() {
        val list = mutableListOf<String>()
        list.add("2018-12-01")
        list.add("2018-10-01")
        list.add("2017-05-11")
        list.sortWith(Utils.dateComparator())
        Assert.assertEquals("2017-05-11", list[0])
        Assert.assertEquals("2018-10-01", list[1])
        Assert.assertEquals("2018-12-01", list[2])
    }

    @Test
    fun getCurrentDate_returns_current_date_yyyy_MM_dd_format() {
        val currentDate = datePattern.format(Date())
        Assert.assertEquals(currentDate, Utils.getCurrentDate())
    }

    @Test
    fun toDateFormatIn_dd_MM_yyyy_when_input_yyyy_MM_dd_format_then_return_dd_MM_yyyy_format() {
        Assert.assertEquals("09-03-2018", Utils.toDateFormatIn_dd_MM_yyyy("2018-03-09"))
        Assert.assertEquals("01-01-2099", Utils.toDateFormatIn_dd_MM_yyyy("2099-01-01"))
    }

    @Test
    fun toDateFormatIn_dd_MM_yyyy_when_not_input_yyyy_MM_dd_format_then_returns_incorrect_date() {
        Assert.assertNotEquals("01-09-2099", Utils.toDateFormatIn_dd_MM_yyyy("01-09-2099"))
    }

    @Test
    fun getCurrentDate_return_valid_todays_date_yyyy_MM_dd_format() {
        val todayDate = datePattern.format(Date())
        Assert.assertEquals(todayDate, Utils.getCurrentDate())
    }

    @Test
    fun addDaysToDate_when_input_yyyy_MM_dd_format_date_and_daysToAdd_then_return_correct_date() {
        var dateStringToTest = "2018-05-01"
        Assert.assertEquals("2018-05-02", Utils.addDaysToDate(dateStringToTest, 1))
        Assert.assertEquals("2018-05-06", Utils.addDaysToDate(dateStringToTest, 5))
        Assert.assertEquals("2018-05-10", Utils.addDaysToDate(dateStringToTest, 9))
        dateStringToTest = "2018-02-28"
        Assert.assertEquals("2018-03-01", Utils.addDaysToDate(dateStringToTest, 1))
        dateStringToTest = "2018-12-31"
        Assert.assertEquals("2019-01-03", Utils.addDaysToDate(dateStringToTest, 3))
    }

    @Test
    fun getQueryParameter_when_input_valid_uri_with_queryStrings_then_return_query_param_value() {
        val url =
            "https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-01-14&end_date=2018-01-15&detailed=false&api_key=1234"

        Assert.assertEquals("2018-01-14", Utils.getQueryParameter(url, "start_date"))
    }

}