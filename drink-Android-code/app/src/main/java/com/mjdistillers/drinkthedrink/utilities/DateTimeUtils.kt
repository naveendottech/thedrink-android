package com.mjdistillers.drinkthedrink.utilities

import java.text.SimpleDateFormat
import java.util.*

/*

Date and Time Pattern	   			    Result

"yyyy.MM.dd G 'at' HH:mm:ss z"    	   2001.07.04 AD at 12:08:56 PDT

"EEE, MMM d, ''yy"			           Wed, Jul 4, '01

"h:mm a"				               12:08 PM

"hh 'o''clock' a, zzzz"			       12 o'clock PM, Pacific Daylight Time

"K:mm a, z"				               0:08 PM, PDT

"yyyyy.MMMMM.dd GGG hh:mm aaa"		   02001.July.04 AD 12:08 PM

"EEE, d MMM yyyy HH:mm:ss Z"		   Wed, 4 Jul 2001 12:08:56 -0700

"yyMMddHHmmssZ"				           010704120856-0700

"yyyy-MM-dd'T'HH:mm:ss.SSSZ"		   2001-07-04T12:08:56.235-0700

"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"	       2001-07-04T12:08:56.235-07:00

"YYYY-'W'ww-u"				           2001-W27-3

*/

/**
 * Contains date time utils
 * */

class DateTimeUtils {

    companion object {

        val SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val TIME_STAMP_FORMAT = "yyMMddHHmmssZ"
        val EMDY = "EEE, MMM d, ''yy"

        fun formatDate(format: String, date: Date): String {
            val format = SimpleDateFormat(format)
            val resultDate = format.format(date)
            return if (resultDate.isEmpty()) "" else resultDate
        }

        fun formatDate(fromFormat: String, toFormat: String, textDate: String): String {
            return if (!textDate.isEmpty()) {
                val fromFormatDate = SimpleDateFormat(fromFormat)
                val toFormatDate = SimpleDateFormat(toFormat)
                toFormatDate.format(fromFormatDate.parse(textDate))
            } else
                ""
        }

        fun getMillis(fromat: String, textDate: String): Long {
            var millis = 0L
            val dateFormat: SimpleDateFormat?
            var date: Date? = null

            if (!textDate.isEmpty()) {
                try {
                    dateFormat = SimpleDateFormat(fromat)
                    date = dateFormat.parse(textDate)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

                date?.let {
                    millis = date.time
                }
            }
            return millis
        }

        /**
         * @param startDate is startDate
         * @param endDate is endDate
         *
         * @return MutableList<Long>
         *   This list contains following as per index
         *   0 -> days
         *   1 -> hours
         *   2 -> minutes
         *   3 -> seconds
         *
         *   This will Work as following
         *
         *    startDate : Thu Oct 10 11:30:10 SGT 2013
         *    endDate : Sun Oct 13 20:35:55 SGT 2013
         *    different : 291945000
         *    3 days, 9 hours, 5 minutes, 45 seconds
         *
         * */
        fun getDifference(startDate: Date, endDate: Date): MutableList<Long> {
            var different = endDate.time - startDate.time

            val secondsInMilli: Long = 1000  // 1000 millis = 1 second
            val minutesInMilli = secondsInMilli * 60 // 60,000 millis = 1 minute
            val hoursInMilli = minutesInMilli * 60  // 36,00,000 millis = 1 hour
            val daysInMilli = hoursInMilli * 24  //  864,00,000 millis = 1 day

            val elapsedDays = different / daysInMilli
            different %= daysInMilli

            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            var details = mutableListOf<Long>()
            details.add(elapsedDays)
            details.add(elapsedHours)
            details.add(elapsedMinutes)
            details.add(elapsedSeconds)

            return details
        }

        /**
         * @date - date to operate on
         *
         * @amount -
         * To add use amount > 0 Example 1 will add one, 2 will add two
         * To subtract use amount < 0 Example -1 will subtract one, -2 will subtract two
         *
         * @field Use field corresponding to requirement
         * example Calendar.HOUR_OF_DAY, Calendar.DAY_OF_MONTH
         *
         * */
        fun addSubtractFromDate(date: Date, field: Int, amount: Int): Date {
            var calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(field, amount)
            return calendar.time
        }

    }

    object DateFormats {
        private var saperator = "-"

        val DMY = "dd" + saperator + "MM" + saperator + "yyyy"
        val DMonY = "dd" + saperator + "MMM" + saperator + "yyyy"
        val DMonthY = "dd" + saperator + "MMMM" + saperator + "yyyy"

        val MDY = "MM" + saperator + "dd" + saperator + "yyyy"
        val MonDY = "MMM" + saperator + "dd" + saperator + "yyyy"
        val MonthDY = "MMMM" + " " + "dd" + ", " + "yyyy"

        fun getDateAsYMD(yyyy: Int, MM: Int, dd: Int, separator: String): String {
            return "$yyyy $separator $MM $separator $dd"
        }

    }

    object TimeFormats {
        private var saperator = ":"
        val HMS = "HH" + saperator + "mm" + saperator + "ss"
        val HMMeridium = "h" + saperator + "mm" + " a"

        fun getTimeAsHMS(h: Int, m: Int, s: Int, separator: String): String {
            return "$h $separator $m $separator $s"
        }
    }

}