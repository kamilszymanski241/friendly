package com.friendly.helpers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetIn
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

sealed class DateTimeHelper() {
    companion object {
        fun convertMillisToDate(millis: Long): String {
            val dateTime = Instant.fromEpochMilliseconds(millis)
            val date = dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
            return date
        }

        fun getCurrentDateAsString(): String {
            return Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        }

        fun getCurrentTimeAsString(): String {
            val hour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
            val minute = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).minute
            return LocalTime(hour = hour, minute = minute).toString()
        }

        fun getCurrentDateAsLocalDate(): LocalDate {
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        }

        fun convertDateAndTimeToSupabaseTimestamptz(dateString: String, timeString: String): String {
            try {
                val localDate = LocalDate.parse(dateString)
                val (hour, minute) = timeString.split(":").map { it.toInt() }
                val localDateTime = LocalDateTime(localDate.year, localDate.monthNumber, localDate.dayOfMonth, hour, minute)

                val timeZone = TimeZone.currentSystemDefault()

                val instant = localDateTime.toInstant(timeZone)
                val zonedDateTime = instant.toLocalDateTime(timeZone)

                val offset = instant.offsetIn(timeZone)
                val formattedDateTime = "${zonedDateTime.date} ${zonedDateTime.time}:00$offset"
                println(formattedDateTime)
                return formattedDateTime
            } catch (e: Exception) {
                println("Incorrect format")
                return ""
            }
        }

        fun parseDateFromSupabaseTimestampzToString(dateTime: String): String{
            val instant = Instant.parse(dateTime)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            return localDateTime.date.toString()
        }

        fun parseDateFromSupabaseTimestampzToLocalDate(dateTime: String): LocalDate{
            val instant = Instant.parse(dateTime)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            return localDateTime.date
        }

        fun parseTimeFromSupabaseTimestampz(dateTime: String): String{
            val instant = Instant.parse(dateTime)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            return LocalTime(localDateTime.hour, localDateTime.minute).toString()
        }

        fun getAgeFromDateOfBirth(date: String): Int{
            val dob = LocalDate.parse(date)
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            return dob.periodUntil(today).years
        }
    }
}