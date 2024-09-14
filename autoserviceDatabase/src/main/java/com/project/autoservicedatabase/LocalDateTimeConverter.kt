package com.project.autoservicedatabase

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {

    @TypeConverter
    public fun fromLocalDateTime(localDateTime: LocalDateTime): String{
        return localDateTime.toString()
    }

    @TypeConverter
    public fun toLocalDateTime(localDateTime: String): LocalDateTime{
        return LocalDateTime.parse(localDateTime)
    }
}