package com.friendly.helpers

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class DateTimeHelper() {
    companion object {
        fun convertMillisToDate(millis: Long): String {
            val dateTime = Instant.fromEpochMilliseconds(millis)
            val date = dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
            return date
        }

        fun getCurrentDate(): String {
            return Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        }

        fun getCurrentTime(): String {
            val hour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
            val minute = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).minute
            return LocalTime(hour = hour, minute = minute).toString()
        }
    }
}