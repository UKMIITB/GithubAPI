package com.example.githubapp.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DateTimeUtils {
    companion object {
        fun getLocalDateStringFromTimeZoneString(timeZoneString: String): String {
            val date = LocalDateTime.parse(timeZoneString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)
                .format(date)
        }
    }
}