package com.project.autoservicedatabase.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
class ClientDBO (
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("discountName") val discountName: String,
    @ColumnInfo("discountPoints")val discountPoints: Int,
    @ColumnInfo("defaultStationId")val defaultStationId: Int?,
    @ColumnInfo("defaultCarId")val defaultCarId: Int?,
    @ColumnInfo("birthday")val birthday: String
)