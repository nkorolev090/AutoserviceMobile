package com.project.autoservicedatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.autoservicedatabase.models.UserDataDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {

    @Query("SELECT * FROM user_data")
    suspend fun getAll(): List<UserDataDBO>

    @Query("SELECT * FROM user_data WHERE id = :id")
    suspend fun get(id: String): List<UserDataDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDataDBO: UserDataDBO)

    @Delete
    suspend fun remove(userDataDBO: UserDataDBO)

    @Query("DELETE FROM user_data")
    suspend fun clean()
}