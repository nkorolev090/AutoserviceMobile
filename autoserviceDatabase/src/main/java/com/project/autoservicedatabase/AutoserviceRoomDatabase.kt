package com.project.autoservicedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.autoservicedatabase.dao.UserDataDao
import com.project.autoservicedatabase.models.UserDataDBO

class AutoserviceDatabase internal constructor(private val database: AutoserviceRoomDatabase){
    val userDataDao: UserDataDao
        get() = database.userDataDao()
}
@Database(entities = [UserDataDBO::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
internal abstract class AutoserviceRoomDatabase : RoomDatabase() {

    abstract fun userDataDao(): UserDataDao

}
fun AutoserviceDatabase(applicationContext: Context): AutoserviceDatabase {
    val autoserviceRoomDatabase =
        Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        AutoserviceRoomDatabase::class.java,
        "autoservice"
    ).build()
    return AutoserviceDatabase(autoserviceRoomDatabase)
}