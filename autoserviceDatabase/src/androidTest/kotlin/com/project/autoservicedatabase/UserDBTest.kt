package com.project.autoservicedatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.project.autoservicedatabase.dao.UserDataDao
import com.project.autoservicedatabase.models.UserDataDBO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class UserDBTest {
    private lateinit var db: AutoserviceRoomDatabase
    private lateinit var dao: UserDataDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AutoserviceRoomDatabase::class.java).build()
        dao = db.userDataDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllTest() = runBlocking {
        val users = dao.getAll()

        Assert.assertEquals(users, listOf<UserDataDBO>())
    }

    @Test
    fun insertAndGet() = runBlocking {
        val user = UserDataDBO(
            "10",
            "name",
            "surname",
            "midname",
            "email",
            "uName",
            "79999999999",
            LocalDateTime.now()
        )

        dao.insert(user)

        val userFromDB = dao.get(user.id).first()

        Assert.assertEquals(user, userFromDB)
    }

    @Test
    fun insertAndGetAll() = runBlocking {
        val user = UserDataDBO(
            "10",
            "name",
            "surname",
            "midname",
            "email",
            "uName",
            "79999999999",
            LocalDateTime.now()
        )

        dao.insert(user)

        val usersFromDB = dao.getAll()

        Assert.assertTrue(usersFromDB.contains(user))
    }

    @Test
    fun insertAndDelete() = runBlocking {
        val user = UserDataDBO(
            "10",
            "name",
            "surname",
            "midname",
            "email",
            "uName",
            "79999999999",
            LocalDateTime.now()
        )

        dao.insert(user)

        val userFromDB = dao.get(user.id).first()

        Assert.assertEquals(user, userFromDB)

        dao.remove(user)

        val usersFromDB = dao.getAll()

        Assert.assertFalse(usersFromDB.contains(user))
    }
}