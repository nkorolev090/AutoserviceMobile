package com.project.autoservicedatabase.models

import androidx.annotation.IntRange
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user_data")
data class UserDataDBO (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")val id: String,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("surname") val surname: String?,
    @ColumnInfo("midname") val midname: String?,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("userName") val userName: String,
    @ColumnInfo("phoneNumber") val phoneNumber: String?,
    @ColumnInfo("dateTime") val requestDateTime: LocalDateTime,
)