package com.project.autoservicedatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.autoservicedatabase.models.ClientDBO

@Dao
interface ClientDao {
    @Query("SELECT * FROM client WHERE id = :id")
    suspend fun get(id: Int): List<ClientDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clientDBO: ClientDBO)

}