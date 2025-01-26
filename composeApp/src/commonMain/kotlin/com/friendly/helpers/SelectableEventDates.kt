package com.friendly.helpers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
class SelectableEventDates(private val type: SelectableDatesTypes): SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return when(type){
            SelectableDatesTypes.All -> {
                true
            }
            SelectableDatesTypes.Past -> {
                Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date <=
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            }
            SelectableDatesTypes.Future -> {
                Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date >=
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            }
        }
    }

    override fun isSelectableYear(year: Int): Boolean {
        return when(type) {
            SelectableDatesTypes.All -> {
                true
            }
            SelectableDatesTypes.Past ->{
                year <= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
            }
            SelectableDatesTypes.Future ->{
                year >= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
            }
        }
    }
}