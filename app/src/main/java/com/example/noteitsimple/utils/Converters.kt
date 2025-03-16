package com.example.noteitsimple.utils

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class Converters {
    @TypeConverter
    fun dateToString(date: LocalDateTime?): String {
        return date?.toString() ?: AppHelper.getCurrentDate().toString()
    }

    @TypeConverter
    fun fromString(value: String?): LocalDateTime {
        return value?.let { LocalDateTime.parse(it) } ?: AppHelper.getCurrentDate()
    }
}