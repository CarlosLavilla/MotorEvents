package com.lavinef.motorevents.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MotorEventsDBConverters {

    private val _dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(value, _dateFormatter) }
    }

    @TypeConverter
    fun dateToString(value: LocalDateTime?): String? {
        return value?.format(_dateFormatter)
    }
}
